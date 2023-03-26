package com.example.w11_snake_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Double snakeSize = 50.0;
    private Double foodSize = 50.0;
    private Rectangle snakeHead;
    private Rectangle snakeTail1;
    double xPos;
    double yPos;

    Food food;

    int score = 0;
    int snakeLength = 2;

    // The direction the snake is moving at start
    private Direction direction;

    // List of all position of thew snake head
    private final List<Position> positions = new ArrayList<>();

    // List of all snake body parts
    private final ArrayList<Rectangle> snakeBody = new ArrayList<>();

    // How many times the snake has moved
    private int gameTicks;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;
    @FXML
    private Label scoreLabel, snakeLengthLabel;
    @FXML
    private CheckBox insaneModeCheckBox = new CheckBox();

    Timeline timeline;
    private double tickLength = 0.3;

    private boolean canChangeDirection;

    @FXML
    void start(MouseEvent event) {
        for (Rectangle snake : snakeBody) {
            anchorPane.getChildren().remove(snake);
        }

        gameTicks = 0;
        score = 0;
        scoreLabel.setText("Score: 0");
        snakeLength = 2;
        snakeLengthLabel.setText("Snake Length: 2");

        positions.clear();
        snakeBody.clear();
        snakeHead = new Rectangle(250, 250, snakeSize, snakeSize);
        snakeTail1 = new Rectangle(snakeHead.getX() - snakeSize, snakeHead.getY(), snakeSize, snakeSize);
        snakeTail1.setFill(Color.rgb(0, 43, 54));
        xPos = snakeHead.getLayoutX();
        yPos = snakeHead.getLayoutY();
        direction = Direction.RIGHT;
        canChangeDirection = true;
        food.moveFood();

        snakeBody.add(snakeHead);
        snakeHead.setFill(Color.rgb(220, 50, 47));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        snakeBody.add(snakeTail1);

        anchorPane.getChildren().addAll(snakeHead, snakeTail1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(tickLength), e -> {
            positions.add(new Position(snakeHead.getX() + xPos, snakeHead.getY() + yPos));
            moveSnakeHead(snakeHead);
            for (int i = 1; i < snakeBody.size(); i++) {
                moveSnakeTail(snakeBody.get(i), i);
            }
            canChangeDirection = true;
            //System.out.println("Snake Head Position: " + (xPos + snakeHead.getX()) + ", " + (yPos + snakeHead.getY()));
            eatFood();
            gameTicks++;
            // Respawn food at random
            while (gameTicks > 0) {
                int randomSpawn = (int) (Math.random() * 100) + 1;
                int randomSpawnUpperBorder = 10;
                if (tickLength <= tickLength / 2) {
                    randomSpawnUpperBorder = randomSpawnUpperBorder / 2;
                }
                if (randomSpawn >= 1 && randomSpawn <= randomSpawnUpperBorder) {
                    food.moveFood();
                }
                break;
            }
            // Insane mode random rotation
            Rotate rotate = new Rotate(90, anchorPane.getWidth() / 2, anchorPane.getHeight() / 2);
            while (gameTicks > 0 && insaneModeCheckBox.isSelected()) {
                int rotateRandom = (int) (Math.random() * 100) + 1;
                int randomSpawnUpperBorder = 5;
                if (tickLength <= tickLength / 2) {
                    randomSpawnUpperBorder = randomSpawnUpperBorder / 2;
                }
                if (rotateRandom >= 1 && rotateRandom <= randomSpawnUpperBorder) {
                    anchorPane.getTransforms().add(rotate);
                }
                break;
            }


            if(checkIfGameIsOver(snakeHead)) {
                timeline.stop();
            }
        }));
        food = new Food(-50, -50, anchorPane, foodSize);
    }

    // Changes direction with key pressed
    @FXML
    void moveSquareKeyPressed(KeyEvent event) {
        if(canChangeDirection){
            if (event.getCode().equals(KeyCode.W) && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (event.getCode().equals(KeyCode.S) && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (event.getCode().equals(KeyCode.A) && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (event.getCode().equals(KeyCode.D) && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
            canChangeDirection = false;
        }
    }

    // Creates another snake body part
    @FXML
    void addBodyPart(ActionEvent event) {
        addSnakeTail();
    }

    @FXML
    void handleInsaneMode() {
        // FIXME: Virker ikke helt, som den skal. Skal man bruge EventHandler her?
        /*if (insaneModeCheckBox.isSelected()) {
            insaneModeCheckBox.setSelected(true);
        } else {
            insaneModeCheckBox.setSelected(false);
        }*/
        insaneModeCheckBox.setSelected(true);
    }

    // Snake head is moved in the specified direction
    private void moveSnakeHead(Rectangle snakeHead) {
        if (direction.equals(Direction.RIGHT)) {
            xPos = xPos + snakeSize;
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.LEFT)) {
            xPos = xPos - snakeSize;
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.UP)) {
            yPos = yPos - snakeSize;
            snakeHead.setTranslateY(yPos);
        } else if (direction.equals(Direction.DOWN)) {
            yPos = yPos + snakeSize;
            snakeHead.setTranslateY(yPos);
        }
    }

    // A specific tail is moved to the position of the head x game ticks after the head was there
    private void moveSnakeTail(Rectangle snakeTail, int tailNumber) {
        double yPos = positions.get(gameTicks - tailNumber + 1).getYPos() - snakeTail.getY();
        double xPos = positions.get(gameTicks - tailNumber + 1).getXPos() - snakeTail.getX();
        snakeTail.setTranslateX(xPos);
        snakeTail.setTranslateY(yPos);
    }

    // New snake tail is created and added to the snake and the anchor pane
    private void addSnakeTail() {
        Rectangle rectangle = snakeBody.get(snakeBody.size() - 1);
        Rectangle snakeTail = new Rectangle(
                snakeBody.get(1).getX() + xPos + snakeSize,
                snakeBody.get(1).getY() + yPos,
                snakeSize, snakeSize);
        snakeTail.setFill(Color.rgb(0, 43, 54));
        snakeBody.add(snakeTail);
        anchorPane.getChildren().add(snakeTail);
    }

    public boolean checkIfGameIsOver(Rectangle snakeHead) {
        if (xPos > 300 || xPos < -250 ||yPos < -250 || yPos > 300) {
            System.out.println("Game Over");
            return true;
        } else if(snakeHitItSelf()) {
            return true;
        }
        return false;
    }

    public boolean snakeHitItSelf() {
        int size = positions.size() - 1;
        if(size > 2){
            for (int i = size - snakeBody.size(); i < size; i++) {
                if(positions.get(size).getXPos() == (positions.get(i).getXPos())
                        && positions.get(size).getYPos() == (positions.get(i).getYPos())){
                    System.out.println("Game Over: Snake hit itself");
                    return true;
                }
            }
        }
        return false;
    }

    private void eatFood(){
        if(xPos + snakeHead.getX() == food.getPosition().getXPos() && yPos + snakeHead.getY() == food.getPosition().getYPos()){
            System.out.println("Eat food");
            foodCantSpawnInsideSnake();
            addSnakeTail();
            score++;
            scoreLabel.setText("Score: " + score);
            snakeLength++;
            snakeLengthLabel.setText("Snake Length: " + snakeLength);
        }
    }

    private void foodCantSpawnInsideSnake() {
        food.moveFood();
        while (isFoodInsideSnake()) {
            food.moveFood();
        }
    }

    private boolean isFoodInsideSnake() {
        int size = positions.size();
        if(size > 2) {
            for (int i = size - snakeBody.size(); i < size; i++) {
                if(food.getPosition().getXPos() == (positions.get(i).getXPos())
                        && food.getPosition().getYPos() == (positions.get(i).getYPos())) {
                    System.out.println("Inside");
                    return true;
                }
            }
        }
        return false;
    }
}