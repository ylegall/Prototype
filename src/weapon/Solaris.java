
package weapon;

import core.Player;
import gamelib.Point2D;
import gamelib.SoundManager;
import gamelib.Vector2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 20, 2009 6:40:30 PM
 */
public class Solaris extends Weapon
{
    
    public Solaris(Player player)
    {
        super(4, player);

        fireDelay = 1500;
        try {
            shot = SoundManager.getClip("beam.wav");
        }
        catch (Exception e) {System.err.println(e.getMessage());}
//        rounds = 5;
        //ammo = 50;
    }

    @Override
    protected Projectile getProjectile() {
        throw new UnsupportedOperationException("method not supported");
    }

    protected List<Projectile> getProjectiles() {
        if(!cocked) {return null;}
        if(rounds > 0) {
            //Vector2D vel = getVelocity();
            SoundManager.playSound(shot);

            cocked = false;
            rounds--;

            List<Projectile> laserList = new LinkedList<Projectile>();
            Vector2D dir = player.getDirection().getScaledVector(5);
            Rectangle rect = new Rectangle(ViewFrame.size);

            BeamParticle beam;
            Point2D p = player.getCenterPoint();
            beam = new BeamParticle(p, true, false);

            while(true) {
                p = new Point2D(p.x+dir.x, p.y + dir.y);
                if(rect.contains(p.getPoint())) {
                    beam = new BeamParticle(p, false, false);
                    laserList.add(beam);
                } else {
                    beam = new BeamParticle(p, false, true);
                    laserList.add(beam);
                    break;
                }
            }
            return laserList;
            
        } else {
            SoundManager.playSound(click);
            return null;
        }
    }

    @Override
    public void fire() {
        List<Projectile> go = getProjectiles();
        if (go != null) {
            ViewFrame.getInstance().getGame().addProjectiles(go);
        }
    }

    @Override
    public String toString() {
        return "Solaris-1";
    }
}
