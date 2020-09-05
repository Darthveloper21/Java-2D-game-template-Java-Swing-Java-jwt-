import java.awt.event.*;

public class KeyManager implements KeyListener
{
    public boolean[] keys;
    public boolean enter = false;
    public KeyEvent event;

    public KeyManager()
    {
        keys = new boolean[256];
    }

    public void SetEnter(Boolean enter)
    {
        this.enter = enter;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        keys[e.getKeyCode()] = true;
        System.out.println("Pressed !");
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys[e.getKeyCode()] = false;
    }
    
}
