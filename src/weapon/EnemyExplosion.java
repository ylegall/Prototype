
package weapon;

import gamelib.Point2D;
import util.ImageManager;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 17, 2009 9:16:47 PM
 */
public class EnemyExplosion extends Explosion
{
    public EnemyExplosion(Point2D point)
    {
        super(new Point2D(point), ImageManager.enemyExp, "exp3.wav");
    }

    @Override
    public int getDamage() {
        return 14;
    }
}
