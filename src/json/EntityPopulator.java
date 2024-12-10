package json;
import java.io.*;

import com.google.gson.*;
import mains.Panel;
import entity.*;
import mains.Panel;

public class EntityPopulator {
    public Panel gamePanel;
    public String jsonFilePath;

    public EntityPopulator(Panel gamePanel, String jsonFilePath) {
        this.gamePanel = gamePanel;
        this.jsonFilePath = jsonFilePath;
    }
    public void repopulate() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Entity.class, new ObjectDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

        try (InputStream is = getClass().getResourceAsStream(jsonFilePath)) {
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //Type listType = new TypeToken<List<ParentObject>>() {}.getType();
            Entity[] entities = gson.fromJson(br, Entity[].class);


            for (Entity entity : entities) {
                entity.setPanel(gamePanel);
                entity.entityPosX *= Panel.TILE_SIZE;
                entity.entityPosY*= Panel.TILE_SIZE;
            }
            gamePanel.npc = entities;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
