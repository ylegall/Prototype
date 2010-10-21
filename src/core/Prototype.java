
package core;

import javax.swing.SwingUtilities;
import ui.ViewFrame;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 30, 2009 9:06:02 PM
 */
public class Prototype
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                ViewFrame.getInstance();
            }
        });
    }
}
