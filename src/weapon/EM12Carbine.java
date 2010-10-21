
package weapon;

import core.Player;
import gamelib.SoundManager;
import gamelib.Vector2D;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 14, 2009 10:59:13 AM
 */
public class EM12Carbine extends Weapon
{
    
    public EM12Carbine(Player player)
    {
        super(6, player);
        speed = 70;
        spread = 9;
        fireDelay = 1200;
        try
        {
            shot = SoundManager.getClip("pulse.wav");
        }
        catch (Exception e) {System.err.println(e.getMessage());}
//        rounds = 5;
//        ammo = 50;
    }

    @Override
    public Projectile getProjectile()
    {
        if(!cocked) {return null;}
        if(rounds > 0) {
            Vector2D vel = getVelocity();

            SoundManager.playSound(shot);

            cocked = false;
            rounds--;
            return new EMPulse(player.getCenterPoint(), vel);
        } else {
            SoundManager.playSound(click);
            return null;
        }
    }

    @Override
    public String toString() {return "EM12-Carbine";}
}
