
package ui;

import core.AutoPilot;
import core.Game;
import core.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 30, 2009 9:07:02 PM
 */
public class ViewFrame extends JFrame implements ActionListener
{
    public static final Dimension size = new Dimension(600,600);
    private static final ViewFrame instance = new ViewFrame();
    public static ViewFrame getInstance() {return instance;}

    private final Timer timer;
    private final SpacePanel spacePanel;

    private JLabel weaponLabel;
    private JTextField messageLabel;
    private JLabel ammoLabel;
    private JLabel autoPilotLabel;
    private JProgressBar ammoBar, shieldBar;

    private ViewFrame()
    {
        spacePanel = new SpacePanel();
        getContentPane().setBackground(Color.BLACK);
        add(spacePanel, BorderLayout.CENTER);

        // top message label:
        JPanel panel;
        Border b = BorderFactory.createLineBorder(Color.DARK_GRAY);
        messageLabel = new JTextField(20);
        messageLabel.setForeground(Color.GREEN);
        messageLabel.setBackground(Color.BLACK);
        messageLabel.setEditable(false);
        messageLabel.setFocusable(false);

        // info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setBackground(Color.BLACK);
        infoPanel.setBorder(b);

        // weapons display
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c  = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,5,5);
        panel.setBorder(BorderFactory.createTitledBorder(b, "weapon systems"));
        panel.setBackground(Color.BLACK);

        c.gridx = 0; c.gridy = 0;
        JLabel label = new JLabel("weapon: ");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        panel.add(label, c);

        c.gridx = 1; c.gridy = 0;
        weaponLabel = new JLabel();
        weaponLabel.setBackground(Color.BLACK);
        weaponLabel.setForeground(Color.lightGray);
        panel.add(weaponLabel, c);

        c.gridx = 0; c.gridy = 1;
        label = new JLabel("rounds: ");
        label.setForeground(Color.GRAY);
        label.setBackground(Color.RED);
        panel.add(label, c);

        c.gridx = 1; c.gridy = 1;
        ammoBar = new JProgressBar();
        ammoBar.setForeground(new Color(100,100,150));
        ammoBar.setBackground(Color.BLACK);
        ammoBar.setStringPainted(true);
        panel.add(ammoBar, c);

        c.gridx = 0; c.gridy = 2;
        label = new JLabel("ammunition: ");
        label.setForeground(Color.GRAY);
        label.setBackground(Color.RED);
        panel.add(label, c);

        c.gridx = 1; c.gridy = 2;
        ammoLabel = new JLabel();
        ammoLabel.setText("0");
        ammoLabel.setForeground(Color.GRAY);
        panel.add(ammoLabel, c);

        infoPanel.add(panel);

        // shield and health panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        subPanel.setBorder(BorderFactory.createTitledBorder(b, "Onboard Computer"));
        subPanel.setBackground(Color.BLACK);

        c.gridx = 0; c.gridy = 0;
        label = new JLabel("auto pilot: ");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        subPanel.add(label, c);

        c.gridx = 1; c.gridy = 0;
        autoPilotLabel = new JLabel("");
        autoPilotLabel.setBackground(Color.BLACK);
        autoPilotLabel.setForeground(Color.GREEN);
        subPanel.add(autoPilotLabel, c);

        c.gridx = 0; c.gridy = 1;
        label = new JLabel("status: ");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        subPanel.add(label, c);

        c.gridx = 1; c.gridy = 1;
        subPanel.add(messageLabel, c);

        panel.add(subPanel);

        subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        subPanel.setBackground(Color.BLACK);
        subPanel.setBorder(BorderFactory.createTitledBorder(b, "defense system"));

        c.gridx = 0; c.gridy = 0;
        label = new JLabel("shields: ");
        label.setBackground(Color.BLACK);
        label.setForeground(Color.GRAY);
        subPanel.add(label, c);

        c.gridx = 1; c.gridy = 0;
        shieldBar = new JProgressBar();
        shieldBar.setBackground(Color.BLACK);
        shieldBar.setStringPainted(true);
        shieldBar.setMaximum(255);
        setHealth(255);
        subPanel.add(shieldBar, c);

        panel.add(subPanel);
        
        infoPanel.add(panel);

        add(infoPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                dispose();
                System.exit(0);
            }}
        );

        //frame properties:
        //setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Prototype");
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        setLocation(100,100);
        setResizable(false);
        setVisible(true);
        pack();

        //setMessage("all systems online");
        Player player = spacePanel.game.getPlayer();
        addKeyListener(player);
        setWeapon(player.getWeapon().toString());
        setAmmo(1,1,1);
        
        timer = new Timer(45, this);
        timer.start();
    }

    public final void setMessage(final String message) {
        messageLabel.setText(message);
    }

    public final void setAutoPilotLabel(AutoPilot.SystemStatus status) {
        switch(status)
        {
            case ON:
                autoPilotLabel.setForeground(Color.CYAN);
                autoPilotLabel.setText("ON");
                break;
            case OFF:
                autoPilotLabel.setForeground(Color.RED);
                autoPilotLabel.setText("OFF");
                break;
            default:
                autoPilotLabel.setForeground(Color.YELLOW);
                autoPilotLabel.setText("DAMAGED");
        }
    }

    public final void setWeapon(String weapon) {
        weaponLabel.setText(weapon);
    }

    public final void setAmmo(int rounds, int clipSize, int ammo) {

        ammoLabel.setText(""+ammo);
        ammoBar.setMaximum(clipSize);
//        int color = (int)(rounds*255d/clipSize);
//        ammoBar.setForeground(new Color(255-color, color, 0));
        ammoBar.setValue(rounds);
    }

    public final void setHealth(int health) {
        if(health < 0) health = 0;
        shieldBar.setForeground(new Color(255 - health,health,0));
        shieldBar.setValue(health);
    }

    public final void actionPerformed(ActionEvent ae)
    {
        spacePanel.update();
        spacePanel.repaint();
    }

    public final void pause() {
        if(timer.isRunning()) {
            timer.stop();

            setTitle("Prototype - paused");
            Graphics  g = spacePanel.getGraphics();
            g.setFont(new Font("Courier New", Font.PLAIN, 16));
            g.setColor(Color.GREEN);
            int h = 20;
            g.drawString("P - PAUSE",  h, h);
            g.drawString("A - AUTO PILOT",  h, h + 15);
            g.drawString("D - CYCLE TARGET",  h, h + 30);
            g.drawString("Q - CYCLE WEAPON",  h, h + 45);
            g.drawString("R - RELOAD",  h, h + 60);
            g.drawString("SPACE - FIRE",  h, h + 75);

            g.dispose();
            
        } else {
            setTitle("Prototype");
            timer.restart();
        }
    }

    public final Game getGame() {return spacePanel.game;}

    private class SpacePanel extends JPanel
    {
        private final Game game;
        //private final BufferedImage background;

        public SpacePanel()
        {
            //background = ImageManager.getBufferedImage("space.bmp");
            setDoubleBuffered(true);
            setBackground(Color.BLACK);
            game = new Game();
        }

        @Override
        public Dimension getPreferredSize() {return size;}

        public final void update()
        {
            game.update();
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            //g.drawImage(background, 0, 0, null);
            game.draw(g);
        }
    }
}
