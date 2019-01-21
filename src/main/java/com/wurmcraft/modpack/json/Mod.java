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

  public String getDownloadLink() {
    if (isDirectDownload()) {
      try {
        return urlData;
      } catch (Exception e) {
      }
    } else if (isCurseDownload()) {
      try {
        return CurseHelper.getDownloadLink(
            CurseHelper.getProjectIDFromUserInput(urlData),
            CurseHelper.getModpackModVersion(urlData));
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
