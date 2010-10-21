package core;

import gamelib.Point2D;
import gamelib.Vector2D;
import ui.ViewFrame;
import weapon.Weapon;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 10, 2009 9:34:21 PM
 */
public class AutoPilot {

    private Player player;
    private GameObject target;
    private int evadeTimer, messageTimer;
    private SystemStatus status;

    public AutoPilot(Player player) {
        this.player = player;
        status = SystemStatus.OFF;
    }

    public final void setTarget(GameObject target) {
        this.target = target;
    }

    public final void update() {
        updateTerminal();

        if (target != null) {

            if (target.isDead()) {
                target = null;
                return;
            }

            if (target instanceof Enemy) {
                attack();
            } else {
                seek();
            }

        } else {
            idle();
        }
    }

    private final void attack() {

        if (player.getWeapon().getClass().equals(weapon.Stinger2.class)) {
            if (evadeTimer > 0) {
                if(evadeTimer % 31 == 0)fire();
            } else {
                evadeTimer = 50;
            }
        } else {
            evadeTimer = 0;
        }

        double angle = getAngle(false);

        // if pointing at target:
        if (Math.abs(angle) < 0.1) {
            player.setRotation(Player.Rotation.NONE);
            double dist = player.pos.distanceTo(target.pos);

            if (evadeTimer > 0) {
                setSpeed(15);
                return;
            } else if (dist < 60) {
                evadeTimer = 30;
            } else if (dist < 160) {
                setSpeed(0);
            } else {
                setSpeed(5);
            }

            fire();

        } else {
            //player.slow();
            rotate(angle);
        }
    }

    private final void seek() {
        evadeTimer = 0;
        double angle = getAngle(true);

        // if pointing at target:
        if (Math.abs(angle) < 0.1) {
            player.setRotation(Player.Rotation.NONE);
            double dist = player.pos.distanceTo(target.pos);
            setSpeed(dist / 7 + 8);
        } else {
            player.slow();
            rotate(angle);
        }
    }

    private final void rotate(double angle) {
        player.setFiring(false);
        if (angle > 0) {
            player.setRotation(Player.Rotation.LEFT);
        } else {
            player.setRotation(Player.Rotation.RIGHT);
        }
    }

    private final double getAngle(boolean wrap) {
        // get next locations:
        Point2D playerNext = GameObject.getNextPosition(player);
        Point2D targetNext = GameObject.getNextPosition(target);

        // try to take shortest path:
        if(wrap) {
            if(Math.abs(playerNext.x - (targetNext.x + 600)) < 150) targetNext.x =  targetNext.x + 600;
            else if(Math.abs(targetNext.x - (playerNext.x + 600)) < 150) targetNext.x =  playerNext.x + 600;
            if(Math.abs(playerNext.y - (targetNext.y + 600)) < 150) targetNext.y =  targetNext.y + 600;
            else if(Math.abs(targetNext.y - (playerNext.y + 600)) < 150) targetNext.y =  playerNext.y + 600;
        }

        // get direction vectors:
        Vector2D direction = new Vector2D(
                targetNext.x - playerNext.x,
                targetNext.y - playerNext.y);
        direction = direction.direction();
        Vector2D heading = player.getDirection();

        // if evading:
        if (evadeTimer > 0) {
            evadeTimer--;
            direction = direction.perpendicular(); // change desired direction
        }

        // get the angle needed to rotate
//        double angle = Math.atan2(direction.y, direction.x);
//        angle = Math.atan2(heading.y, heading.x) - angle;
        double angle = Vector2D.signedAngle(direction, heading);
        return angle;
    }

    private final void fire() {
        if (player.getWeapon().getRounds() > 0) {
            player.setFiring(false);
            player.setFiring(true);
        } else {
            autoLoad();
        }
    }

    private final void idle() {
        evadeTimer = 0;
        player.setFiring(false);
        autoLoad();
        player.setRotation(Player.Rotation.NONE);
        setSpeed(0);
        autoTarget();
    }

    private final void setSpeed(double speed) {
        player.setBoosting(false);
        double mag = player.vel.magnitude();
        if (mag > speed) {
            player.slow();
        } else {
            player.setBoosting(true);
        }
    }

    private final void autoLoad() {
        Weapon weapon = player.getWeapon();
        if (weapon.getClass().equals(weapon.SMP9.class)) {
            player.cycleWeapon(-1);
        }

        while (weapon.getAmmo() + weapon.getRounds() < 1) {
            player.cycleWeapon(-1);
            weapon = player.getWeapon();
        }

//        if(player.getWeapon().getClass().equals(weapon.Stinger2.class)) {
//            player.cycleWeapon(-1);
//        }

        if (weapon.getRounds() < 1) {
            player.reload();
        }
    }

    private final void autoTarget() {
        // try to auto-target:
        target = player.cycleTarget(false);
        if (target != null) {
            printMessage("target acquired: " + target.toString());
        }
    }

    private final void updateTerminal() {
        messageTimer++;
        if (messageTimer > 97) {
            printMessage("");
        }
    }

    private final void printMessage(String str) {
        ViewFrame.getInstance().setMessage(str);
        messageTimer = 0;
    }

    public final void setStatus(SystemStatus status) {
        this.status = status;
        switch (status) {
            case ON:
                printMessage("auto pilot online");
                break;
            case DAMAGED:
                printMessage(" \u0E54\u0E34\u0E41\u0E23 \u0E11\u0E22 \u0E13\u0E17\u0E39");
                break;
            case OFF:
                printMessage("manual control restored");
                break;
        }
        player.setFiring(false);
        player.setBoosting(false);
        player.setRotation(Player.Rotation.NONE);
        evadeTimer = 0;
        ViewFrame.getInstance().setAutoPilotLabel(status);
    }

    public SystemStatus getStatus() {
        return status;
    }

    public static enum SystemStatus {

        ON, OFF, DAMAGED
    }
}
