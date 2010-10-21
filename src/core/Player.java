package core;

import core.AutoPilot.SystemStatus;
import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import ui.ViewFrame;
import util.ImageManager;
import weapon.*;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 2, 2009 8:06:05 PM
 */
public class Player extends GameObject implements KeyListener {

    private Image image, boostingImage;
    private double sin, cos, heading;
    private Vector2D direction;
    private boolean boosting, firing, breaking, wasHit;
    private Rotation rotation;
    private Rectangle rectangle;
    private int shields, weaponIndex;
    private ArrayList<Weapon> weapons;
    private Weapon weapon;
    private AutoPilot autoPilot;
    private GameObject target;

    public Player(Point2D p) {
        super(p);
        direction = new Vector2D(0, -1);
        rotation = Rotation.NONE;

        double rotationSpeed = 0.17;
        sin = Math.sin(rotationSpeed);
        cos = Math.cos(rotationSpeed);

        boostingImage = ImageManager.getImage("boosting.png").getImage();
        ImageIcon icon = ImageManager.getImage("player.png");
        rectangle = new Rectangle(icon.getIconWidth(), icon.getIconHeight());
        rectangle.setLocation(p.getPoint());
        image = icon.getImage();

        shields = 255;
        weaponIndex = 0;
        weapons = new ArrayList<Weapon>();
        weapons.add(new SMP9(this));
        weapons.add(new X2Vulcan(this));
        weapons.add(new EM12Carbine(this));
        weapons.add(new Stinger2(this));
        weapons.add(new ALS22(this));
        weapons.add(new Solaris(this));

        weapon = weapons.get(weaponIndex);

        autoPilot = new AutoPilot(this);
    }

    public final void setBoosting(boolean boosting) {
        this.boosting = boosting;
    }
    public final void setBreaking(boolean breaking) {
        this.breaking = breaking;
    }
    public final void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public final void setWeapon(int weaponIndex) {
        weapon = weapons.get(weaponIndex);
        ViewFrame.getInstance().setWeapon(weapon.toString());
    }

    public final void setFiring(boolean firing) {
        if(this.firing == firing) {
            return;
        } else {
            this.firing = firing;
            if (!firing) {
                weapon.cock();
            }
        }
    }

    public final GameObject cycleTarget(boolean enemyFirst) {

        Game game = ViewFrame.getInstance().getGame();
        if(enemyFirst) {
            target = game.getEnemy();
            if (target != null) {
                return target;
            } else {
                target = game.getItem();
            }
        } else {
            target = game.getItem();
            if (target != null) {
                return target;
            } else {
                target = game.getEnemy();
            }
        }
        return target;
    }

    public Enemy getEnemy() {
        if(target instanceof Enemy) {
            return (Enemy)target;
        } else {
            return null;
        }
    }

    public final void slow() {
        vel.scaleBy(0.8);
    }

    public final Vector2D getDirection() {
        return direction;
    }

    public final void cycleWeapon(int direction) {
        do {
            weaponIndex += direction;
            if (weaponIndex < 0) {
                weaponIndex = weapons.size() - 1;
            } else if (weaponIndex >= weapons.size()) {
                weaponIndex = 0;
            }
            weapon = weapons.get(weaponIndex);
        } while (weapon.getAmmo() + weapon.getRounds() == 0);
        ViewFrame.getInstance().setWeapon(weapon.toString());
        displayAmmo();
    }

