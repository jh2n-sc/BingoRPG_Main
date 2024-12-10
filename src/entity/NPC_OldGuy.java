package entity;

import mains.*;
import mains.Panel;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.io.IOException;
import java.util.Random;

public class NPC_OldGuy extends Entity {

    public NPC_OldGuy(Panel gp) {
        super(gp);

        this.name = "oldGuy";
        this.entitySpeed = 3;
        this.variableEntitySpeed = entitySpeed;
        this.bobCounter = 0;
        this.numWalk = 0;
        this.isCollisionOn = false;
        this.direction = "right";
        this.collisionBox = new Rectangle(8, 16, 30, 30);
        this.cantGoDirection = "false";
        getEntitySprites();
    }

    public void getEntitySprites() {
        try {
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_walk1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_right_walk2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_walk1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_left_walk2.png")));
            stillLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_still_left.png")));
            stillRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/player_still_right.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setAction() {
        Random r = new Random();
        int i = r.nextInt(125) + 1;
        if ( i < 25) {
            direction = "right";
        }
        else if (i > 25 && i < 50) {
            direction = "left";
        }
        else if (i > 50 && i < 75) {
            direction = "up";
        }
        else if (i > 75 && i < 100) {
            direction = "down";
        }
        else {
            direction = "none";
        }
        cont = 0;
    }

}

