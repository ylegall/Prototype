
package weapon;

import gamelib.Point2D;
import util.ImageManager;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 13, 2009 7:12:45 PM
 */
public class SmallExp extends Explosion{

    public SmallExp(Point2D point)
    {
        super(point, ImageManager.smallExp, "exp3.wav");
    }

    @Override
    public int getDamage() {
        return 17;
    }
}
