
package weapon;

import core.GameObject;
import gamelib.Point2D;
import gamelib.Vector2D;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 6, 2009 5:09:40 PM
 */
public abstract class Projectile extends GameObject
{

    protected int damage;

    public Projectile(Point2D point, Vector2D velocity)
    {
        super(point, velocity);
    }

    public abstract int getDamage();
}
