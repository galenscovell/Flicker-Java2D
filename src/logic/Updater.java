
/**
 * UPDATER CLASS
 * Handles Entity logic.
 */

package logic;

import entities.Entity;

import java.util.List;
import java.util.Random;


public class Updater {
    private int tileSize;
    private List<Tile> tiles;


    public Updater(List<Tile> tiles, int tileSize) {
        this.tiles = tiles;
        this.tileSize = tileSize;
    }

    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.isInView()) {
                randomMove(entity);
            } else {
                randomMove(entity);
            }
        }
    }

    private void randomMove(Entity entity) {
        int dx, dy;
        int entityX = (entity.getX() / tileSize);
        int entityY = (entity.getY() / tileSize);

        Random generator = new Random();
        int choice = generator.nextInt(5);
        if (choice == 0) {
            dx = generator.nextInt(3) - 1;
            dy = 0;
        } else if (choice == 1) {
            dx = 0;
            dy = generator.nextInt(3) - 1;
        } else {
            dx = 0;
            dy = 0;
        }

        Tile nextTile = findTile(entityX + dx, entityY + dy);
        if (nextTile.isFloor() && !nextTile.isOccupied()) {
            // If possible, move to new Tile and set old Tile as unoccupied
            Tile currentTile = findTile(entityX, entityY);
            currentTile.toggleOccupied();
            entity.move(dx * tileSize, dy * tileSize, true);
            nextTile.toggleOccupied();
        } else {
            // Otherwise just turn in that direction
            entity.move(dx * tileSize, dy * tileSize, false);
        }
    }

    private void chaseMove() {

    }

    private Tile findTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.x == x && tile.y == y) {
                return tile;
            }
        }
        return null;
    }
}