package com.wurmcraft.core;

import static com.wurmcraft.core.controllers.CoreGuiController.returnToMainGUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wurmcraft.Tread;
import com.wurmcraft.core.json.ForgeData;
import com.wurmcraft.core.json.Modpack;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class ModpackManager {

  public static final File SAVE_DIR = new File("Modpacks");
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static ForgeData forgeData;

  public static void init() {
    forgeData =
        GSON.fromJson(
            getData("http://files.minecraftforge.net/maven/net/minecraftforge/forge/json"),
            ForgeData.class);
  }

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
      Tread.loadedModpack =
          GSON.fromJson(
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
    return Arrays.asList(forgeData.mcversion.keySet().toArray(new String[0]));
  }

  public static List<Integer> getForgeVersions(String version) {
    List<Integer> data = forgeData.mcversion.get(version);
    Collections.reverse(data);
    return data;
  }

  private static String getData(String url) {
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
      return rd.lines().collect(Collectors.joining());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
