package objects;

import com.google.gson.annotations.Expose;
import mains.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.imageio.ImageIO;

public class ParentObject {
    private static final Logger LOGGER = Logger.getLogger(ParentObject.class.getName());

    public BufferedImage image;
    public Boolean collision = false;
    public Rectangle collisionBox;

    @Expose
    public String image_path;
    @Expose
    public String name;
    @Expose
    public int objectX, objectY;
    @Expose
    public int objectWidth, objectHeight;
    @Expose
    public int offsetX, offsetY;
    @Expose
    public int colBoxX, colBoxY;
    @Expose
    public int colBoxW, colBoxH;


    public void render(Graphics2D g2d, Panel gamePanel) {

        // no for loops because the for loop for rendering other objects is inside the Panel class

        int screenX = objectX - gamePanel.player.entityPosX + gamePanel.player.screenPosX;
        int screenY = objectY - gamePanel.player.entityPosY + gamePanel.player.screenPosY;

        if (gamePanel.player.withinRenderDistance(objectX, objectY ,4)) {
            g2d.drawImage(image,
                screenX,
                screenY,
                objectWidth * Panel.SCALE,
                objectHeight * Panel.SCALE,
                null);
            if(gamePanel.keyboardListener.isDebugPressed()) {
                g2d.setColor(Color.RED);
                g2d.drawRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
            }
        }

    }
    public void setImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(image_path)));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Player.class: Failed to load image " + image_path, e);
        }
        collisionBox = new Rectangle(objectX, objectY);
        objectHeight = image.getHeight();
        objectWidth = image.getWidth();
        collisionBox.width = colBoxW * Panel.SCALE;
        collisionBox.height = colBoxH * Panel.SCALE;
    }
}

