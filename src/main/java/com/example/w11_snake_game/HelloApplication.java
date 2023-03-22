package com.example.w11_snake_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;

public class HelloApplication extends Application {
    private void drawFruit(GraphicsContext gc,double x, double y, double radius, Color color)
    {
        gc.setFill(color);

        gc.fillOval(x - radius, y - radius , radius*2,radius*2);

    }

    boolean Gameover = false;

    @Override
    public void start(Stage stage) throws IOException {

        Group root = new  Group();
        Canvas canvas = new Canvas(600,600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Random random = new Random();
        int minX = 0;
        int maxX = 600;
        int minY = 0;
        int maxY = 600;

        int randomX = random.nextInt(maxX - minX + 1) + minX % 10;
        int randomY = random.nextInt(maxY - minY + 1) + minX % 10;

        //drawFruit(gc,randomX,randomY,20,Color.DARKORANGE);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds((2)), event -> {
                    drawFruit(gc, randomX,randomY,20,Color.DARKORANGE);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();



        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();



      /*
        while (Gameover = false)
        {


        }
        */
    }



    public static void main(String[] args) {
        launch();
    }
}