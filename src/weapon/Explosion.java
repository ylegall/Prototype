package weapon;

import gamelib.Point2D;
import gamelib.SoundManager;
import gamelib.Vector2D;
import java.awt.Graphics;
import java.awt.Image;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 13, 2009 12:03:33 AM
 */
public abstract class Explosion extends Projectile {

    private float life, delay;
    private Image sprites;
    private int imageSize, numImages;
    protected Clip clip;

    public Explosion(Point2D p, ImageIcon spriteSheet, String soundFile)
    {    
        this(p,spriteSheet);
        try { clip = SoundManager.getClip(soundFile);}
        catch (Exception e) {System.err.println(e.getMessage());}
        SoundManager.playSound(clip);
    }

    public Explosion(Point2D p, ImageIcon spriteSheet) {
        super(p, new Vector2D(0, 0));
        ImageIcon im = spriteSheet;
        imageSize = im.getIconHeight();
        numImages = im.getIconWidth()/imageSize;
        sprites = im.getImage();
        life = 1f;
        delay = 0.08f;
    }

    @Override
    public boolean isDead() {
        return life <= 0;
    }

    public float getLife() {return life;}

    public void update() {
        life -= delay;
        if (life <= 0) {
            life = 0;
        }
    }

    public void draw(Graphics g) {
        int imageIndex = (int) ((1f - life) * numImages);
        int x = imageIndex * imageSize;
        //int offset = imageSize;
        g.drawImage(sprites,
                (int) pos.x - imageSize, (int) pos.y - imageSize,
                (int) pos.x + imageSize, (int) pos.y + imageSize,
                x, 0,
                x + imageSize, imageSize, null);
    }

    public boolean isExploding() {return true;}

}
