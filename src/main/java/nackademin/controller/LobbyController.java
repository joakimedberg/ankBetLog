package nackademin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    @FXML
    private void login() {
        if (super.getDatabase().getUser().getUsername().equals(username_Field.getText()) &&
                super.getDatabase().getUser().getPassword().equals(password_Field.getText())) {
            view.loadPrimaryView();
        }
    }

    @FXML
    private void exit() {
        super.getDatabase().closeConnection();
        System.exit(0);
    }
    @Override
    public void setView(View view) {
        this.view = view;
    }
}