import java.awt.*;
//import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.IOException;

public class Game implements Runnable {
    private Display display;
    private Thread thread;
    private boolean running = false;
    public int width, height;
    public String title;

    private BufferStrategy bs;
    private Graphics g;

    // States
    private State gameState;
    // private State menuState;

    // Input
    private KeyManager keyManager;
    private MouseManager mouseManager;
    private boolean ok = false;
    private int grid[][];

    public Game(String title, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init()
    {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init();

        gameState = new GameState();
        // menuState = new MenuState();
        State.setState(gameState);
        // input
        try
        {
            grid = Generate.initmap();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void tick()
    {
        if (State.getState() != null)
            State.getState().tick();
    }

    private void render()
    {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null)
        {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        // draw

        if(mouseManager.isLeftPressed())
        {
            ok = true;
        }
        else if(mouseManager.isRightPressed())
        {
            ok = false;
        }

        if(ok)
        {
            grid = Generate.nextgen(grid);
            for(int i = 0; i < 13; ++i)
                for(int j = 0; j < 17; ++j)
                {
                    if(grid[i][j] == 1) g.drawImage(Assets.alive, Assets.width*j, Assets.height*i, null);
                    else if(grid[i][j] == 0) g.drawImage(Assets.dead, Assets.width*j, Assets.height*i, null);
                }
        }

        // end drawing
        bs.show();
        g.dispose();
    }

    public void run()
    {
        init();

        int fps = 2;
        double timepertick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / timepertick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1)
            {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000)
            {
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start()
    {
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop()
    {
        if(!running) return;
        running = false;
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
