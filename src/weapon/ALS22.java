
package weapon;

import core.Game;
import core.Player;
import gamelib.Point2D;
import gamelib.SoundManager;
import gamelib.Vector2D;
import java.util.ArrayList;
import java.util.List;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 24, 2009 10:03:59 PM
 */
public class ALS22 extends Weapon
{
    public ALS22(Player player)
    {
        super(4, player);
        speed = 33;
        spread = 17;
        fireDelay = 1000;

        try { shot = SoundManager.getClip("pulse.wav");}
        catch (Exception e) {System.err.println(e.getMessage());}
        //ammo = 50;
    }

    @Override
    protected Projectile getProjectile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected List<Projectile> getProjectiles() {
        if(!cocked) {return null;}
        if(rounds > 0) {
            SoundManager.playSound(shot);
            
            cocked = false;
            rounds--;
            
            List<Projectile> shotList = new ArrayList<Projectile>(7);
            Point2D p = player.getCenterPoint();

            Vector2D vel = player.getDirection().getScaledVector(speed);
            
            double half = spread / 2;
            for(int i=0; i<7; i++) {
                Vector2D v = new Vector2D();
                v.x = vel.x + (Game.random.nextDouble() * spread - half);
                v.y = vel.y + (Game.random.nextDouble() * spread - half);
                //v = v.direction();
                //v.scaleBy(speed);
                shotList.add(new PhotonR(new Point2D(p), v));
            }
            
            return shotList;

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
    public String toString() {return "ALS-22";}
}
