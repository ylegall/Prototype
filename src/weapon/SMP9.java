
package weapon;


import core.Player;
import gamelib.SoundManager;
import gamelib.Vector2D;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 10:59:13 AM
 */
public class SMP9 extends Weapon
{

    public SMP9(Player player)
    {
        super(1, player);
        speed = 50;
        spread = 10;
        fireDelay = 0;
        try
        {
            shot = SoundManager.getClip("pulse.wav");
        }
        catch (Exception e) {System.err.println(e.getMessage());}

        rounds = ammo = 1;
    }

    @Override
    public Projectile getProjectile()
    {
        if(!cocked) return null;
        if(rounds > 0) {
            Vector2D vel = getVelocity();

            SoundManager.playSound(shot);
            cocked = false;
            //rounds--;

            return new PhotonR(player.getCenterPoint(), vel);
        } else {
            SoundManager.playSound(click);
            return null;
        }
    }

    @Override
    public String toString() {return "SMP9";}
}
