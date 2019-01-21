package com.wurmcraft.curse;

import com.wurmcraft.curse.json.ProjectData;
import com.wurmcraft.modpack.json.Mod;
import com.wurmcraft.utils.URLUtils;
import java.net.URL;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class CurseHelper {

  public static final String CURSE_API_LINK = "https://curse.nikky.moe/api/";

  public static NonBlockingHashMap<Long, ProjectData> projectCache = new NonBlockingHashMap<>();

  // TODO Implement
  public static URL getDownloadLink(int projectID, int fileID) {
    return null;
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
}
