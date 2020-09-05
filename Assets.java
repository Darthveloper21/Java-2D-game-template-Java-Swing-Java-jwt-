import java.awt.image.*;
public class Assets
{
    public static final int width = 48, height = 48;

    public static BufferedImage dead, alive;
    public static void init()
    {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("resources/cells2.png"));
        dead = sheet.crop(0, 0, width, height);
        alive = sheet.crop(width, 0, width, height);
    }
}
