package com.wurmcraft.modpack.builder;

import com.wurmcraft.modpack.ModpackManager;
import com.wurmcraft.modpack.json.Mod;
import com.wurmcraft.utils.FileUtils;
import com.wurmcraft.utils.URLUtils;
import java.io.File;

public class TechnicBuilder {

  public static void build(File file) {
    if (file.exists()) {
      file.delete();
    }
    downloadForge(file);
    handleOverrides(file);
    FileUtils.zipDir(
        file,
        new File(file.getParent() + File.separator + "build" + File.separator + "technic.zip"));
  }

  public static void downloadForge(File file) {
    String forgeFullVersion =
        ModpackManager.forgeData.number.get(ModpackManager.loadedModpack.forgeVersion).version;
    URLUtils.download(
        "http://files.minecraftforge.net/maven/net/minecraftforge/forge/%MCVERSION%-%VERSION%/forge-%MCVERSION%-%VERSION%-universal.jar"
            .replaceAll("%VERSION%", forgeFullVersion)
            .replaceAll("%MCVERSION%", ModpackManager.loadedModpack.mcVersion),
        file.getPath() + File.separator + "bin");
    File forgeJar =
        new File(
            file
                + File.separator
                + "bin"
                + File.separator
                + "forge-"
                + ModpackManager.loadedModpack.mcVersion
                + "-"
                + forgeFullVersion
                + "-universal.jar");
    if (!forgeJar.renameTo(
        new File((file + File.separator + "bin" + File.separator + "modpack.jar")))) {
      forgeJar.delete();
    }
  }

  public static void handleOverrides(File file) {
    CurseBuilder.handleOverrides(file);
    File modDir = new File(file + File.separator + "mods");
    for (Mod mod : ModpackManager.loadedModpack.mods) {
      if (mod.isCurseDownload()) {
        URLUtils.download(mod.getDownloadLink(), modDir.getPath());
      }
    }
  }
}
