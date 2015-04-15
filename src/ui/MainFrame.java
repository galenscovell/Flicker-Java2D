
/**
 * MAINFRAME CLASS
 * Main frame for application.
 * This class also is the sole creator of PlayerStats used throughout the application.
 */

package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame implements Runnable {
    private int windowX, windowY;
    private JFrame frame;
    public static HUDPanel hud;
    public static PlayerStats playerStats;


    public MainFrame(int x, int y) {
        this.windowX = x;
        this.windowY = y;
        playerStats = new PlayerStats();
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
        GamePanel gamePanel = new GamePanel(windowX, windowY - 100, 64);
        hud = new HUDPanel(windowX, 100);
        container.add(hud, BorderLayout.PAGE_START);
        container.add(gamePanel, BorderLayout.PAGE_END);
        gamePanel.start();
    }
}