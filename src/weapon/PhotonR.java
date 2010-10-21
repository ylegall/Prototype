package weapon;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 10:45:08 AM
 */
public class PhotonR extends Projectile {

    protected float life = 1f;

    public PhotonR(Point2D pos, Vector2D vel) {
        super(pos, vel);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke s = g2.getStroke();
        g.setColor(new Color(life, 0, 0));
        g2.setStroke(new BasicStroke(2f));
        g.drawLine(
                (int) pos.x,
                (int) pos.y,
                (int) (pos.x - vel.x),
                (int) (pos.y - vel.y));
        g2.setStroke(s);
    }

    @Override
    public boolean isDead() {
        return life <= 0;
    }

    @Override
    public void kill() {
        life = -1;
    }

    @Override
    public int getDamage() {
        return 30;
    }

    @Override
    public void update()
    {
        life -= 0.05;
        vel.scaleBy(0.95);
        pos.x += vel.x;
        pos.y += vel.y;

//        if (pos.x < 0) {
//            pos.x = 1;
//            life = -1;
//        } else if (pos.x > ViewFrame.size.width) {
//            pos.x = ViewFrame.size.width - 1;
//            life = -1;
//        }
//
//        if (pos.y > ViewFrame.size.height) {
//            pos.y = ViewFrame.size.height;
//            life = -1;
//        } else if (pos.y < 0) {
//            life = -1;
//        }
    }
}
