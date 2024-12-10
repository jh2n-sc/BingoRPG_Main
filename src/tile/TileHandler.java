package tile;

import mains.Panel;
import java.awt.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import json.TilePopulator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TileHandler {
    private static final Logger logger = Logger.getLogger(TileHandler.class.getName());
    Panel panel;
    public Map<Integer, Tile> tileList;
    public int[][] worldMap;


    public TileHandler(Panel panel) {
        this.panel = panel;
        tileList = new HashMap<>();
        this.worldMap = new int[panel.MAX_WORLD_COL_SIZE][panel.MAX_WORLD_ROW_SIZE];
        loadImage();
        loadMap("/maps/final.csv");
    }

    /* to do: fix the loading Image. make a class that will handle all the properties of the tiles
     * instead of hardcoding every thing
     **/
    public void loadImage() {
        new TilePopulator(this);
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            for (int row = 0; row < panel.MAX_WORLD_ROW_SIZE; row++) {
                String line = br.readLine();
                String[] inLine = new String[panel.MAX_WORLD_COL_SIZE];
                if (line != null) inLine = line.split(",");
                for (int col = 0; col < panel.MAX_WORLD_COL_SIZE; col++) {
                    worldMap[col][row] = Integer.parseInt(inLine[col]);
                   // System.out.println(col + " " + row);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load map", e);
        }
    }

    public void drawTile(Graphics2D g2d) {
        for (int row = 0; row < panel.MAX_WORLD_ROW_SIZE; row++) {
            for (int col = 0; col < panel.MAX_WORLD_COL_SIZE; col++) {
                int worldX = col * Panel.TILE_SIZE;
                int worldY = row * Panel.TILE_SIZE;
                int screenX = worldX - panel.player.entityPosX + panel.player.screenPosX;
                int screenY = worldY - panel.player.entityPosY + panel.player.screenPosY;

                if (worldX + Panel.TILE_SIZE * 4 > panel.player.entityPosX - panel.player.screenPosX &&
                    worldX - Panel.TILE_SIZE * 4 < panel.player.entityPosX + panel.player.screenPosX &&
                    worldY + Panel.TILE_SIZE * 4 > panel.player.entityPosY - panel.player.screenPosY &&
                    worldY - Panel.TILE_SIZE * 4 < panel.player.entityPosY + panel.player.screenPosY) {
                    int key = worldMap[col][row];
                    Tile tile = tileList.get(key);
                        g2d.drawImage(tile.image,
                            screenX,
                            screenY,
                            null);
                }
            }
        }
    }
}



