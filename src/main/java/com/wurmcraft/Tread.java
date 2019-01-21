package com.wurmcraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wurmcraft.modpack.ModpackManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Tread extends Application {

  // Default Values
  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;
  public static final String DEFAULT_MC = "1.12.2";

  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final ScheduledExecutorService EXECUTORS =
      Executors.newScheduledThreadPool(
          (Runtime.getRuntime().availableProcessors() - 2) > 0
              ? (Runtime.getRuntime().availableProcessors() - 2)
              : 1);

  public static Stage stage;
  public static Group group;
  public static ModpackManager manager;

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;
    group = new Group();
    // Load Default Gui
    Parent mainGui = FXMLLoader.load(getClass().getResource("/main.fxml"));
    group.getChildren().add(mainGui);
    // Set default values
    Scene scene = new Scene(group, WIDTH, HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Tread");
    stage.setResizable(false);
    stage.getIcons().add(new Image("logo.png"));
    stage.show();
  }
}
