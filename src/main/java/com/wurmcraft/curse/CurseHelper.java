package com.wurmcraft.curse;

import com.wurmcraft.curse.json.ProjectData;
import com.wurmcraft.curse.json.ProjectData.ModFile;
import com.wurmcraft.modpack.json.Mod;
import com.wurmcraft.utils.URLUtils;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class CurseHelper {

  public static final String CURSE_API_LINK = "https://curse.nikky.moe/api/";

  public static NonBlockingHashMap<Long, ProjectData> projectCache = new NonBlockingHashMap<>();
  public static NonBlockingHashMap<Long, ModFile> projectFileCache = new NonBlockingHashMap<>();

  public static String getDownloadLink(long projectID, long fileID) {
    return loadModFile(projectID, fileID).downloadURL;
  }

  public static ProjectData loadProjectData(long projectID) {
    if (projectCache.contains(projectID)) {
      return projectCache.get(projectID);
    } else {
      ProjectData projectData =
          URLUtils.get(CURSE_API_LINK + "addon/" + projectID, ProjectData.class);
      projectCache.put(projectID, projectData);
      return projectData;
    }
  }

  public static ProjectData loadProjectData(Mod mod) {
    return loadProjectData(getProjectIDFromUserInput(mod.urlData));
  }

  public static long getProjectIDFromUserInput(String input) {
    try {
      if (input.contains(":")) {
        return Long.parseLong(input.split(":")[0]);
      } else {
        return Long.parseLong(input);
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return -1L;
    }
  }

  public static ModFile loadModFile(long projectID, long fileID) {
    if (projectFileCache.contains(fileID)) {
      return projectFileCache.get(fileID);
    } else {
      ModFile fileData =
          URLUtils.get(CURSE_API_LINK + "addon/" + projectID + "/file/" + fileID, ModFile.class);
      projectFileCache.put(fileID, fileData);
      return fileData;
    }
  }

  public static long getModpackModVersion(String data) {
    long projectID = getProjectIDFromUserInput(data);
    ProjectData projectData = loadProjectData(projectID);
    ModFile newestFile = projectData.latestFiles.get(0);
    for (ModFile file : projectData.latestFiles) {
      if (newestFile.fileDate < file.fileDate) {
        newestFile = file;
      }
    }
    projectFileCache.put(newestFile.id, newestFile);
    return newestFile.id;
  }
}
