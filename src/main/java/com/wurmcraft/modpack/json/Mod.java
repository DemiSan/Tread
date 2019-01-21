package com.wurmcraft.modpack.json;

import com.wurmcraft.curse.CurseHelper;
import com.wurmcraft.curse.json.ProjectData;
import java.net.URL;
import java.util.regex.Pattern;

public class Mod {

  public static final Pattern LAST_SEGMENT = Pattern.compile("[^/]+(?=/$|$)");

  public String name;
  public String urlData;

  public Mod(String urlData) {
    this.urlData = urlData;
    this.name = getName();
  }

  public boolean isDirectDownload() {
    try {
      new URL(urlData);
    } catch (Exception e) {
    }
    return false;
  }

  public boolean isCurseDownload() {
    try {
      return CurseHelper.loadProjectData(CurseHelper.getProjectIDFromUserInput(urlData)) != null;
    } catch (NumberFormatException e) {
      // TODO Invalid Modpack Mod entry
    }
    return false;
  }

  public URL getDownloadLink() {
    if (isDirectDownload()) {
      try {
        return new URL(urlData);
      } catch (Exception e) {
      }
    } else if (isCurseDownload()) {
      String[] splitData = urlData.split(":");
      try {
        return CurseHelper.getDownloadLink(
            Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]));
      } catch (NumberFormatException e) {
        // TODO Invalid Modpack Mod entry
      }
    }
    return null;
  }

  private String getName() {
    if (isCurseDownload()) {
      return CurseHelper.loadProjectData(CurseHelper.getProjectIDFromUserInput(urlData)).slug;
    } else if (isDirectDownload()) {
      return LAST_SEGMENT.matcher(urlData).group();
    }
    return "";
  }

  public ProjectData getCurseProjectData() {
    if (isCurseDownload()) {
      return CurseHelper.loadProjectData(CurseHelper.getProjectIDFromUserInput(urlData));
    }
    return null;
  }
}
