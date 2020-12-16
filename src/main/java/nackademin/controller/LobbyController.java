package nackademin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nackademin.model.Model;
import nackademin.view.View;

public class LobbyController extends Controller {
    @FXML
    Button login_Button;
    @FXML
    TextField username_Field;
    @FXML
    PasswordField password_Field;
    @FXML
    Button exit_Button;

    private View view;
    private Model model;


    @FXML
    private void login() {
        if (model.getUserDatabase().getUser().getUsername().equals(username_Field.getText()) &&
                model.getUserDatabase().getUser().getPassword().equals(password_Field.getText())) {
            System.out.println("User logged in...");
            view.loadPrimaryView();
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }


}