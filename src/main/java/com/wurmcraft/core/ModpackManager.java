package com.wurmcraft.core;

import static com.wurmcraft.core.controllers.CoreGuiController.returnToMainGUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wurmcraft.Tread;
import com.wurmcraft.core.json.Modpack;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class ModpackManager {

  public static final File SAVE_DIR = new File("Modpacks");
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void saveModpack(Modpack modpack) throws IOException {
    if (!SAVE_DIR.exists()) {
      SAVE_DIR.mkdirs();
    }
    File modpackJson = new File(SAVE_DIR + File.separator + modpack.name + ".json");
    modpackJson.createNewFile();
    Files.write(modpackJson.toPath(), GSON.toJson(modpack).getBytes());
  }

  public static void loadModpack(File file) throws Exception {
    if (file.exists()) {
      Tread.loadedModpack = GSON.fromJson(
          String.join("", Files.readAllLines(file.toPath()).toArray(new String[0])),
          Modpack.class);
    }
  }

  public static void load() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Modpack Json");
    File file = fileChooser.showOpenDialog(Tread.stage);
    try {
      ModpackManager.loadModpack(file);
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Error Loading");
      alert.setHeaderText("Failed to load Modpack Json");
      alert.showAndWait();
    }
    returnToMainGUI();
  }

  public static List<String> getMinecraftVersions() {
    // TODO Get from Forge Version List
    List<String> minecraftVersions = new ArrayList<>();
    minecraftVersions.add("1.12.2");
    minecraftVersions.add("1.7.10");
    return minecraftVersions;
  }

  public static List<String> getForgeVersions() {
    // TODO Get from Forge API "http://files.minecraftforge.net/maven/net/minecraftforge/forge/json"
    List<String> forgeVersions = new ArrayList<>();
    forgeVersions.add("Latest");
    forgeVersions.add("Recommended");
    return forgeVersions;
  }


}
