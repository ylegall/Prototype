
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
 *  Dec 20, 2009 6:52:36 PM
 */
public class BeamParticle extends Projectile
{
    private static boolean updated;
    private static Point2D first, last;
    private int life;

    public BeamParticle(Point2D point, boolean isFirst, boolean isLast)
    {
        super(point, new Vector2D(0,0));
        if(isFirst) {
            first = point;
        } else if (isLast) {
            last = point;
        }
        life = 10;
    }

    @Override
    public int getDamage() {
        return 20;
    }

    @Override
    public void draw(Graphics g) {
        if(updated) return;
        Graphics2D g2 = (Graphics2D)g;
        Stroke s = g2.getStroke();
        g2.setStroke(new BasicStroke(life));
        g2.setColor(Color.ORANGE);
        g2.drawLine((int)first.x, (int)first.y, (int)last.x, (int)last.y);
        g2.setStroke(s);
        updated = true;
    }

    @Override
    public void update() {
        updated = false;
        life--;
    }

    @Override
    public boolean isDead() {
        return life <= 0;
    }

    @Override
    public void kill() {
        life = 0;
    }



}
