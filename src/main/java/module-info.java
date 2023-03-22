module com.example.w11_snake_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.w11_snake_game to javafx.fxml;
    exports com.example.w11_snake_game;
}