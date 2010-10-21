package weapon;

import core.Game;
import core.Player;
import gamelib.SoundManager;
import gamelib.Vector2D;
import javax.sound.sampled.Clip;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 11, 2009 11:50:18 PM
 */
public abstract class Weapon {

    private final int clipSize;
    protected int rounds, fireDelay, ammo;
    protected volatile boolean cocked, cockPending;
    protected double speed, spread;
    protected Clip click, reload, shot;
    protected Player player;

    public Weapon(int clipSize, Player player) {
        this.clipSize = clipSize;
        cocked = true;
        this.player = player;
        try {
            click = SoundManager.getClip("click.wav");
            reload = SoundManager.getClip("reload.wav");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void fire() {
        Projectile go = getProjectile();
        if (go != null) {
            ViewFrame.getInstance().getGame().addProjectile(go);
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void addAmmo(int amount) {
        if (amount > 0) {
            ammo += amount;
        }
    }

    public int getRounds() {
        return rounds;
    }

    public int getClipSize() {
        return clipSize;
    }

    protected Vector2D getVelocity() {
        Vector2D vel = new Vector2D(player.getDirection());
        vel.scaleBy(speed);

        double half = spread / 2;
        vel.x = vel.x + (Game.random.nextDouble() * spread - half);
        vel.y = vel.y + (Game.random.nextDouble() * spread - half);
        return vel;
    }

    protected abstract Projectile getProjectile();

    public void reload() {
        while (rounds < clipSize && ammo > 0) {
            rounds++;
            ammo--;
        }

        if (rounds > 0) {
            SoundManager.playSound(reload);
            if(!cocked) cock();
        }
    }

    public synchronized void cock() {
        if(cockPending) return;
        
        cockPending = true;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(fireDelay);
                } catch (InterruptedException e) {}
                finally {
                    cocked = true;
                    cockPending = false;
                }
            }
        }).start();
    }
}
