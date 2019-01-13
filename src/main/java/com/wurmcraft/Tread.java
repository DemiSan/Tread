package com.wurmcraft;

import com.wurmcraft.core.json.Modpack;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Tread extends Application {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;

  public static Stage stage;
  public static Group root;

  public static Modpack loadedModpack;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    root = new Group();
    Parent mainGui = FXMLLoader.load(getClass().getResource("/main.fxml"));
    root.getChildren().add(mainGui);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    stage.setTitle("Tread");
    stage.getIcons().add(new Image("logo.png"));
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    stage.getScene().setFill(new Color(45 / 255f, 50 / 255f, 60 / 255f, 1));
  }

  public static Rectangle drawBackground() {
    Rectangle rect = new Rectangle(0, 30, WIDTH + 200, HEIGHT + 200);
    rect.setFill(new Color(45 / 255f, 50 / 255f, 60 / 255f, 1));
    root.getChildren().add(rect);
    return rect;
  }
}
