package weapon;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 10:45:08 AM
 */
public class EMPulse extends Projectile {

    private float life = 1f;

    public EMPulse(Point2D pos, Vector2D vel) {
        super(pos, vel);
    }

    @Override
    public void draw(Graphics g) {
        {
            g.setColor(new Color(life,life, 1f));
            ((Graphics2D) g).setStroke(new BasicStroke(2f));
            g.drawLine(
                    (int) pos.x,
                    (int) pos.y,
                    (int) (pos.x - vel.x),
                    (int) (pos.y - vel.y));
        }

    }

    @Override
    public boolean isDead() {
        return life <= 0;
    }

    @Override
    public void kill() {
        explode();
    }

    @Override
    public void update() {
        vel.scaleBy(0.9);
        pos.x += vel.x;
        pos.y += vel.y;

        // bounce off of walls:
        if (pos.x < 0 || pos.x > ViewFrame.size.width) {
            vel.x = -vel.x;
        }
        else if (pos.y < 0 || pos.y > ViewFrame.size.height) {
            vel.y = -vel.y;
        }

        if (life <= 0.01) {
            explode();
        } else {
            life -= 0.09f;
        }
    }

    @Override
    public int getDamage() {
        return 20;
    }

    public void explode() {
        vel.x = vel.y = 0;
        life = 0f;
        ViewFrame.getInstance().getGame().addExplosion(new BlueExplosion(pos));
    }
}
