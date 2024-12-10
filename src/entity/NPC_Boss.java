package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import mains.Panel;
import interact.Interactable;

public class NPC_Boss extends Entity implements Interactable {
    private boolean interacted;
    private boolean lastInteract;

    public NPC_Boss() {

    }

    public NPC_Boss(Panel gp) {
        super(gp);
        this.interacted = false;
        this.lastInteract = false;
        this.name = "boss";
        this.entitySpeed = 0;
        this.variableEntitySpeed = entitySpeed;
        this.bobCounter = 0;
        this.numWalk = 0;
        this.isCollisionOn = false;
        this.direction = "right";
        this.collisionBox = new Rectangle(8, 16, 30, 30);
        this.cantGoDirection = "false";
        getEntitySprites();
    }

    @Override
    public void getEntitySprites() {
        try {
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entities/boss.png")));
            right2 = right1;
            left1 = right1;
            left2 = right1;
            stillRight = right1;
            stillLeft = right1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2d) {
        image = right1;
        int screenX = entityPosX - gp.player.entityPosX + gp.player.screenPosX;
        int screenY = entityPosY - gp.player.entityPosY + gp.player.screenPosY;

        if (gp.player.withinRenderDistance(entityPosX, entityPosY, 10)) {

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

    @Override
    public void interact(Player player) {
        if (!interacted) {
            if (!gp.finished) {
                if (player.coins <= 5) {
                    gp.ui.showMessage("Unsufficient Coins!");
                    return;
                }
                player.coins = 0;
                gp.bingoBoss();
            }
            return;
        }
        if (lastInteract) {
            return;
        }

        if (gp.winner) {
            gp.ui.showMessage("You've won already, Im broke now!! :{");
        } else if (!gp.cheated) {
            gp.ui.showMessage("Aww, I'll give you compensation, here's 10 coins");
            player.coins += 10;
        } else  {
            gp.ui.showMessage("I don't like cheaters!");
            player.coins -= 100;
            player.luck -= 999;
        }
        lastInteract = true;
    }
}
