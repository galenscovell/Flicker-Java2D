
/**
 * GAMEPANEL CLASS
 * Handles game thread, game loop calls to updater/renderer, and player input key-bindings.
 */

package ui;

import logic.Renderer;
import logic.Updater;
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
    final int FRAMERATE = 60;
    final int TIMESTEP = 12;
    private double interpolation;

    final int worldWidth = 3200;
    final int worldHeight = 3200;

    private boolean running;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    private World world;
    private Thread thread;
    private Renderer renderer;
    private Updater updater;


    public GamePanel(int panelWidth, int panelHeight, int tileSize, MainFrame root) {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setDoubleBuffered(true);
        this.world = new World(worldWidth, worldHeight, tileSize);
        this.renderer = new Renderer(world.getTiles(), tileSize, panelWidth, panelHeight);
        this.updater = new Updater(world.getTiles(), tileSize, worldWidth, root.getHud());

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
        long startTime, endTime, sleepTime;
        int[] inputDirection;
        int updateAccumulator = 0;

        while (running) {
            updateAccumulator++;
            startTime = System.currentTimeMillis();

            // Player movement and entity logic
            if (updateAccumulator > TIMESTEP) {
                updater.updateEntities(checkInput(), renderer.getEntityList());
                updateAccumulator = 0;
            }

            // Graphics rendering
            interpolation = (double) updateAccumulator / TIMESTEP;
            repaint();

            endTime = System.currentTimeMillis();
            // Sleep to keep graphics rendering at framerate
            sleepTime = (1000 / FRAMERATE) - (endTime - startTime);
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
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, getWidth(), getHeight());
        renderer.render(gfx, interpolation);
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Display");

        int smoothTicks = 6;
        while (smoothTicks > 0) {
            world.update();
            smoothTicks--;
        }
        world.optimizeLayout();
        renderer.placePlayer();
        updater.setPlayer(renderer.getPlayer());
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

    public void pause() {
        if (running) {
            running = false;
        } else {
            running = true;
        }
    }
}