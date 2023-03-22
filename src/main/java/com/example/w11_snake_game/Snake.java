package com.example.w11_snake_game;

import javafx.scene.paint.Color;

public class Snake {
    private enum Direction {UP, DOWN, LEFT, RIGHT}

    private int xSnakePosition = 300;
    private int ySnakePosition = 300;
    private int snakeSize = 3;
    private int snakeSpeed = 4; // 4 squares per second?
    private Color snakeColor = Color.WHITESMOKE;

    public Snake() {

    }


}
