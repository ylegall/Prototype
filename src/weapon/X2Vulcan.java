
package weapon;

import core.Player;
import gamelib.SoundManager;
import gamelib.Vector2D;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 10:59:13 AM
 */
public class X2Vulcan extends Weapon
{

    public X2Vulcan(Player player)
    {
        super(50, player);
        speed = 70;
        spread = 21;
        fireDelay = 0;
        try {
            shot = SoundManager.getClip("zap.wav");
        }
        catch (Exception e) {System.err.println(e.getMessage());}
//        rounds = 50;
//        ammo = 50;
    }

    @Override
    public Projectile getProjectile()
    {
        if(!cocked) return null;
        if(rounds > 0) {
            Vector2D vel = getVelocity();

            SoundManager.playSound(shot);

            //cocked = false;
            rounds--;

            return new PhotonG(player.getCenterPoint(), vel);
        } else {
            SoundManager.playSound(click);
            return null;
        }
    }

    @Override
    public String toString() {return "X2-Vulcan";}
}
