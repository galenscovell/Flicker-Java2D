
/**
* CREATURE SUPERCLASS
*/

package entities;

import graphics.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;


public class Creature implements Entity {
    private int x, y, prevX, prevY;
    private int spriteNumber, waitFrames, tileSize;
    private boolean inView;
    private boolean isAttacking;

    protected Sprite sprite;
    protected Sprite[] currentSet;
    protected Sprite[] leftSprites, rightSprites;

    protected int speed, strength;
    private int moveTime;


    public Creature(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.spriteNumber = 0;
        this.waitFrames = 20;
        this.moveTime = 0;
        this.tileSize = tileSize;
    }

    public void move(int dx, int dy, boolean possible) {
        if (dx < 0) {
            currentSet = leftSprites;
        } else if (dx > 0) {
            currentSet = rightSprites;
        }

        if (possible) {
            animate(currentSet);
            x += dx;
            y += dy;
        }
    }

    public void draw(Graphics2D gfx, double interpolation) {
        animate(currentSet);
        // When interpolation is 1, movement animation is complete
        if (interpolation == 1) {
            prevX = x;
            prevY = y;
            gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
            return;
        }
        int currentX = (int) (prevX + ((x - prevX) * interpolation));
        int currentY = (int) (prevY + ((y - prevY) * interpolation));
        
        gfx.drawImage(sprite.getSprite(), currentX, currentY, tileSize, tileSize, null);
    }

    public void attack(Graphics2D gfx, double interpolation, Player player) {
        int diffX = player.getX() - x;
        int diffY = player.getY() - y;
        if (diffX > 0) {
            currentSet = rightSprites;
        } else if (diffX < 0) {
            currentSet = leftSprites;
        }
        animate(currentSet);
        int currentX = (int) (prevX + (diffX * interpolation));
        int currentY = (int) (prevY + (diffY * interpolation));
        
        // Attack animation only covers half of player's tile
        if (interpolation >= 0.6) {
            toggleAttacking();
        } else {
            gfx.drawImage(sprite.getSprite(), currentX, currentY, tileSize, tileSize, null);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void toggleInView() {
        if (inView) {
            inView = false;
        } else {
            inView = true;
        }
    }

    public boolean isInView() {
        return inView;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void toggleAttacking() {
        if (isAttacking) {
            isAttacking = false;
        } else {
            isAttacking = true;
        }
    }

    public boolean isMoveTime() {
        return moveTime == speed;
    }

    public void resetMoveTime() {
        moveTime = 0;
    }

    public void incrementMoveTime() {
        moveTime++;
    }

    private void animate(Sprite[] currentSet) {
        if (waitFrames == 0) {
            spriteNumber++;
            waitFrames = 20;
            if (spriteNumber > 1) {
                spriteNumber = 0;
            }
        } else {
            waitFrames--;
        }
        sprite = currentSet[spriteNumber];
    }
}