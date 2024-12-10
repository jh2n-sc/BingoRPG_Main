package entity;

import mains.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int entityPosX;
    public int entityPosY;
    public int entitySpeed;
    public int variableEntitySpeed;
    public String cantGoDirection;
    public boolean isCollisionOn;
    public Rectangle collisionBox;
    public Panel gp;
    public int cont = 0;
    BufferedImage image;

    public String direction;
    protected BufferedImage right1, right2, left1, left2, stillRight, stillLeft;
    protected int numWalk;
    protected int bobCounter;
    public int counter;
    public String name;
    public Entity() {

    }
    public Entity (Panel gp) {
        this.gp = gp;
    }
    public void setPanel(Panel gp) {
        this.gp = gp;
    }
    public void updatePos () {
        if(gp.gameMode != 1) {
            return;
        }
        if (cont >= 120) {
            setAction();
            cont = 0;
        }
        cont++;
        isCollisionOn = false;
        gp.collisionDetector.checkTile(this);

        isCollisionOn = false;
        cantGoDirection = "false";

        if (direction.equals("up")) {
            direction = "up";
            if (!gp.collisionDetector.checkForCollision(this, "up").equals("up")) {
                entityPosY -= entitySpeed;
            }
            else {
                setAction();
            }
        }
        else if (direction.equals("down")) {
            if (!gp.collisionDetector.checkForCollision(this, "left").equals("left")) {
                entityPosY += entitySpeed;
            }
            else {
                setAction();
            }
        }
        else if (direction.equals("left")) {
            if (!gp.collisionDetector.checkForCollision(this, "down").equals("down")) {
                entityPosX -= entitySpeed;
            }
            else {
                setAction();
            }
        }
        else if (direction.equals("right")) {
            if (!gp.collisionDetector.checkForCollision(this, "right").equals("right")) {
                entityPosX += entitySpeed;
            }
            else {
                setAction();
            }
        }
        int index = gp.collisionDetector.isCollidingWithObj(this, true);

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
    public void getEntitySprites() {};
    public void render(Graphics2D g2d) {
        int screenX = entityPosX - gp.player.entityPosX + gp.player.screenPosX;
        int screenY = entityPosY - gp.player.entityPosY + gp.player.screenPosY;

        if (gp.player.withinRenderDistance(entityPosX, entityPosY ,10)) {
            if (gp.gameMode == 1) {
                image = right1;
                if (this.direction.equals("left")) {
                    if (numWalk >= 0) {
                        image = left1;
                    } else {
                        image = left2;
                    }
                    if (direction.equals("none")) {}
                } else {
                    if (numWalk == 0) {
                    } else {
                        image = stillRight;
                    }
                }

                if (gp.keyboardListener.isKeyboardPressed()) {
                    if (direction.equals("left")) {
                        image = stillLeft;
                    } else {
                        image = stillRight;
                    }
                }
            }
            g2d.drawImage(image,
                screenX,
                screenY,
                Panel.TILE_SIZE,
                Panel.TILE_SIZE,
                null);
            if (gp.keyboardListener.isDebugPressed()) {
                g2d.setColor(Color.RED);
                g2d.drawRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
            }
        }
    }
    public void setAction() {

    }
}
