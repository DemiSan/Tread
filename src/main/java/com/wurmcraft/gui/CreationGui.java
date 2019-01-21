package com.wurmcraft.gui;

import static com.wurmcraft.Tread.DEFAULT_MC;
import static com.wurmcraft.Tread.manager;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.wurmcraft.modpack.json.Modpack;
import com.wurmcraft.utils.Validator;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

public class CreationGui implements Initializable {

  // Creation Gui
  public JFXTextField name;
  public JFXComboBox minecraftVersion;
  public JFXComboBox forgeVersion;
  public JFXTextField modpackVersion;
  public JFXTextField authors;
  public JFXButton createButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    MainGui.drawBackground();
    GuiUtils.setMinecraftVersions(minecraftVersion);
    GuiUtils.setForgeVersions(forgeVersion, DEFAULT_MC, -1);
    createButton.setDisable(true);
    // Update Forge Versions based on MC Version
    minecraftVersion
        .valueProperty()
        .addListener(
            (observable, oldValue, newValue) ->
                GuiUtils.setForgeVersions(
                    forgeVersion,
                    minecraftVersion
                        .getSelectionModel()
                        .selectedItemProperty()
                        .getValue()
                        .toString(),
                    -1));
    // Add Validators
    new Validator();
    name.setValidators(Validator.NAME);
    applyChangeListener(name);
    authors.setValidators(Validator.NAME);
    applyChangeListener(authors);
    applyChangeListener(modpackVersion);
  }

  private boolean validate() {
    return name.validate()
        && modpackVersion.getText() != null
        && modpackVersion.getText().length() > 0
        && authors.validate();
  }

  public void onCreate() {
    if (validate()) {
      Modpack modpack =
          new Modpack(
              name.getText(),
              authors.getText(),
              minecraftVersion.getSelectionModel().selectedItemProperty().getValue().toString(),
              forgeVersion.getSelectionModel().selectedItemProperty().getValue().toString(),
              modpackVersion.getText(),
              new ArrayList<>());
      manager.initModpack(modpack);
      manager.saveModpack();
      MainGui.returnToMainGUI();
    }
  }

  public void applyChangeListener(JFXTextField text) {
    text.focusedProperty()
        .addListener(
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
              if (!newValue) {
                if (text.validate()) {
                  if (validate()) {
                    createButton.setDisable(false);
                  }
                } else {
                  createButton.setDisable(true);
                }
              }
            });
    text.setOnKeyTyped(
        event -> {
          if (text.validate() && validate()) {
            createButton.setDisable(false);
          }
        });
  }
}
