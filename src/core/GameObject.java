
package core;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.Graphics;
import java.awt.Point;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 1, 2009 6:50:52 PM
 */
public abstract class GameObject
{
    protected Vector2D vel;
    protected Point2D pos;

    public GameObject(Point2D pos)
    {
        this(pos, new Vector2D(0,0));
    }

    public GameObject(Point pos)
    {
        this(pos, new Vector2D(0,0));
    }
    
    public GameObject(Point pos, Vector2D vel)
    {
        this.pos = new Point2D(pos.x, pos.y);
        this.vel = vel;
    }

    public GameObject(Point2D pos, Vector2D vel)
    {
        this.pos = pos;
        this.vel = vel;
    }

    public Point2D getPosition() {return pos;}

    public abstract void draw(Graphics g);
    public abstract void update();
    public void kill() {}
    public boolean isDead() {return false;}

    public static final Point2D getNextPosition(GameObject object) {
        Point2D p = new Point2D(object.pos);
        p.moveBy(object.vel);
        return p;
    }

    @Override
    public String toString() {return "";}
}
