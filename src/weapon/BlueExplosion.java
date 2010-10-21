
package weapon;

import gamelib.Point2D;
import util.ImageManager;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 13, 2009 6:06:23 PM
 */
public class BlueExplosion extends Explosion
{
    public BlueExplosion(Point2D point)
    {
        super(point, ImageManager.blueExp, "exp3.wav");
    }

    @Override
    public int getDamage() {
        return 12;
    }
}