    public final void reload() {
        if(weapon.getAmmo() > 0) {
            weapon.reload();
            displayAmmo();
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public final Point2D getCenterPoint() {
        return new Point2D(rectangle.getCenterX(), rectangle.getCenterY());
    }

    public boolean containsPoint(Point2D p) {
        return rectangle.contains(p.x, p.y);
    }

    public final void damage(int amount) {
        shields -= amount;
        if(shields < 0) shields = 0;
        wasHit = true;
        if(shields < 50) {
            autoPilot.setStatus(AutoPilot.SystemStatus.DAMAGED);
        }
        ViewFrame.getInstance().setHealth(shields);
    }

    public final int getShields() {
        return shields;
    }

    public final void addShields(int amount) {
        shields += amount;
        if(shields > 255) shields = 255;
        else if(shields > 50) {
            if(autoPilot.getStatus() == AutoPilot.SystemStatus.DAMAGED) {
                autoPilot.setStatus(AutoPilot.SystemStatus.OFF);
            }
        }
    }

    public final void addAmmo(int amount, Class weaponType) {
        for(Weapon w : weapons) {
            if(w.getClass().equals(weaponType)) {
                w.addAmmo(amount);
            }
        }
    }

    public final boolean isFiring() {return firing;}

    @Override
    public boolean isDead() {
        return false;
    }

    private void displayAmmo() {
        ViewFrame.getInstance().setAmmo(weapon.getRounds(), weapon.getClipSize(), weapon.getAmmo());
    }

    @Override
    public void draw(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = g2.getTransform();
        heading = Math.atan2(direction.x, -direction.y);
        g2.rotate(heading, rectangle.getCenterX(), rectangle.getCenterY());
        if (boosting) {
            g2.drawImage(boostingImage, (int) pos.x, (int) pos.y, null);
        } else {
            g2.drawImage(image, (int) pos.x, (int) pos.y, null);
        }

        g2.setTransform(at);

        if (target != null) {
            if (target.isDead()) {
                target = null;
                return;
            }
            if(target instanceof Enemy) {
                g2.setColor(Color.RED);
                g2.draw(((Enemy)target).getRectangle());
            } else {
                g2.setColor(Color.CYAN);
                g2.draw(((Item)target).getRectangle());
            }
        }

        if(wasHit) {
            g2.setColor(new Color(0,shields>>1, shields));
            g2.drawOval((int)pos.x, (int)pos.y, rectangle.width, rectangle.height);
            wasHit = false;
        }

//        if(weapon instanceof Solaris) {
//            g2.setColor(Color.DARK_GRAY);
//            java.awt.Point p = getCenterPoint().getPoint();
//            g2.drawLine(p.x, p.y, p.x + (int)(600*direction.x), p.y + (int)(600*direction.y));
//        }
    }

    @Override
    public void update() {
        if (autoPilot.getStatus() == AutoPilot.SystemStatus.ON) {
            autoPilot.update();
        }

        switch (rotation) {
            case RIGHT:
                direction.x = (direction.x * cos) - (direction.y * sin);
                direction.y = (direction.x * sin) + (direction.y * cos);
                break;
            case LEFT:
                direction.x = (direction.x * cos) + (direction.y * sin);
                direction.y = -(direction.x * sin) + (direction.y * cos);
                break;
        }

        direction = direction.direction();

        if (boosting) {
            vel.add(direction);

            vel.x = (Math.abs(vel.x) > 35)? Math.signum(vel.x)*35 : vel.x;
            vel.y = (Math.abs(vel.y) > 35)? Math.signum(vel.y)*35 : vel.y;
        } else if(breaking) {
            slow();
        }

        pos.x += vel.x;
        pos.y += vel.y;
        rectangle.setLocation(pos.getPoint());

        // bounds checking:
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

        if (firing) {
            weapon.fire();
            displayAmmo();
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_P) {
            ViewFrame.getInstance().pause();
            return;
        }
        else if(keyCode == KeyEvent.VK_A) {

            SystemStatus status = autoPilot.getStatus();
            switch(status) {
                case ON: 
                    autoPilot.setStatus(AutoPilot.SystemStatus.OFF);
                    boosting = firing = false;
                    rotation = Rotation.NONE;
                    break;
                case OFF: 
                    autoPilot.setStatus(AutoPilot.SystemStatus.ON);
                    break;
                default:
                    return;
            }
            return;
        }

        if(autoPilot.getStatus() == AutoPilot.SystemStatus.ON) {
            return;
        }

        
        switch (keyCode) {

            case KeyEvent.VK_UP: {
                if(!boosting) {
                    setBoosting(true);
                }
                break;
            }
            case KeyEvent.VK_DOWN: {
                if(!breaking) {
                    setBreaking(true);
                }
                break;
            }
            case KeyEvent.VK_LEFT: {
                setRotation(Player.Rotation.LEFT);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                setRotation(Player.Rotation.RIGHT);
                break;
            }
            case KeyEvent.VK_D: {
                cycleTarget(true);
                break;
            }
            case KeyEvent.VK_Q: {
                cycleWeapon(-1);
                break;
            }
            case KeyEvent.VK_E: {
                cycleWeapon(1);
                break;
            }
            case KeyEvent.VK_SPACE:
                firing = true;
                break;
            case KeyEvent.VK_R:
                reload();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_E:
            case KeyEvent.VK_Q:
//                ViewFrame.getInstance().setWeapon(weapon.toString());
//                displayAmmo();
                break;
            case KeyEvent.VK_UP:
                setBoosting(false);
            case KeyEvent.VK_DOWN:
                setBreaking(false);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                setRotation(Player.Rotation.NONE);
                break;
            case KeyEvent.VK_SPACE: {
                firing = false;
                new Thread(new Runnable() {
                    public void run() {
                        weapon.cock();
                    }
                }).start();
                break;
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static enum Rotation {

        NONE,
        LEFT,
        RIGHT
    }
}

