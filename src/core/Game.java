package core;

import gamelib.Point2D;
import gamelib.Vector2D;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import ui.ViewFrame;
import weapon.EnemyExplosion;
import weapon.Explosion;
import weapon.Projectile;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Dec 1, 2009 6:14:30 PM
 */
public class Game {

    private Player player;
    private int kills;
    private LinkedList<Projectile> projectiles;
    private LinkedList<Projectile> enemyProjectiles;
    private LinkedList<Enemy> enemies;
    private LinkedList<Explosion> explosions;
    private LinkedList<Item> items;
    public static Random random = new Random();
    private int spawnEvent, enemyIndex, itemIndex;

    public Game() {
        projectiles = new LinkedList<Projectile>();
        enemyProjectiles = new LinkedList<Projectile>();
        enemies = new LinkedList<Enemy>();
        explosions = new LinkedList<Explosion>();
        items = new LinkedList<Item>();

        player = new Player(new Point2D(ViewFrame.size.width / 2, ViewFrame.size.height / 2));
        spawnEvent = 300;
        kills = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public final void newGame() {
        ViewFrame frame = ViewFrame.getInstance();
        frame.removeKeyListener(player);

        enemies.clear();
        projectiles.clear();
        explosions.clear();
        enemyProjectiles.clear();
        items.clear();

        player = new Player(new Point2D(ViewFrame.size.width / 2, ViewFrame.size.height / 2));
        frame.addKeyListener(player);
        spawnEvent = 300;
        kills = 0;
    }

    public final void update() {
        player.update();

        for (Enemy enemy : enemies) {
            enemy.update();

            if(enemy.pos.distanceTo(player.pos) < 20) {
                player.damage(5);

                Vector2D temp = new Vector2D(player.vel);
                player.vel = enemy.vel;
                enemy.vel = temp;

                enemy.damage(10);
            }

            for (Projectile p : projectiles) {
                if (enemy.containsPoint(p.getPosition())) {
                    enemy.damage(p.getDamage());
                    p.kill();
                }
            }
            
            for(Explosion e : explosions) {
                double damage = 30*e.getDamage() / (1 + e.getPosition().distanceTo(enemy.getPosition()));
                //System.out.println(damage);
                enemy.damage((int) damage);
            }
        }

        for (GameObject go : projectiles) {
            go.update();
        }

        for (Projectile p : enemyProjectiles) {
            p.update();
            if (player.containsPoint(p.getPosition())) {
                player.damage(p.getDamage());
                p.kill();
            }
        }

        for(Item i : items) {
            i.update();
            if(player.pos.distanceTo(i.pos) < 29) {
                if(i.isHealth()) {
                    player.addShields(i.getAmount());
                    ViewFrame.getInstance().setHealth(player.getShields());
                } else {
                    player.addAmmo(i.getAmount(), i.getWeaponType());
                }
                i.kill();
            }
        }

        for (Explosion ex : explosions) {
            ex.update();
        }

        if (spawnEvent <= 0 && enemies.size() < 1 + (kills / 15)) {
            Dimension size = ViewFrame.size;
            
            boolean b = random.nextBoolean();
            if (b) {
                enemies.add(new Enemy(new Point(random.nextInt(size.width), 0)));
            } else {
                enemies.add(new Enemy(new Point(0, random.nextInt(size.height))));
            }
            
            spawnEvent = 70 + random.nextInt(Math.max(1, 250 - kills));
        } else {
            spawnEvent--;
            spawnEvent = (spawnEvent < 0 )? (200-kills) : spawnEvent;
        }

        if(player.getShields() <= 0) {
            JOptionPane.showMessageDialog(null, "Game Over. Final Score: "+kills);
            newGame();
        }
    }

    public final void draw(Graphics g)
    {
        Iterator<Enemy> en = enemies.iterator();
        Enemy e;
        while (en.hasNext()) {
            e = en.next();
            if (e.isDead()) {
                addExplosion(new EnemyExplosion(e.pos));
                if(random.nextBoolean()) {
                    items.add(new Item(e.pos, e.vel));
                }
                kills++;
                en.remove();
            } else {
                e.draw(g);
            }
        }

        Iterator<Projectile> iterator = projectiles.iterator();
        Projectile object;
        while (iterator.hasNext()) {
            object = iterator.next();
            if (object.isDead()) {
                iterator.remove();
            } else {
                object.draw(g);
            }
        }

        player.draw(g);

        iterator = enemyProjectiles.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (object.isDead()) {
                iterator.remove();
            } else {
                object.draw(g);
            }
        }

        Iterator<Explosion> exs = explosions.iterator();
        Explosion ex;
        while (exs.hasNext()) {
            ex = exs.next();
            if (ex.isDead()) {
                exs.remove();
            } else {
                ex.draw(g);
            }
        }

        Iterator<Item> it = items.iterator();
        Item i;
        while (it.hasNext()) {
            i = it.next();
            if (i.isDead()) {
                it.remove();
            } else {
                i.draw(g);
            }
        }
    }

    public final void addProjectile(Projectile item) {
        projectiles.add(item);
        
    }

    public final void addProjectiles(List<Projectile> items) {
        projectiles.addAll(items);
    }

    public final void addEnemyProjectile(Projectile item) {
        enemyProjectiles.add(item);
    }

    public final void addExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

    public final void addItem(Item item) {
        items.add(item);
    }

    public final Enemy getEnemy() {
        int size = enemies.size();
        if (size < 1) {
            return null;
        }

        enemyIndex++;
        if (enemyIndex >= enemies.size()) {
            enemyIndex = 0;
        }
        return enemies.get(enemyIndex);
    }

    public final Item getItem() {
        int size = items.size();
        if (size < 1) {
            return null;
        }

        itemIndex++;
        if (itemIndex >= items.size()) {
            itemIndex = 0;
        }
        return items.get(itemIndex);
    }
}
