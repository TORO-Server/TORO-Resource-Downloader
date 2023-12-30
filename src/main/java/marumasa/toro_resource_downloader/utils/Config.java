package marumasa.toro_resource_downloader.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static marumasa.toro_resource_downloader.TORO_Resource_Downloader.MOD_ID;

public class Config {
    private static final Path path = FabricLoader.getInstance().getConfigDir().normalize().resolve(MOD_ID + ".json");

    private String ResourceURL = "None";

    public void setResourceURL(String url) {
        ResourceURL = url;
        save();
    }

    public String getResourceURL() {
        return ResourceURL;
    }

    public Config() {
        final File configFile = path.toFile();
        if (!configFile.exists()) {
            save();
        } else {
            deserialize();
        }
    }

    private void deserialize() {
        final JsonObject jsonObject = loadJSON();
        ResourceURL = gson.fromJson(jsonObject.get("ResourceURL"), new TypeToken<String>() {
        }.getType());
    }

    private JsonObject serialize() {
        final JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("ResourceURL", ResourceURL);

        return jsonObj;
    }

    private void save() {
        saveJSON(serialize());
    }

    private static final Gson gson = new Gson();

    private static JsonObject loadJSON() {
        try (final BufferedReader reader = Files.newBufferedReader(Config.path)) {
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveJSON(JsonObject jsonObject) {
        try (final BufferedWriter writer = Files.newBufferedWriter(Config.path)) {
            gson.toJson(jsonObject, JsonObject.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}