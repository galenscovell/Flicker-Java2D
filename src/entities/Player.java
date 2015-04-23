
/**
 * PLAYER CLASS
 * Displays player sprite and handles player coordinates.
 */

package entities;

import graphics.Weapon;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Player {
    private int x, y, prevX, prevY, currentX, currentY;
    private int spriteNumber, waitFrames;

    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] upSprites, downSprites, leftSprites, rightSprites;

    private Weapon weapon;
    private boolean weaponPrepared;
    private boolean attacking;


    public Player(int x, int y) {
        this.x = x;
        this.y = y; 
        this.prevX = x;
        this.prevY = y;
        this.currentX = x;
        this.currentY = y;
        setWeapon("pickaxe");

        SpriteSheet sheet = SpriteSheet.charsheet;
        this.upSprites = new Sprite[4];
        this.downSprites = new Sprite[4];
        this.leftSprites = new Sprite[4];
        this.rightSprites = new Sprite[4];

        // Populate sprite animation sets
        for (int i = 0; i < 4; i++) {
            upSprites[i] = new Sprite(sheet, i + 48);
            downSprites[i] = new Sprite(sheet, i);
            leftSprites[i] = new Sprite(sheet, i + 16);
            rightSprites[i] = new Sprite(sheet, i + 32);
        }

        this.currentSet = downSprites;
        this.sprite = currentSet[0];
        this.spriteNumber = 0;
        this.waitFrames = 20;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setWeapon(String type) {
        this.weapon = new Weapon(type);
    }

    public void move(int dx, int dy, boolean possible) {
        if (dy < 0) {
            currentSet = upSprites;
        } else if (dy > 0) {
            currentSet = downSprites;
        } else if (dx < 0) {
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

    public void draw(Graphics2D gfx, int tileSize, double interpolation) {
        animate(currentSet);
        // When interpolation is 1, movement animation is complete
        if (interpolation == 1.0) {
            prevX = x;
            prevY = y;
            gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
            return;
        }
        currentX = (int) (prevX + ((x - prevX) * interpolation));
        currentY = (int) (prevY + ((y - prevY) * interpolation));
        
        gfx.drawImage(sprite.getSprite(), currentX, currentY, tileSize, tileSize, null);
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void toggleAttack() {
        if (attacking) {
            attacking = false;
        } else {
            attacking = true;
        }
    }

    public void attack(Graphics2D gfx, int tileSize, double interpolation) {
        if (!weaponPrepared) {
            String dir;
            if (currentSet == upSprites) {
                dir = "up";
            } else if (currentSet == downSprites) {
                dir = "down";
            } else if (currentSet == leftSprites) {
                dir = "left";
            } else {
                dir = "right";
            }
            weapon.setDirection(dir);
            weapon.setPosition(currentX, currentY);
            weaponPrepared = true;
            weapon.resetFrame();
        }

        int weaponFrame = weapon.getFrame();
        if (weaponFrame <= 3) {
            weapon.draw(gfx, tileSize, 0);
        } else if (weaponFrame > 3 && weaponFrame < 9) {
            weapon.draw(gfx, tileSize, 1);
        } else if (weaponFrame > 9 && weaponFrame < 14) {
            weapon.draw(gfx, tileSize, 2);
        } else if (weaponFrame == 15) {
            weaponPrepared = false;
            toggleAttack();
            return;
        }
        weapon.incrementFrame();
    }

    private void animate(Sprite[] currentSet) {
        if (waitFrames == 0) {
            spriteNumber++;
            waitFrames = 20;
            if (spriteNumber > 3) {
                spriteNumber = 0;
            }
        } else {
            waitFrames--;
        }
        sprite = currentSet[spriteNumber];
    }
}