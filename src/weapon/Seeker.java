
package weapon;

import core.Enemy;
import core.GameObject;
import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 16, 2009 11:42:40 PM
 */
public class Seeker extends Projectile
{
    private int state;
    private Enemy enemy;

    public Seeker(Point2D pos, Vector2D vel)
    {
        super(pos, vel);
        state = 300;
    }

    @Override
    public void update()
    {
        state--;
        
        if(enemy == null) {
            enemy = ViewFrame.getInstance().getGame().getPlayer().getEnemy();
        } else {
            if(enemy.isDead()) {
                enemy = null;
            } else {
                seek();
            }
        }

        pos.x += vel.x;
        pos.y += vel.y;

        // bounds updating
        if (pos.x < 0) {
            pos.x = ViewFrame.size.width - 1;
        } else if (pos.x > ViewFrame.size.width - 1) {
            pos.x = 0;
        }

        if (pos.y < 0) {
            pos.y = ViewFrame.size.height - 1;
        } else if (pos.y > ViewFrame.size.height - 1) {
            pos.y = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke s = g2.getStroke();
        g.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(3f));
        g.drawLine((int)pos.x, (int)pos.y, (int)(pos.x + vel.x), (int)(pos.y + vel.y));
        g2.setStroke(s);
    }

    private final void seek()
    {
        Point2D e = GameObject.getNextPosition(enemy);
        Point2D next = GameObject.getNextPosition(this);

        // auto-detonate when close
        if(pos.distanceTo(e) < 15) {
            explode();
            return;
        }

        Vector2D dir = new Vector2D(e.x - next.x + 10, e.y - next.y + 10);
        dir = dir.direction();
        vel.add(dir);
        vel = vel.direction();
        vel.scaleBy(12);
    }

    public final int getDamage() {
        return 30;
    }

    @Override
    public boolean isDead() {
        return state < 0;
    }

    @Override
    public void kill() {
        explode();
    }

    public final void explode()
    {
        state = -1;
        ViewFrame.getInstance().getGame().addExplosion(new SmallExp(pos));
    }
}

