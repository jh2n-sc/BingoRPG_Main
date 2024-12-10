package json;

import com.google.gson.*;
import objects.*;

import java.lang.reflect.Type;

public class ObjectDeserializer implements JsonDeserializer<ParentObject> {

    @Override
    public ParentObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        switch(type) {
            case "OBJ_Bottle":
                return context.deserialize(jsonObject, OBJ_Bottle.class);
            case "OBJ_Coins":
                return context.deserialize(jsonObject, OBJ_Coins.class);
            default:
                throw new JsonParseException("Unknown object type: " + type);
        }
    }


}
