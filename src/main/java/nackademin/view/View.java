package nackademin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nackademin.controller.Controller;
import nackademin.controller.LobbyController;
import nackademin.controller.PrimaryController;


import java.io.IOException;

public class View {

    private Stage stage;
    private Parent parent;

    public View(Stage stage)  {
        this.stage = stage;
        stage.setTitle("ankBetLog");
        loadLobbyView();

    }

    public void loadPrimaryView() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
            parent = loader.load();
            PrimaryController controller = loader.getController();
            controller.setView(this);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loadLobbyView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
            parent = loader.load();
            LobbyController controller = loader.getController();
            controller.setView(this);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
