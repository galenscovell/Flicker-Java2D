
/**
 * UPDATER CLASS
 * Handles Entity logic.
 */

package logic;

import entities.Entity;
import entities.Player;

import java.util.List;
import java.util.Random;


public class Updater {
    private int tileSize;
    private List<Tile> tiles;


    public Updater(List<Tile> tiles, int tileSize) {
        this.tiles = tiles;
        this.tileSize = tileSize;
    }

    public void update(List<Entity> entities, Player player) {
        for (Entity entity : entities) {
            if (entity.isInView()) {
                chaseMove(entity, player);
            } else {
                exploreMove(entity);
            }
        }
    }

    private void exploreMove(Entity entity) {
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

    private void chaseMove(Entity entity, Player player) {
        int playerX = (player.getX() / tileSize);
        int playerY = (player.getY() / tileSize);
        int entityX = (entity.getX() / tileSize);
        int entityY = (entity.getY() / tileSize);
        int diffX = (entityX - playerX);
        int diffY = (entityY - playerY);

        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;

        if ((diffX == 0 && (diffY == 1 || diffY == -1)) || (diffY == 0 && (diffX == 1 || diffX == -1))) {
            attackMove(entity, player);
        }

        Tile upTile = findTile(entityX, entityY - 1);
        Tile downTile = findTile(entityX, entityY + 1);
        if (diffY >= 1 && upTile.isFloor() && !upTile.isOccupied()) {
                up = true;
        } else if (diffY <= -1 && downTile.isFloor() && !downTile.isOccupied()) {
                down = true;
        }

        Tile leftTile = findTile(entityX - 1, entityY);
        Tile rightTile = findTile(entityX + 1, entityY);
        if (diffX >= 1 && leftTile.isFloor() && !leftTile.isOccupied()) {
                left = true;
        } else if (diffX <= -1 && rightTile.isFloor() && !rightTile.isOccupied()) {
                right = true;
        }

        int dx = 0;
        int dy = 0;
        Random generator = new Random();
        int choice = generator.nextInt(2);

        Tile currentTile = findTile(entityX, entityY);
        if (choice == 0 && up) {
            currentTile.toggleOccupied();
            dy--;
            upTile.toggleOccupied();
        } else if (choice == 0 && down) {
            currentTile.toggleOccupied();
            dy++;
            downTile.toggleOccupied();
        } else if (choice == 1 && left) {
            currentTile.toggleOccupied();
            dx--;
            leftTile.toggleOccupied();
        } else if (choice == 1 && right) {
            currentTile.toggleOccupied();
            dx++;
            rightTile.toggleOccupied();
        }
        entity.move(dx * tileSize, dy * tileSize, true);
    }

    private void attackMove(Entity entity, Player player) {
        System.out.println("Salamander attacks!");
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