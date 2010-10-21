
package weapon;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.Color;
import java.awt.Graphics;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 11, 2009 11:48:07 PM
 */
public class PhotonG extends PhotonR
{
    public PhotonG(Point2D pos, Vector2D vel) {
        super(pos, vel);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(life*life, life, 0));
        g.drawLine(
                (int) pos.x,
                (int) pos.y,
                (int) (pos.x - vel.x),
                (int) (pos.y - vel.y));
    }

    @Override
    public int getDamage() {
        return 20;
    }

    @Override
    public void update()
    {
        life -= 0.05;
        vel.scaleBy(0.9);
        pos.x += vel.x;
        pos.y += vel.y;
    }
}
