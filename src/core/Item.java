
package core;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import ui.ViewFrame;
import util.ImageManager;
import weapon.*;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 13, 2009 11:34:07 PM
 */
public class Item extends GameObject
{
    private ImageIcon icon;
    private Class weaponType;
    private int amount, ammo;
    private Rectangle rectangle;

    public Item(Point2D pos, Vector2D vel)
    {
        super(pos, vel);
        amount = 200;
        
        if(Game.random.nextBoolean()) {
            icon = ImageManager.health;
        } else {
            icon = ImageManager.ammo;
            int type = Game.random.nextInt(5);
            switch (type)
            {
                case 0: weaponType = X2Vulcan.class; ammo=80; break;
                case 1: weaponType = EM12Carbine.class; ammo=12; break;
                case 2: weaponType = Stinger2.class; ammo=7; break;
                case 3: weaponType = ALS22.class; ammo=12; break;
                case 4: weaponType = Solaris.class; ammo=4; break;
            }
        }
        rectangle = new Rectangle(icon.getIconWidth(), icon.getIconHeight());
        rectangle.setLocation((int)pos.x, (int)pos.y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(icon.getImage(), (int) pos.x, (int) pos.y, null);
    }

    public final Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void update() {
        amount--;

        pos.x += vel.x;
        pos.y += vel.y;
        rectangle.setLocation((int)pos.x, (int)pos.y);

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
    }

    public final int getAmount() {
        if(isHealth()) {
            return 33;
        } else {
            return ammo;
        }
    }

    public final boolean isHealth() {
        return weaponType == null;
    }

    public Class getWeaponType() {
        return weaponType;
    }

    @Override
    public boolean isDead() {
        return amount < 1;
    }

    @Override
    public final void kill() {
        amount = 0;
    }

    @Override
    public String toString() {
        if(isHealth()) {
            return "shield repair";
        } else {
            return "ammunition";
        }
    }
}
