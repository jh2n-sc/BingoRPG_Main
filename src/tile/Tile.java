package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import mains.Panel;
import mains.UtilityTool;

import com.google.gson.annotations.Expose;

public class Tile {
    @Expose
    public int tile_id;
    @Expose
    public String image_path;
    @Expose
    public boolean collisionSolid;

    public BufferedImage image;

    public Tile(BufferedImage image) {
        this.image = image;
    }
    public void setImage() {
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(image_path)));
            this.image = UtilityTool.scaleImage(image, Panel.TILE_SIZE, Panel.TILE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
