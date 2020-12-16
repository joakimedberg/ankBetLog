package nackademin.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nackademin.controller.AddBetController;
import nackademin.controller.LobbyController;
import nackademin.controller.PrimaryController;
import nackademin.model.Model;
import org.controlsfx.control.PopOver;


import java.io.IOException;

public class View {

    private Stage stage;
    private Parent parent;
    private PopOver pop;
    private PrimaryController primaryController;
    private Model model;


    public View(Stage stage)  {
        this.stage = stage;
        stage.setTitle("ankBetLog");
        model = new Model();
        loadLobbyView();



    }

    public void loadPrimaryView() {

        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
            parent = loader.load();
            primaryController = loader.getController();
            primaryController.setView(this);
            primaryController.setModel(model);

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
            LobbyController lobbyController = loader.getController();
            lobbyController.setView(this);
            lobbyController.setModel(model);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAddBetView(Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addbet.fxml"));
            Parent parent = loader.load();
            AddBetController addBetController = loader.getController();
            addBetController.setView(this);
            addBetController.setModel(model);
            addBetController.setPrimaryController(primaryController);

            pop = new PopOver(new Pane(parent));
            pop.show(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAddBetView() {
        pop.hide();
    }
}
