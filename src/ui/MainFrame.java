
/**
 * MAINFRAME CLASS
 * Main frame for application.
 */

package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;


public class MainFrame implements Runnable {
    private int windowX;
    private int windowY;
    private JFrame frame;

    public MainFrame(int x, int y) {
        this.windowX = x;
        this.windowY = y;
    }

    @Override
    public void run() {
        this.frame = new JFrame("Flicker");
        frame.setPreferredSize(new Dimension(windowX, windowY));
        frame.getContentPane().setBackground(new Color(0x2c3e50));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.setLayout(new FlowLayout());
        GamePanel gamePanel = new GamePanel(30);
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.white));
        container.add(gamePanel);
        gamePanel.start();
    }
}