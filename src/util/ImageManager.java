
package util;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.InputStream;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 2:36:14 PM
 */
public class ImageManager
{
    public static final ImageIcon health = getImage("health.png");
    public static final ImageIcon ammo = getImage("ammo.png");
    public static final ImageIcon enemy = getImage("enemy.png");
    public static final ImageIcon blueExp = getImage("blueExp.png");
    public static final ImageIcon smallExp = getImage("smallExp.png");
    public static final ImageIcon enemyExp = getImage("myExp.png");
    public static final int bigSize = blueExp.getIconHeight();

    private ImageManager() {}

    public static ImageIcon getImage(String file)
    {
        //URL url = ImageManager.class.getResource("/resources/images/"+file);
		//return new ImageIcon(url);
		try {
			InputStream is = ImageManager.class.getResourceAsStream("/resources/images/"+file);
			return new ImageIcon(ImageIO.read(is));
		} catch (Exception e) {
			System.err.println("error loading image '" + file + "': " + e);
			return null;
		}
    }

    public static BufferedImage getBufferedImage(String file)
    {
        BufferedImage bi = null;
        try
        {
            URL url = ImageManager.class.getResource("/resources/images/"+file);
            bi = ImageIO.read(url);
        }
        catch (java.io.IOException ioe) {}
        return bi;
    }

}
