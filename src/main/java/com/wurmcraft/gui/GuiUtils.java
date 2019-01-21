package com.wurmcraft.gui;

import static com.wurmcraft.Tread.DEFAULT_MC;

import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.collections.ObservableListWrapper;
import com.wurmcraft.Tread;
import java.util.*;
import java.util.Arrays;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class GuiUtils {

  public static FadeTransition createFade(int duration) {
    FadeTransition ft = new FadeTransition(Duration.millis(1500), MainGui.drawBackground());
    ft.setFromValue(0);
    ft.setToValue(1);
    ft.setCycleCount(1);
    return ft;
  }

  public static JFXComboBox setMinecraftVersions(JFXComboBox box, String version) {
    box.setItems(new ObservableListWrapper(getMinecraftVersions()));
    for (int index = 0; index < getMinecraftVersions().size(); index++) {
      if (version.equalsIgnoreCase(getMinecraftVersions().get(index))) {
        box.getSelectionModel().select(index);
      }
    }
    return box;
  }

  public static JFXComboBox setMinecraftVersions(JFXComboBox box) {
    return setMinecraftVersions(box, DEFAULT_MC);
  }

  public static JFXComboBox setForgeVersions(JFXComboBox box, String mcVersion, int forgeVersion) {
    box.setItems(new ObservableListWrapper(getForgeVersions(mcVersion)));
    if (forgeVersion > 0) {
      for (int index = 0; index < getForgeVersions(mcVersion).size(); index++) {
        if (forgeVersion == getForgeVersions(mcVersion).get(index)) {
          box.getSelectionModel().select(index);
        }
      }
    } else {
      box.getSelectionModel().select(0);
    }
    return box;
  }

  private static List<String> getMinecraftVersions() {
    return Arrays.asList(Tread.manager.forgeData.mcversion.keySet().toArray(new String[0]));
  }

  public static List<Integer> getForgeVersions(String version) {
    List<Integer> data = Tread.manager.forgeData.mcversion.get(version);
    Collections.reverse(data);
    return data;
  }
}
