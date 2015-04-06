
/**
 * Main entry for Flicker application.
 */

import ui.MainFrame;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        MainFrame mainframe = new MainFrame(960, 640);
        SwingUtilities.invokeLater(mainframe);
    }
}