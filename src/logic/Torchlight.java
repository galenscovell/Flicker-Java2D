
/**
 * TORCHLIGHT CLASS
 * Handles torchlight effect surrounding player.
 */

package logic;

import entities.Player;

import java.awt.Color;
import java.awt.Graphics2D;


public class Torchlight {
    private int tileSize;
    private Color lit;
    private Color dim;
    private Color dark;
    private Color darker;
    private Color pitch;

    public Torchlight(int tileSize) {
        this.tileSize = tileSize;
        this.lit = new Color(0.1f, 0.0f, 0.0f, 0.0f);
        this.dim = new Color(0.1f, 0.0f, 0.0f, 0.15f);
        this.dark = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        this.darker = new Color(0.0f, 0.0f, 0.0f, 0.45f);
        this.pitch = new Color(0.0f, 0.0f, 0.0f, 0.7f);
    }

    public void castLight(Graphics2D gfx, Tile tile, Player player) {
        int xDistance = Math.abs(player.getX() - (tile.x * tileSize));
        int yDistance = Math.abs(player.getY() - (tile.y * tileSize));

        // Center tile
        if (xDistance == 0 && yDistance == 0) {
            gfx.setColor(new Color(0.2f, 0.0f, 0.0f, 0.0f));
        // Tiles horizontally in-line with player
        } else if (xDistance == 0) {
            if (yDistance == tileSize) {
                gfx.setColor(lit);
            } else if (yDistance == (tileSize * 2)) {
                gfx.setColor(dim);
            } else if (yDistance == (tileSize * 3)) {
                gfx.setColor(dark);
            } else if (yDistance == (tileSize * 4)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        // Tiles vertically in-line with player
        } else if (yDistance == 0) {
            if (xDistance == tileSize) {
                gfx.setColor(lit);
            } else if (xDistance == (tileSize * 2)) {
                gfx.setColor(lit);
            } else if (xDistance == (tileSize * 3)) {
                gfx.setColor(dim);
            } else if (xDistance == (tileSize * 4)) {
                gfx.setColor(dark);
            } else if (xDistance == (tileSize * 5)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        } else if (xDistance == tileSize) {
            if (yDistance == tileSize) {
                gfx.setColor(lit);
            } else if (yDistance == (tileSize * 2)) {
                gfx.setColor(dim);
            } else if (yDistance == (tileSize * 3)) {
                gfx.setColor(dark);
            } else if (yDistance == (tileSize * 4)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        } else if (yDistance == tileSize) {
            if (xDistance == tileSize) {
                gfx.setColor(lit);
            } else if (xDistance == (tileSize * 2)) {
                gfx.setColor(dim);
            } else if (xDistance == (tileSize * 3)) {
                gfx.setColor(dark);
            } else if (xDistance == (tileSize * 4)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        } else if (xDistance == (tileSize * 2)) {
            if (yDistance == (tileSize * 2)) {
                gfx.setColor(dark);
            } else if (yDistance == (tileSize * 3)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        } else if (yDistance == (tileSize * 2)) {
            if (xDistance == (tileSize * 2)) {
                gfx.setColor(dark);
            } else if (xDistance == (tileSize * 3)) {
                gfx.setColor(darker);
            } else {
                gfx.setColor(pitch);
            }
        } else {
            gfx.setColor(pitch);
        }
        gfx.fillRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
    }
}