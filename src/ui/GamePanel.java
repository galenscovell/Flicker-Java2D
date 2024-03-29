
/**
 * GAMEPANEL CLASS
 * Handles game thread, game loop calls to updater/renderer, and player input key-bindings.
 */

package ui;

import entities.Player;

import logic.Renderer;
import logic.Updater;
import logic.World;

import util.Constants;

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
    private double interpolation;

    private boolean running;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean spaceReleased, eReleased;

    private MainFrame root;
    private Player playerInstance;
    private Thread thread;
    
    private World world;
    private Renderer renderer;
    private Updater updater;


    public GamePanel(MainFrame root) {
        this.root = root;
        setPreferredSize(new Dimension(Constants.WINDOW_X, Constants.WINDOW_Y - Constants.HUD_HEIGHT));
        setDoubleBuffered(true);
        this.playerInstance = new Player(0, 0, Constants.TILESIZE);

        // Setup player input bindings
        getInputMap().put(KeyStroke.getKeyStroke("pressed W"), "moveUp");
        getInputMap().put(KeyStroke.getKeyStroke("pressed S"), "moveDown");
        getInputMap().put(KeyStroke.getKeyStroke("pressed A"), "moveLeft");
        getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "moveRight");

        getInputMap().put(KeyStroke.getKeyStroke("released W"), "releaseUp");
        getInputMap().put(KeyStroke.getKeyStroke("released S"), "releaseDown");
        getInputMap().put(KeyStroke.getKeyStroke("released A"), "releaseLeft");
        getInputMap().put(KeyStroke.getKeyStroke("released D"), "releaseRight");

        getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "useWeapon");
        getInputMap().put(KeyStroke.getKeyStroke("released E"), "interact");

        getActionMap().put("moveUp", moveUp);
        getActionMap().put("moveDown", moveDown);
        getActionMap().put("moveLeft", moveLeft);
        getActionMap().put("moveRight", moveRight);

        getActionMap().put("releaseUp", releaseUp);
        getActionMap().put("releaseDown", releaseDown);
        getActionMap().put("releaseLeft", releaseLeft);
        getActionMap().put("releaseRight", releaseRight);

        getActionMap().put("useWeapon", useWeapon);
        getActionMap().put("interact", interact);
    }

    public void run() {
        long startTime, endTime, sleepTime;
        int updateAccumulator = 0;

        while (running) {
            updateAccumulator++;
            startTime = System.currentTimeMillis();

            // Player movement and entity logic
            if (updateAccumulator > Constants.TIMESTEP) {
                updater.update(checkMovement(), spaceReleased, eReleased, renderer.getEntityList(), renderer.getDeadList(), renderer.getInanimateList());
                updateAccumulator = 0;
                spaceReleased = false;
                eReleased = false;

                if (updater.playerDescends()) {
                    renderer.deconstruct();
                    updater.deconstruct();
                    world.deconstruct();
                    System.gc(); // Suggest garbage collection on null references
                    createNewLevel();
                }
            }

            // Graphics rendering
            interpolation = (double) updateAccumulator / Constants.TIMESTEP;
            repaint();

            endTime = System.currentTimeMillis();
            // Sleep to keep graphics rendering at framerate
            sleepTime = (1000 / Constants.FRAMERATE) - (endTime - startTime);
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
        createNewLevel();
        thread.start(); // call run()
    }

    private void createNewLevel() {
        running = false;
        this.world = new World();
        this.renderer = new Renderer(world.getTiles());
        this.updater = new Updater(world.getTiles(), root.getHud());
        
        int smoothTicks = Constants.WORLD_SMOOTHING_PASSES;
        while (smoothTicks > 0) {
            world.update();
            smoothTicks--;
        }

        world.optimizeLayout();
        renderer.assembleLevel(playerInstance);
        updater.setPlayer(playerInstance);
        updater.setStairs(renderer.getInanimateList());
        running = true;
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

    Action useWeapon = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (!(upPressed || downPressed || leftPressed || rightPressed || eReleased)) {
                spaceReleased = true;
            }
        }
    };

    Action interact = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (!(upPressed || downPressed || leftPressed || rightPressed || spaceReleased)) {
                eReleased = true;
            }
        }
    };

    private int[] checkMovement() {
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
}