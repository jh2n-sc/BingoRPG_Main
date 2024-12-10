package json;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import tile.*;

import java.io.FileReader;

public class TilePopulator {
    Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create();

    public TilePopulator(TileHandler tileHandler) {
        try (InputStream is = getClass().getResourceAsStream("/config/tiles_config/BingTileset.json"))
        {
            assert is != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type mapType = new TypeToken<Map<Integer, Tile>>() {}.getType();

        Map<Integer, Tile> map = gson.fromJson(reader, mapType);
        tileHandler.tileList = map;


        for (int i = 0; i < map.size(); i++) {
            Tile tile = map.get(i);
            tile.setImage();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
