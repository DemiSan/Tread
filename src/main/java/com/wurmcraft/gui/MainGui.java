package com.wurmcraft.gui;

import static com.wurmcraft.Tread.DEFAULT_MC;
import static com.wurmcraft.Tread.HEIGHT;
import static com.wurmcraft.Tread.WIDTH;
import static com.wurmcraft.Tread.group;
import static com.wurmcraft.Tread.manager;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.wurmcraft.Tread;
import com.wurmcraft.modpack.ModpackManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class MainGui implements Initializable {

  @FXML public MenuItem edit;

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
    drawBackground();
    manager = new ModpackManager();
    setModpackVisability();
    updateSettings();
  }

  public void saveAndQuit() {
    save();
    System.exit(1);
  }

  public void save() {
    manager.saveModpack();
  }

  public void load() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Modpack Json");
    File file = fileChooser.showOpenDialog(Tread.stage);
    try {
      manager.loadModpack(file);
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Error Loading");
      alert.setHeaderText("Failed to load Modpack Json");
      alert.showAndWait();
    }
    returnToMainGUI();
  }

  public void createNewModpack() {
    FadeTransition ft = GuiUtils.createFade(500);
    ft.play();
    ft.setOnFinished(
        (e) -> {
          try {
            group.getChildren().clear();
            Parent creation = FXMLLoader.load(MainGui.class.getResource("/creation.fxml"));
            group.getChildren().add(creation);
          } catch (IOException f) {
            f.printStackTrace();
          }
        });
  }

  public void addMod() {}

  public void openSettings() {}

  public static void returnToMainGUI() {
    FadeTransition ft = GuiUtils.createFade(500);
    ft.play();
    ft.setOnFinished(
        (e) -> {
          try {
            Tread.group.getChildren().clear();
            Parent main = FXMLLoader.load(MainGui.class.getResource("/main.fxml"));
            Tread.group.getChildren().add(main);
          } catch (IOException f) {
            f.printStackTrace();
          }
        });
  }

  public static Rectangle drawBackground() {
    Rectangle rect = new Rectangle(0, 30, WIDTH + 200, HEIGHT + 200);
    rect.setFill(new Color(45 / 255f, 50 / 255f, 60 / 255f, 1));
    group.getChildren().add(rect);
    return rect;
  }

  public void importModpack() {}

  public void exportModpack() {}

  private void setModpackVisability() {
    if (manager.isModpackLoaded()) {
      modpackPane.setVisible(true);
      edit.setVisible(true);
    } else {
      modpackPane.setVisible(false);
      edit.setVisible(false);
    }
  }

  private void updateSettings() {
    if (manager.isModpackLoaded()) {
      // Modpack Export Values
      curseEnabled.setSelected(ModpackManager.loadedModpack.export.twitch);
      curseEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.twitch = curseEnabled.isSelected();
                save();
              });
      ftbEnabled.setSelected(ModpackManager.loadedModpack.export.ftb);
      ftbEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.ftb = ftbEnabled.isSelected();
                save();
              });
      technicEnabled.setSelected(ModpackManager.loadedModpack.export.technic);
      technicEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.technic = technicEnabled.isSelected();
                save();
              });
      atEnabled.setSelected(ModpackManager.loadedModpack.export.at);
      atEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.at = atEnabled.isSelected();
                save();
              });
      skcraftEnabled.setSelected(ModpackManager.loadedModpack.export.skcraft);
      skcraftEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.skcraft = skcraftEnabled.isSelected();
                save();
              });
      multicraftEnabled.setSelected(ModpackManager.loadedModpack.export.multicraft);
      multicraftEnabled
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                ModpackManager.loadedModpack.export.multicraft = multicraftEnabled.isSelected();
                save();
              });
      // Minecraft - Forge Versions
      GuiUtils.setMinecraftVersions(minecraftVersion, ModpackManager.loadedModpack.mcVersion);
      GuiUtils.setForgeVersions(
          forgeVersion,
          ModpackManager.loadedModpack.mcVersion,
          Integer.parseInt(ModpackManager.loadedModpack.forgeVersion));
      // Update Forge Versions based on MC Version
      minecraftVersion
          .valueProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                GuiUtils.setForgeVersions(
                    forgeVersion,
                    minecraftVersion
                        .getSelectionModel()
                        .selectedItemProperty()
                        .getValue()
                        .toString(),
                    -1);
              });
      updateModpackForSettings(forgeVersion);
      updateModpackForSettings(minecraftVersion);
    } else {
      GuiUtils.setMinecraftVersions(minecraftVersion, DEFAULT_MC);
      GuiUtils.setForgeVersions(forgeVersion, DEFAULT_MC, -1);
    }
  }

  private void updateModpackForSettings(JFXComboBox box) {
    box.valueProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              try {
                ModpackManager.loadedModpack.mcVersion =
                    minecraftVersion
                        .getSelectionModel()
                        .selectedItemProperty()
                        .getValue()
                        .toString();
                ModpackManager.loadedModpack.forgeVersion =
                    forgeVersion.getSelectionModel().selectedItemProperty().getValue().toString();
                if (oldValue != newValue) {
                  save();
                }
              } catch (Exception e) {
              }
            });
  }
}