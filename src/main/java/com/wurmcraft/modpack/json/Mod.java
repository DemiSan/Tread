package com.wurmcraft.modpack.json;

import com.wurmcraft.curse.CurseHelper;
import java.net.URL;

public class Mod {

  public String name;
  public String urlData;

  public Mod(String name, String urlData) {
    this.name = name;
    this.urlData = urlData;
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
      return CurseHelper.loadProjectData(Integer.parseInt(urlData.split(":")[0])) != null;
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
}
