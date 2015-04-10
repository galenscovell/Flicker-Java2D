
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
    final float TIMESTEP = 1.0f / 10.0f;
    final float FRAMERATE = 1.0f / 60.0f;

    private boolean running;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    private World world;
    private Camera camera;
    private Thread thread;


    public GamePanel(int panelWidth, int panelHeight, int tileSize) {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setDoubleBuffered(true);
        this.world = new World(2000, 2000, tileSize);
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
        int[] direction = new int[2];
        if (upPressed) {
            direction[1]--;
        } else if (downPressed) {
            direction[1]++;
        } else if (leftPressed) {
            direction[0]--;
        } else if (rightPressed) {
            direction[0]++;
        }
        return direction;
    }

    public void run() {
        double updateAccumulator = 0.0f;
        double renderAccumulator = 0.0f;
        long oldTime = System.nanoTime();
        long currentTime = System.nanoTime();
        double deltaSeconds;
        int[] inputDirection;

        while (running) {
            currentTime = System.nanoTime();
            deltaSeconds = (double) (currentTime - oldTime) / 1000000000;
            oldTime = currentTime;

            updateAccumulator += deltaSeconds;
            while (updateAccumulator > TIMESTEP) {
                inputDirection = checkInput();
                camera.playerMove(inputDirection[0], inputDirection[1]);
                updateAccumulator -= TIMESTEP;
            }
            
            renderAccumulator += deltaSeconds;
            if (renderAccumulator > FRAMERATE) {
                repaint();
                renderAccumulator = 0.0f;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gfx = (Graphics2D) g;
        super.paintComponent(gfx);

        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, getWidth(), getHeight());

        camera.render(gfx);
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Display");

        int smoothTicks = 6;
        while (smoothTicks > 0) {
            world.update();
            smoothTicks--;
        }
        world.skin();
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