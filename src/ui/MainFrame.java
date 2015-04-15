
/**
 * MAINFRAME CLASS
 * Main frame for application.
 */

package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame implements Runnable {
    private int windowX, windowY;
    private JFrame frame;
    private HUDPanel hud;


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

    public HUDPanel getHud() {
        return hud;
    }

    private void createComponents(Container container) {
        hud = new HUDPanel(windowX, 100);
        GamePanel gamePanel = new GamePanel(windowX, windowY - 100, 64, this);
        container.add(hud, BorderLayout.PAGE_START);
        container.add(gamePanel, BorderLayout.PAGE_END);
        gamePanel.start();
    }
}