package com.wurmcraft.modpack.builder;

import com.wurmcraft.curse.CurseHelper;
import com.wurmcraft.curse.json.CurseManifest;
import com.wurmcraft.curse.json.CurseManifest.Minecraft.ModLoader;
import com.wurmcraft.curse.json.CurseManifest.PackMod;
import com.wurmcraft.curse.json.ProjectData;
import com.wurmcraft.modpack.ModpackManager;
import com.wurmcraft.modpack.json.Mod;
import com.wurmcraft.utils.FileUtils;
import com.wurmcraft.utils.URLUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CurseBuilder {

  public static void build(File file) {
    FileUtils.saveFile(new File(file + File.separator + "manifest.json"), createManifest());
    createModList(new File(file + File.separator + "modlist.html"));
    handleOverrides(new File(file + File.separator + "overrides"));
    FileUtils.zipDir(
        file,
        new File(file.getParent() + File.separator + "build" + File.separator + "twitch.zip"));
  }

  public static CurseManifest createManifest() {
    List<PackMod> packMods = new ArrayList<>();
    for (Mod mod : ModpackManager.loadedModpack.mods) {
      packMods.add(
          new CurseManifest()
          .new PackMod(
              CurseHelper.getProjectIDFromUserInput(mod.urlData),
              CurseHelper.getModpackModVersion(mod.urlData),
              true));
    }
    List<ModLoader> modloader = new ArrayList<>();
    modloader.add(
        new CurseManifest().new Minecraft()
        .new ModLoader(
            ModpackManager.forgeData.artifact
                + "-"
                + ModpackManager.forgeData.number.get(ModpackManager.loadedModpack.forgeVersion)
                    .version,
            true));
    return new CurseManifest(
        new CurseManifest().new Minecraft(ModpackManager.loadedModpack.mcVersion, modloader),
        "minecraftModpack",
        1,
        ModpackManager.loadedModpack.name,
        ModpackManager.loadedModpack.modpackVersion,
        ModpackManager.loadedModpack.author,
        packMods,
        "overrides");
  }

  public static void createModList(File file) {
    FileUtils.appendFile(file, "<ul>");
    for (Mod mod : ModpackManager.loadedModpack.mods) {
      if (mod.isCurseDownload()) {
        String format = "<li><a href=%URL%>%NAME% (by %AUTHOR%)</a></li>";
        ProjectData data = CurseHelper.loadProjectData(mod);
        FileUtils.appendFile(
            file,
            format
                .replaceAll("%NAME%", data.name)
                .replaceAll("%URL%", data.webSiteURL)
                .replaceAll("%AUTHOR%", data.primaryAuthorName));
      }
    }
    FileUtils.appendFile(file, "</ul>");
  }

  public static void handleOverrides(File file) {
    if (!file.exists()) {
      file.mkdir();
    }
    File modDir = new File(file + File.separator + "mods");
    for (Mod mod : ModpackManager.loadedModpack.mods) {
      if (mod.isDirectDownload()) {
        URLUtils.download(mod.getDownloadLink(), modDir.getPath());
      }
    }
    for (File dir : ModpackManager.modpackFile.getParentFile().listFiles()) {
      if (dir.isDirectory() && dir.getName().equalsIgnoreCase("config")) {
        FileUtils.copyFolder(dir.toPath(), new File(file + File.separator + "config").toPath());
      }
      if (dir.isDirectory() && dir.getName().equalsIgnoreCase("scripts")) {
        FileUtils.copyFolder(dir.toPath(), new File(file + File.separator + "scripts").toPath());
      }
      if (dir.isDirectory() && dir.getName().equalsIgnoreCase("resources")) {
        FileUtils.copyFolder(dir.toPath(), new File(file + File.separator + "resources").toPath());
      }
    }
  }
}
