package core;

import core.Player.Rotation;
import gamelib.Point2D;
import gamelib.SoundManager;
import gamelib.Vector2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import ui.ViewFrame;
import util.ImageManager;
import weapon.Phaser;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 11, 2009 11:43:35 PM
 */
public class Enemy extends GameObject {

    private final Image image;
    private final Rectangle rectangle;
    private double sin, cos;
    private int shields, state;
    private Vector2D direction;
    private final Player player;
    private Rotation rotation;
    private Clip fire;
    private boolean wasHit;

    public Enemy(Point p) {
        super(p);
        shields = 100;
        direction = new Vector2D(0, 1);
        ImageIcon im = ImageManager.enemy;
        rectangle = new Rectangle(new Dimension(im.getIconWidth(), im.getIconHeight()));
        rectangle.setLocation(pos.getPoint());
        image = im.getImage();

        player = ViewFrame.getInstance().getGame().getPlayer();

        sin = Math.sin(0.17);
        cos = Math.cos(0.17);

        try {
            fire = SoundManager.getClip("zap.wav");
        } catch (Exception e) {
        	System.err.println(e);
        	e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = g2.getTransform();
        g2.rotate(Math.atan2(direction.x, -direction.y), rectangle.getCenterX(), rectangle.getCenterY());
        g2.drawImage(image, (int) pos.x, (int) pos.y, null);
        g2.setTransform(at);

        if(wasHit) {
            g2.setColor(new Color(0,shields<<1, shields<<1));
            g2.drawOval((int)pos.x, (int)pos.y, rectangle.width, rectangle.height);
            wasHit = false;
        }
    }

    @Override
    public void update() {
        state++;
        if(state > 1000) state = 0;

        Vector2D dir = new Vector2D(player.pos.x - pos.x, player.pos.y - pos.y);
        dir = dir.direction();

        double angle = Math.atan2(dir.y, dir.x);
        angle = -angle + Math.atan2(direction.y, direction.x);

        if(vel.magnitude() > 12) { // going too fast:
            vel.scaleBy(0.7);
        } else if (Math.abs(angle) < 0.1) { // pointing at target
            rotation = Rotation.NONE;
            if(player.pos.distanceTo(pos) > 157) {
                vel.add(direction);
            } else {
                vel.scaleBy(0.7);
            }

            // shoot
            fire();

        } else { // otherwise rotate:
            if (angle > 0) {
                rotation = Rotation.LEFT;
            } else {
                rotation = Rotation.RIGHT;
            }
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
        pos.x += vel.x;
        pos.y += vel.y;
        rectangle.setLocation(pos.getPoint());

        // bounds checking:
        if (pos.x <
                0) {
            pos.x = ViewFrame.size.width - 1;
        } else if (pos.x > ViewFrame.size.width - 1) {
            pos.x = 0;
        }
        if (pos.y < 0) {
            pos.y = ViewFrame.size.height - 1;
        } else if (pos.y > ViewFrame.size.height - 1) {
            pos.y = 0;
        }
    }

    private final void fire() {
        if(state % 17 > 0) return;

        Vector2D v = new Vector2D(direction);
        v.scaleBy(35);

        int spread = 33;
        double half = spread / 2;
        v.x = v.x + (Game.random.nextDouble() * spread - half);
        v.y = v.y + (Game.random.nextDouble() * spread - half);

        ViewFrame.getInstance().getGame().addEnemyProjectile(
                new Phaser( new Point2D(rectangle.getCenterX(), rectangle.getCenterY()), v));
        SoundManager.playSound(fire);
    }

    public final Rectangle getRectangle() {return rectangle;}

    public boolean containsPoint(Point2D p) {
        if (p.x > rectangle.x && p.x < (rectangle.x + rectangle.width)) {
            if (p.y > rectangle.y && p.y < (rectangle.y + rectangle.height)) {
                return true;
            }
        }
        return false;
    }

    public final void damage(int amount) {
        shields -= amount;
        wasHit = true;
    }

    @Override
    public boolean isDead() {
        return shields < 0;
    }

    @Override
    public String toString() {
    	return "enemy";
    }
}

