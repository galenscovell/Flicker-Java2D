
/**
 * GAMEPANEL CLASS
 * Handles game loop updating/rendering, displays game within panel.
 */

package ui;

import logic.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {
    private int smoothTicks;
    private int framerate;
    private boolean running = false;
    private boolean threadActive;

    private World world;
    private Thread thread;


    public GamePanel(int framerate) {
        this.smoothTicks = 2;
        this.framerate = framerate;
        setPreferredSize(new Dimension(720, 480));
        this.world = new World(720, 480, 8);
    }

    @Override
    public void run() {
        long start, end, sleepTime;

        while (threadActive) {
            start = System.currentTimeMillis();

            if (smoothTicks > 0) {
                world.update();
                smoothTicks--;
            } 

            if (running) {
                
            }

            repaint();
            end = System.currentTimeMillis();
            // Sleep to match FPS limit
            sleepTime = (1000 / framerate) - (end - start);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); 
                } catch (InterruptedException e) {
                    thread.interrupt();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gfx = (Graphics2D) g;

        // Clear screen
        gfx.setColor(new Color(0x2c3e50));
        gfx.fillRect(0, 0, getWidth(), getHeight());
        // Render next frame
        world.render(gfx);
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Display");
        this.threadActive = true;
        thread.start(); // call run()
    }

    public synchronized void stop() {
        running = false;
        threadActive = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }

    public void pause() {
        if (running) {
            running = false;
        } else {
            running = true;
        }
    }
}