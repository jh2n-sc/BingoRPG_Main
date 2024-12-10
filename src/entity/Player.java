package entity;

import interact.Interactable;
import mains.KeyboardListener;
import mains.Panel;
import objects.ParentObject;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import bingo.*;


public class Player extends Entity {
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    public int luck = 0;
    public int coins = 6;
    public boolean neverInteract = false;
    public int[][] bingoCard = {
        {11, 16, 33, 46, 61},
        {12, 17, 34, 47, 62},
        {13, 18, 0, 48, 63},
        {14, 19, 36, 49, 64},
        {15, 20, 37, 50, 65}
    }
    ;
    public boolean[][] markCard = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}
    };


    public final int screenPosX;
    public final int screenPosY;
    public String lastDirection;
    public final int xOffset = 8;
    public final int yOffset = 16;
    final double putHypotenuse = 0.41;
    BufferedImage image;

    KeyboardListener keyboardListener;
    int counter = 0;

    public Player(Panel gp, KeyboardListener keyboardListener) {
        super(gp);
        this.entityPosX = Panel.TILE_SIZE * 27;
        this.entityPosY = Panel.TILE_SIZE * 15;
        this.screenPosX = gp.SCREEN_WIDTH / 2 - Panel.TILE_SIZE;
        this.screenPosY = gp.SCREEN_HEIGHT / 2 - Panel.TILE_SIZE;
        this.keyboardListener = keyboardListener;
        this.entitySpeed = 8;
        this.variableEntitySpeed = entitySpeed;
        this.bobCounter = 0;
        this.numWalk = 0;
        this.isCollisionOn = false;
        this.direction = "right";
        this.collisionBox = new Rectangle(xOffset, yOffset, 30, 30);
        this.cantGoDirection = "false";
        this.lastDirection = "right";
        getEntitySprites();
    }


    @Override
    public void getEntitySprites() {
        try {
            this.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_walk2.png")));
            this.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_walk1.png")));
            this.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_walk1.png")));
            this.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_walk2.png")));
            this.stillRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_still_right.png")));
            this.stillLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_still_left.png")));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load player", e);

        }
    }

    public void render(Graphics2D g2D) {
        if(gp.gameMode == 1) {
            if (this.lastDirection.equals("left")) {
                if (numWalk == 0) {
                    image = left1;
                } else {
                    image = left2;
                }
            } else {
                if (numWalk == 0) {
                    image = right1;
                } else {
                    image = right2;
                }
            }

            if (!keyboardListener.isKeyboardPressed()) {
                if (lastDirection.equals("left")) {
                    image = stillLeft;
                } else {
                    image = stillRight;
                }
            }
        }

        g2D.drawImage(image, screenPosX, screenPosY, Panel.TILE_SIZE, Panel.TILE_SIZE, null);
        if (gp.keyboardListener.isDebugPressed()) {
            g2D.setColor(Color.RED);
            g2D.drawRect(
                screenPosX + xOffset,
                screenPosY + yOffset,
                collisionBox.width,
                collisionBox.height);
        }
        // for drawing hit box
    }


    public void updatePos() {
        isCollisionOn = false;
        cantGoDirection = "false";

        if (keyboardListener.isWPressed()) {
            direction = "up";
            if (!gp.collisionDetector.checkForCollision(this, "up").equals("up")) {
                entityPosY -= variableEntitySpeed;
                this.direction = "up";
            }
        }
        if (keyboardListener.isAPressed()) {
            if (!gp.collisionDetector.checkForCollision(this, "left").equals("left")) {
                entityPosX -= variableEntitySpeed;
                this.direction = "left";
                this.lastDirection = this.direction;
            }
        }
        if (keyboardListener.isSPressed()) {
            if (!gp.collisionDetector.checkForCollision(this, "down").equals("down")) {
                entityPosY += variableEntitySpeed;
                this.direction = "down";
            }
        }
        if (keyboardListener.isDPressed()) {
            if (!gp.collisionDetector.checkForCollision(this, "right").equals("right")) {
                this.entityPosX += variableEntitySpeed;
                this.direction = "right";
                this.lastDirection = this.direction;
            }
        }
        int index = gp.collisionDetector.isCollidingWithObj(this, true);
        pickUpObj(index);

        int npcIndex = gp.collisionDetector.checkEntityCollision(this, gp.npc);
        interactNpcIndex(npcIndex);

        if (gp.collisionDetector.checkForCollision(this, "down").equals("down") &&
            gp.collisionDetector.checkForCollision(this, "right").equals("right") &&
            gp.collisionDetector.checkForCollision(this, "up").equals("up") && direction.equals("right")) {
            entityPosX -= entitySpeed;
        } else if (gp.collisionDetector.checkForCollision(this, "down").equals("down") &&
            gp.collisionDetector.checkForCollision(this, "left").equals("left") &&
            gp.collisionDetector.checkForCollision(this, "right").equals("right") && direction.equals("down")) {
            entityPosY -= entitySpeed;
        }

        //this.direction = "false";

        if ((keyboardListener.isWPressed() || keyboardListener.isSPressed()) &&
            (keyboardListener.isAPressed() || keyboardListener.isDPressed())) {
            variableEntitySpeed = entitySpeed - (int) (entitySpeed * putHypotenuse);
        } else {
            variableEntitySpeed = entitySpeed;
        }


        counter++;

        if (counter >= 60) {
            counter = 0;
        }
        bobCounter += (entitySpeed / 2);
        if (bobCounter > 40) {
            numWalk = (numWalk == 0) ? 1 : 0;
            bobCounter = 0;
        }
    }

    public void pickUpObj(int index) {
        if (index < 0) {
            return;
        }

        ParentObject newObj = gp.object[index];
        if(newObj instanceof Interactable inter) {
            inter.interact(this);
            String msg;
            gp.object[index] = null;
        }
    }

    public boolean withinRenderDistance(int objectX, int objectY, int tol) {
        return objectX + Panel.TILE_SIZE * tol > entityPosX - screenPosX &&
            objectX - Panel.TILE_SIZE * tol < entityPosX + screenPosX &&
            objectY + Panel.TILE_SIZE * tol > entityPosY - screenPosY &&
            objectY - Panel.TILE_SIZE * tol < entityPosY + screenPosY;
    }

    // checks who the player collided with...
    public void interactNpcIndex(int index) {
        if (index < 0) {
            return;
        }
        Entity tmp = gp.npc[index];
        if (tmp instanceof Interactable inter) {
            inter.interact(this);
        }
    }
}
