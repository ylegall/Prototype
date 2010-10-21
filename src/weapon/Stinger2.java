
package weapon;

import core.Player;
import gamelib.SoundManager;
import gamelib.Vector2D;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 16, 2009 11:57:10 PM
 */
public class Stinger2 extends Weapon
{

    public Stinger2(Player player)
    {
        super(3, player);
        speed = 8;
        spread = 5;
        fireDelay = 2300;

        try { shot = SoundManager.getClip("rocket.wav");}
        catch (Exception e) {System.err.println(e.getMessage());}
//        ammo = 50;
    }

    @Override
    public Projectile getProjectile()
    {
        if(!cocked) return null;
        if(rounds > 0) {
            Vector2D vel = getVelocity();

            SoundManager.playSound(shot);

            cocked = false;
            rounds--;

            return new Seeker(player.getCenterPoint(), vel);
        } else {
            SoundManager.playSound(click);
            return null;
        }
    }

    @Override
    public String toString() {return "Stinger Mark-2";}
}
