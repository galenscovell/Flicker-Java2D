
/**
 * GAMEPANEL CLASS
 * Handles game loop updating/rendering within game JPanel as well as player input.
 */

package ui;

import logic.Camera;
import logic.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
    final int FPS = 30;
    private boolean running;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private World world;
    private Camera camera;
    private Thread thread;


    public GamePanel(int panelWidth, int panelHeight, int tileSize) {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.world = new World(1000, 1000, tileSize);
        this.camera = new Camera(world.getTiles(), tileSize, panelWidth, panelHeight);

        // Setup player input bindings
        getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        getInputMap().put(KeyStroke.getKeyStroke("released UP"), "releaseUp");
        getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "releaseDown");
        getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "releaseLeft");
        getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "releaseRight");

        getActionMap().put("moveUp", moveUp);
        getActionMap().put("moveDown", moveDown);
        getActionMap().put("moveLeft", moveLeft);
        getActionMap().put("moveRight", moveRight);

        getActionMap().put("releaseUp", releaseUp);
        getActionMap().put("releaseDown", releaseDown);
        getActionMap().put("releaseLeft", releaseLeft);
        getActionMap().put("releaseRight", releaseRight);
    }

    Action moveUp = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            upPressed = true;
        }
    };

    Action moveDown = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            downPressed = true;
        }
    };

    Action moveLeft = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            leftPressed = true;
        }
    };

    Action moveRight = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            rightPressed = true;
        }
    };

    Action releaseUp = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            upPressed = false;
        }
    };

    Action releaseDown = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            downPressed = false;
        }
    };

    Action releaseLeft = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            leftPressed = false;
        }
    };

    Action releaseRight = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            rightPressed = false;
        }
    };

    private int[] checkInput() {
        int[] directions = new int[2];
        if (upPressed) {
            directions[1]--;
        }
        if (downPressed) {
            directions[1]++;
        }
        if (leftPressed) {
            directions[0]--;
        }
        if (rightPressed) {
            directions[0]++;
        }
        return directions;
    }

    public void run() {
        long start, end, sleepTime;
        int[] inputDirections;

        while (running) {
            start = System.currentTimeMillis();

            inputDirections = checkInput();
            camera.playerMove(inputDirections[0], inputDirections[1]);

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
        Graphics2D gfx = (Graphics2D) g;
        super.paintComponent(gfx);

        // Clear screen
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, getWidth(), getHeight());
        // Render next frame
        camera.render(gfx);
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Display");

        int smoothTicks = 4;
        while (smoothTicks > 0) {
            world.update();
            smoothTicks--;
        }
        camera.placePlayer();

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