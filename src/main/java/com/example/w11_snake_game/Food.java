package com.example.w11_snake_game;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Food {
    public enum FoodType {
        NORMAL, SPEED, BIGHEAD
    }

    private FoodType foodType;

    private Position position;
    private Rectangle rectangle;
    private Color normalColor = Color.rgb(133, 153, 0);
    private Color speedColor = Color.rgb(57,18,215);
    private AnchorPane anchorPane;
    private Random random = new Random();
    private int size;

    public Food(double xPos, double yPos, AnchorPane anchorPane, double size) {
        this.anchorPane = anchorPane;
        this.size = (int) size;
        position = new Position(xPos, yPos);
        rectangle = new Rectangle(position.getXPos(), position.getYPos(), size, size);
        rectangle.setFill(normalColor);
        anchorPane.getChildren().add(rectangle);
        foodType = FoodType.NORMAL;
    }

    public FoodType getFoodType() {
            return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public Position getPosition() {
        return position;
    }

    public void moveFood() {
        if (random.nextInt(10) == 0) {
            spawnSpeedFood();
        } else spawnFood();
    }

    private void spawnFood() {
        int positionX = random.nextInt(12);
        int positionY = random.nextInt(12);
        rectangle.setX(positionX * size);
        rectangle.setY(positionY * size);

        position.setXPos(positionX * size);
        position.setYPos(positionY * size);
        System.out.println("Food Position: " + (positionX * size) + ", " + (positionY * size));

        rectangle.setFill(normalColor);
    }

    private void spawnSpeedFood() {
        int positionX = random.nextInt(12);
        int positionY = random.nextInt(12);
        rectangle.setX(positionX * size);
        rectangle.setY(positionY * size);
        rectangle.setFill(speedColor);

        position.setXPos(positionX * size);
        position.setYPos(positionY * size);
        System.out.println("Food Position: " + (positionX * size) + ", " + (positionY * size));

        // få fat i "food" fra Controller klassen.
    }
}
