package nackademin;

import javafx.application.Application;
import javafx.stage.Stage;
import nackademin.view.View;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        new View(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}