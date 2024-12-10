package json;
import java.io.*;

import com.google.gson.*;
import mains.Panel;
import objects.*;

public class ObjectPopulator {
    public Panel gamePanel;
    public String jsonFilePath;
    public ObjectPopulator(Panel gamePanel, String jsonFilePath) {
        this.gamePanel = gamePanel;
        this.jsonFilePath = jsonFilePath;
    }
    public void repopulate() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(ParentObject.class, new ObjectDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

        try (InputStream is = getClass().getResourceAsStream(jsonFilePath)) {
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //Type listType = new TypeToken<List<ParentObject>>() {}.getType();
            ParentObject[] parentObjects = gson.fromJson(br, ParentObject[].class);


            for (ParentObject parentObject : parentObjects) {
                parentObject.setImage();
                parentObject.objectX *= gamePanel.TILE_SIZE;
                parentObject.objectY *= gamePanel.TILE_SIZE;
            }
            gamePanel.object = parentObjects;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
