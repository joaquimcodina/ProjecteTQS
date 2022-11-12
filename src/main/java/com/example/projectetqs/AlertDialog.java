package com.example.projectetqs;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertDialog {
  public static void display(String title, String message){
    Stage window = new Stage();
    window.setTitle(title);
    window.setMinWidth(400);
    window.setMaxHeight(500);

    Label label = new Label();
    label.setText(message);
    Button ok = new Button("OK");
    ok.setOnAction(e -> window.close());

    VBox layout = new VBox(5);
    layout.getChildren().addAll(label, ok);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }
}
