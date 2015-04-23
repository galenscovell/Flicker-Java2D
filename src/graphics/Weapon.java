
/**
 * WEAPON CLASS
 * Handles loading of weapon sprites for attack animations.
 */

package graphics;

import java.awt.Graphics2D;


public class Weapon {
    private int x, y, playerX, playerY;
    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] upSprites, downSprites, leftSprites, rightSprites;
    private int frame;


    public Weapon(String type) {
        SpriteSheet sheet = SpriteSheet.charsheet;

        if (type.equals("pickaxe")) {
            this.upSprites = new Sprite[3];
            this.downSprites = new Sprite[3];
            this.leftSprites = new Sprite[3];
            this.rightSprites = new Sprite[3];

            upSprites[0] = new Sprite(sheet, 67);
            upSprites[1] = new Sprite(sheet, 68);
            upSprites[2] = new Sprite(sheet, 64);

            downSprites[0] = new Sprite(sheet, 65);
            downSprites[1] = new Sprite(sheet, 69);
            downSprites[2] = new Sprite(sheet, 66);

            leftSprites[0] = new Sprite(sheet, 64);
            leftSprites[1] = new Sprite(sheet, 70);
            leftSprites[2] = new Sprite(sheet, 65);

            rightSprites[0] = new Sprite(sheet, 66);
            rightSprites[1] = new Sprite(sheet, 71);
            rightSprites[2] = new Sprite(sheet, 67);
        }
    }

    public int getFrame() {
        return frame;
    }

    public void incrementFrame() {
        frame++;
    }

    public void resetFrame() {
        frame = 0;
    }

    public void setDirection(String dir) {
        if (dir.equals("up")) {
            currentSet = upSprites;
        } else if (dir.equals("down")) {
            currentSet = downSprites;
        } else if (dir.equals("left")) {
            currentSet = leftSprites;
        } else if (dir.equals("right")) {
            currentSet = rightSprites;
        }
    }

    public void setPosition(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public void draw(Graphics2D gfx, int tileSize, int frame) {
        findCoords(frame, tileSize);
        gfx.drawImage(currentSet[frame].getSprite(), x, y, tileSize, tileSize, null);
    }

    private void findCoords(int frame, int tileSize) {
        int halfTile = tileSize / 2;
        int quarterTile = tileSize / 4;
        this.x = playerX;
        this.y = playerY;

        if (currentSet == upSprites) {
            if (frame == 0) {
                x += halfTile;
                y -= quarterTile;
            } else if (frame == 1) {
                y -= halfTile;
            } else {
                x -= halfTile;
                y -= quarterTile;
            }
        } else if (currentSet == downSprites) {
            if (frame == 0) {
                x -= halfTile;
                y += quarterTile;
            } else if (frame == 1) {
                y += halfTile;
            } else {
                x += halfTile;
                y += quarterTile;
            }
        } else if (currentSet == leftSprites) {
            if (frame == 0) {
                x -= quarterTile;
                y -= halfTile;
            } else if (frame == 1) {
                x -= halfTile;
            } else {
                x -= quarterTile;
                y += halfTile;
            }
        } else if (currentSet == rightSprites) {
            if (frame == 0) {
                x += quarterTile;
                y += halfTile;
            } else if (frame == 1) {
                x += halfTile;
            } else {
                x += quarterTile;
                y -= halfTile;
            }
        }
    }
}