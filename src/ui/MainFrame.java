
/**
 * MAINFRAME CLASS
 * Main frame for application.
 */

package ui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame implements Runnable {
    private int windowX;
    private int windowY;
    private JFrame frame;

    public MainFrame(int x, int y) {
        this.windowX = x;
        this.windowY = y;
    }

    public void run() {
        this.frame = new JFrame("Flicker");
        frame.setPreferredSize(new Dimension(windowX, windowY));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createComponents(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        // New GamePanel: width, height, tileSize
        GamePanel gamePanel = new GamePanel(960, 640, 32);
        container.add(gamePanel);
        gamePanel.start();
    }
}