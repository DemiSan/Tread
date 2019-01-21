package com.wurmcraft.modpack;

import static com.wurmcraft.Tread.GSON;

import com.wurmcraft.Tread;
import com.wurmcraft.modpack.builder.CurseBuilder;
import com.wurmcraft.modpack.json.ForgeData;
import com.wurmcraft.modpack.json.Mod;
import com.wurmcraft.modpack.json.Modpack;
import com.wurmcraft.utils.FileUtils;
import com.wurmcraft.utils.URLUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ModpackManager {

  private static final File SAVE_DIR = new File("pack");
  public static File modpackFile;

  public static Modpack loadedModpack;
  public static ForgeData forgeData;

  public ModpackManager() {
    forgeData =
        URLUtils.get(
            "http://files.minecraftforge.net/maven/net/minecraftforge/forge/json", ForgeData.class);
  }

  public void initModpack(Modpack modpack) {
    if (modpack != null && modpack.mods != null) {
      for (Mod mod : modpack.mods) {
        Tread.EXECUTORS.schedule((Runnable) mod::isCurseDownload, 0L, TimeUnit.SECONDS);
      }
    }
    this.loadedModpack = modpack;
  }

  public boolean loadModpack(File file) {
    if (file.exists()) {
      try {
        modpackFile = file;
        Modpack modpack = GSON.fromJson(FileUtils.getFileLines(file), Modpack.class);
        if (modpack != null) {
          loadedModpack = modpack;
          return true;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public void saveModpack() {
    if (loadedModpack != null) {
      File file = new File(SAVE_DIR + File.separator + loadedModpack.name + ".json");
      try {
        SAVE_DIR.mkdir();
        file.createNewFile();
        Files.write(file.toPath(), GSON.toJson(loadedModpack).getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isModpackLoaded() {
    return loadedModpack != null;
  }

  public static Mod getModFromString(String input) {
    return new Mod(input);
  }

  public static boolean isModWithinModpack(String data) {
    for (Mod mod : loadedModpack.mods) {
      if (mod.urlData.equalsIgnoreCase(data)) {
        return true;
      }
    }
    return false;
  }

  public static void initBuild() {
    List<Thread> downloadThreads;
    File modsFolder = new File(modpackFile.getParent() + File.separator + "mods");
    if (!modsFolder.exists()) {
      modsFolder.mkdir();
    }
    downloadThreads =
        loadedModpack.mods.stream().map(ModpackManager::downloadMod).collect(Collectors.toList());
    for (Thread thread : downloadThreads) {
      synchronized (thread) {
        if (thread.isAlive()) {
          try {
            Thread.currentThread().wait(100);
          } catch (Exception e) {
          }
        }
      }
    }
    if (loadedModpack.export.twitch) {
      File twtich = new File(modpackFile.getParent() + File.separator + "twitch");
      if (!twtich.exists()) {
        twtich.mkdir();
      }
      CurseBuilder.build(twtich);
    }
  }

  public static Thread downloadMod(Mod mod) {
    Thread thread =
        new Thread(
            () -> {
              URLUtils.download(
                  mod.getDownloadLink(), modpackFile.getParent() + File.separator + "mods");
            });
    thread.setName("Download_" + mod.name);
    thread.start();
    return thread;
  }
}
