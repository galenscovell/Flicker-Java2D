
/**
 * Main entry for Flicker application.
 */

import ui.MainFrame;

import javax.swing.SwingUtilities;


public class Main {
    
    public static void main(String[] args) {
        MainFrame mainframe = new MainFrame(1008, 724);
        SwingUtilities.invokeLater(mainframe);
    }
}