package com.wurmcraft.core.controllers;

import static com.wurmcraft.core.ModpackManager.getForgeVersions;
import static com.wurmcraft.core.ModpackManager.getMinecraftVersions;
import static com.wurmcraft.core.ModpackManager.load;
import static com.wurmcraft.core.controllers.CoreGuiController.createNew;
import static com.wurmcraft.core.controllers.CoreGuiController.openSettings;
import static com.wurmcraft.core.controllers.CoreGuiController.save;
import static com.wurmcraft.core.controllers.CoreGuiController.saveAndExit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.sun.javafx.collections.ObservableListWrapper;
import com.wurmcraft.Tread;
import com.wurmcraft.core.json.Modpack;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class CreationGuiController implements Initializable {

  // Menu
  @FXML public MenuItem create;

  @FXML public MenuItem save;

  @FXML public MenuItem load;

  @FXML public MenuItem settings;

  @FXML public MenuItem close;

  // Creation Gui
  @FXML public JFXTextField name;

  @FXML public JFXComboBox minecraftVersion;

  @FXML public JFXComboBox forgeVersion;

  @FXML public JFXTextField modpackVersion;

  @FXML public JFXTextField authors;

  @FXML public JFXButton createButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Setup Menu
    create.setOnAction(event -> createNew());
    save.setOnAction(event -> save());
    load.setOnAction(event -> load());
    settings.setOnAction(event -> openSettings());
    close.setOnAction(event -> saveAndExit());
    // load Creation Settings
    minecraftVersion.setItems(new ObservableListWrapper(getMinecraftVersions()));
    // TODO Setup Default MC Version
    minecraftVersion.getSelectionModel().select(0);
    forgeVersion.setItems(
        new ObservableListWrapper(
            getForgeVersions(
                minecraftVersion
                    .getSelectionModel()
                    .selectedItemProperty()
                    .getValue()
                    .toString())));
    createButton.setDisable(true);
    forgeVersion.getSelectionModel().select(0);
    // Update Forge Versions on Minecraft Version Change
    minecraftVersion
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) ->
                forgeVersion.setItems(
                    new ObservableListWrapper(
                        getForgeVersions(
                            minecraftVersion
                                .getSelectionModel()
                                .selectedItemProperty()
                                .getValue()
                                .toString()))));
    // Setup Validators
    RegexValidator nameValidator = new RegexValidator("Must be at least 3 letters long");
    nameValidator.setRegexPattern("^[\\w\\d_. -]{3,}$");
    name.setValidators(nameValidator);
    name.focusedProperty()
        .addListener(
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
              if (!newValue) {
                if (name.validate()) {
                  if (authors.getText().length() > 0 && modpackVersion.getText().length() > 0) {
                    if (validate()) {
                      createButton.setDisable(false);
                    }
                  }
                } else {
                  createButton.setDisable(true);
                }
              }
            });
    name.setOnKeyTyped(
        event -> {
          if (name.validate() && validate()) {
            createButton.setDisable(false);
          }
        });
    RegexValidator versionValidator = new RegexValidator("Must be in the format X.X.X");
    versionValidator.setRegexPattern("([0-9a-zA-Z-]+\\.)([0-9a-zA-Z-]+\\.)([0-9a-zA-Z-]+)");
    modpackVersion.setValidators(versionValidator);
    modpackVersion
        .focusedProperty()
        .addListener(
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
              if (!newValue) {
                if (modpackVersion.validate()) {
                  if (name.getText().length() > 0 && authors.getText().length() > 0) {
                    if (validate()) {
                      createButton.setDisable(false);
                    }
                  }
                } else {
                  createButton.setDisable(true);
                }
              }
            });
    modpackVersion.setOnKeyTyped(
        event -> {
          if (modpackVersion.validate() && validate()) {
            createButton.setDisable(false);
          }
        });
    authors.setValidators(nameValidator);
    authors
        .focusedProperty()
        .addListener(
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
              if (!newValue) {
                if (authors.validate()) {
                  if (name.getText().length() > 0 && modpackVersion.getText().length() > 0) {
                    if (validate()) {
                      createButton.setDisable(false);
                    }
                  }
                } else {
                  createButton.setDisable(true);
                }
              }
            });
    authors.setOnKeyTyped(
        event -> {
          if (authors.validate() && validate()) {
            createButton.setDisable(false);
          }
        });
  }

  public void onCreate() {
    if (validate()) {
      Modpack modpack =
          new Modpack(
              name.getText(),
              authors.getText(),
              minecraftVersion.getSelectionModel().selectedItemProperty().getValue().toString(),
              forgeVersion.getSelectionModel().selectedItemProperty().getValue().toString(),
              modpackVersion.getText());
      Tread.loadedModpack = modpack;
      save();
      CoreGuiController.returnToMainGUI();
    }
  }

  private boolean validate() {
    return name.validate() && modpackVersion.validate() && authors.validate();
  }
}
