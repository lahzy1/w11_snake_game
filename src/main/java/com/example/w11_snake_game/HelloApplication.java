package com.example.w11_snake_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Game Screen
        Pane gamePane = new Pane();

        // Main Menu
        Label titleLabel = new Label("SNAKE");
        titleLabel.setPadding(new Insets(80));

        Button resumeButton = new Button("Resume Game");
        resumeButton.setDisable(true);

        Button newGameButton = new Button("New Game");

        Button insaneModeButton = new Button("New Game (Insane)");

        Button exitButton = new Button("Exit");

        VBox menuVBox = new VBox(titleLabel, resumeButton, newGameButton, insaneModeButton, exitButton);
        menuVBox.setAlignment(Pos.CENTER);
        menuVBox.setSpacing(20);

        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setCenter(menuVBox);



        //
        Scene scene = new Scene(menuBorderPane, 800, 800);

        stage.setTitle("Sssssnake");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}