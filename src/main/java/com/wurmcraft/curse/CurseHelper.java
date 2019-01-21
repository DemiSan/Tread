package com.wurmcraft.curse;

import com.wurmcraft.curse.json.ProjectData;
import com.wurmcraft.utils.URLUtils;
import java.net.URL;
import org.cliffc.high_scale_lib.NonBlockingHashMap;

public class CurseHelper {

  public static final String CURSE_API_LINK = "https://curse.nikky.moe/api/";

  public static NonBlockingHashMap<Integer, ProjectData> projectCache = new NonBlockingHashMap<>();

  // TODO Implement
  public static URL getDownloadLink(int projectID, int fileID) {
    return null;
  }

  public static ProjectData loadProjectData(int projectID) {
    if (projectCache.contains(projectID)) {
      return projectCache.get(projectID);
    } else {
      ProjectData projectData =
          URLUtils.get(CURSE_API_LINK + "addon/" + projectID, ProjectData.class);
      projectCache.put(projectID, projectData);
      return projectData;
    }
  }
}
