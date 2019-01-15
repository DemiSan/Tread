package com.wurmcraft.core.controllers;

import static com.wurmcraft.core.ModpackManager.getForgeVersions;
import static com.wurmcraft.core.ModpackManager.getMinecraftVersions;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.sun.javafx.collections.ObservableListWrapper;
import com.wurmcraft.Tread;
import com.wurmcraft.core.ModpackManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;

public class CoreGuiController implements Initializable {

  // Menu
  @FXML public MenuItem create;
  @FXML public MenuItem save;
  @FXML public MenuItem load;
  @FXML public MenuItem settings;
  @FXML public MenuItem close;

  // Settings
  @FXML public JFXTabPane modpackPane;
  @FXML public JFXCheckBox curseEnabled;
  @FXML public JFXCheckBox ftbEnabled;
  @FXML public JFXCheckBox technicEnabled;
  @FXML public JFXCheckBox atEnabled;
  @FXML public JFXCheckBox skcraftEnabled;
  @FXML public JFXCheckBox multicraftEnabled;
  @FXML public JFXComboBox minecraftVersion;
  @FXML public JFXComboBox forgeVersion;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Setup Menu
    create.setOnAction(event -> createNew());
    save.setOnAction(event -> save());
    load.setOnAction(event -> load());
    save.setOnAction(event -> openSettings());
    close.setOnAction(event -> saveAndExit());
    // Display Modpack Creator
    if (Tread.loadedModpack != null) {
      modpackPane.setVisible(true);
    } else {
      modpackPane.setVisible(false);
    }
    // Modpack Export Values
    curseEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.twitchEnabled = curseEnabled.isSelected();
              save();
            });
    ftbEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.ftbEnabled = ftbEnabled.isSelected();
              save();
            });
    technicEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.technicEnabled = technicEnabled.isSelected();
              save();
            });
    atEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.atEnabled = atEnabled.isSelected();
              save();
            });
    skcraftEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.skcraftEnabled = skcraftEnabled.isSelected();
              save();
            });
    multicraftEnabled
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.export.multicraftEnabled = multicraftEnabled.isSelected();
              save();
            });
    // General Settings
    minecraftVersion.setItems(new ObservableListWrapper(getMinecraftVersions()));
    if (Tread.loadedModpack != null) {
      for (int index = 0; index < getMinecraftVersions().size(); index++) {
        if (Tread.loadedModpack.mcVersion.equals(getMinecraftVersions().get(index))) {
          minecraftVersion.getSelectionModel().select(index);
        }
      }
    }
    forgeVersion.setItems(new ObservableListWrapper(getForgeVersions(Tread.DEFAULT_MC)));
    if (Tread.loadedModpack != null) {
      forgeVersion.setItems(
          new ObservableListWrapper(getForgeVersions(Tread.loadedModpack.mcVersion)));
      for (int index = 0; index < getForgeVersions(Tread.loadedModpack.mcVersion).size(); index++) {
        if (Tread.loadedModpack.forgeVersion.equals(
            getForgeVersions(Tread.loadedModpack.mcVersion).get(index).toString())) {
          forgeVersion.getSelectionModel().select(index);
        }
      }
    }
    minecraftVersion
        .selectionModelProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.mcVersion =
                  minecraftVersion.getSelectionModel().selectedItemProperty().getValue().toString();
              save();
            });
    forgeVersion
        .selectionModelProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Tread.loadedModpack.forgeVersion =
                  forgeVersion.getSelectionModel().selectedItemProperty().getValue().toString();
              save();
            });
    if (Tread.loadedModpack != null) {
      curseEnabled.setSelected(Tread.loadedModpack.export.twitchEnabled);
      ftbEnabled.setSelected(Tread.loadedModpack.export.ftbEnabled);
      technicEnabled.setSelected(Tread.loadedModpack.export.technicEnabled);
      atEnabled.setSelected(Tread.loadedModpack.export.atEnabled);
      skcraftEnabled.setSelected(Tread.loadedModpack.export.skcraftEnabled);
      multicraftEnabled.setSelected(Tread.loadedModpack.export.multicraftEnabled);
    }
  }

  public static void createNew() {
    FadeTransition ft = new FadeTransition(Duration.millis(1500), Tread.drawBackground());
    ft.setFromValue(0);
    ft.setToValue(1);
    ft.setCycleCount(1);
    ft.play();
    ft.setOnFinished(
        (e) -> {
          try {
            Tread.root.getChildren().clear();
            Parent creation =
                FXMLLoader.load(CoreGuiController.class.getResource("/creation.fxml"));
            Tread.root.getChildren().add(creation);
          } catch (IOException f) {
            f.printStackTrace();
          }
        });
  }

  public static void save() {
    try {
      ModpackManager.saveModpack(Tread.loadedModpack);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void load() {
    ModpackManager.load();
  }

  // TODO Implement
  public static void openSettings() {}

  public static void saveAndExit() {
    save();
    System.exit(1);
  }

  public static void returnToMainGUI() {
    FadeTransition ft = new FadeTransition(Duration.millis(1500), Tread.drawBackground());
    ft.setFromValue(0);
    ft.setToValue(1);
    ft.setCycleCount(1);
    ft.play();
    ft.setOnFinished(
        (e) -> {
          try {
            Tread.root.getChildren().clear();
            Parent main = FXMLLoader.load(CoreGuiController.class.getResource("/main.fxml"));
            Tread.root.getChildren().add(main);
          } catch (IOException f) {
            f.printStackTrace();
          }
        });
  }
}
