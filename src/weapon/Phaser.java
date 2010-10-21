
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
public class Phaser extends PhotonR
{
    public Phaser(Point2D pos, Vector2D vel) {
        super(pos, vel);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(life, 0, life));
        g.drawLine(
                (int) pos.x,
                (int) pos.y,
                (int) (pos.x - vel.x),
                (int) (pos.y - vel.y));
    }

    @Override
    public int getDamage() {
        return 10;
    }

}
