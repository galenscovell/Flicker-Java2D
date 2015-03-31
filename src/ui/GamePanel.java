
/**
 * GAMEPANEL CLASS
 * Handles game loop updating/rendering within game JPanel.
 */

package ui;

import logic.Camera;
import logic.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {
    final int FPS = 20;
    private boolean running;

    private World world;
    private Camera camera;
    private Thread thread;


    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        this.world = new World(2000, 2000, 10);
        this.camera = new Camera(world.getTiles(), world.getGrid(), 10, 800, 600);
    }

    @Override
    public void run() {
        long start, end, sleepTime;
        int smoothTicks = 4;

        while (running) {
            start = System.currentTimeMillis();

            if (smoothTicks > 0) {
                world.update();
                smoothTicks--;
            } else {
                camera.playerMove();
            }

            repaint();

            end = System.currentTimeMillis();
            // Sleep to match FPS limit
            sleepTime = (1000 / FPS) - (end - start);
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
        camera.render(gfx);
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Display");
        this.running = true;
        thread.start(); // call run()
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }
}