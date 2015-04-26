
/**
 * MAINFRAME CLASS
 * Main frame for application.
 */

package ui;

import util.Constants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainFrame implements Runnable {
    private JFrame frame;
    private HUDPanel hud;


    public void run() {
        this.frame = new JFrame("Flicker");
        frame.setPreferredSize(new Dimension(Constants.WINDOW_X, Constants.WINDOW_Y));
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
        this.hud = new HUDPanel();
        GamePanel gamePanel = new GamePanel(this);
        container.add(hud, BorderLayout.PAGE_START);
        container.add(gamePanel, BorderLayout.PAGE_END);
        gamePanel.start();
    }
}