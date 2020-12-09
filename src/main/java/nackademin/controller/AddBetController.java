package nackademin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import nackademin.view.View;

public class AddBetController extends Controller {

    @FXML
    private DatePicker date_Picker;
    @FXML
    private TextField sport_Field, league_Field, odds_Field, stake_Field, team1_Field, team2_Field, line_Field;
    @FXML
    private MenuButton category_MenuButton;
    @FXML
    private Button add_Button;

    private View view;

    @FXML
    public void initialize() {
        Menu menu1 = new Menu("3WAY");
        Menu menu2 = new Menu("HCP");
        Menu menu3 = new Menu("O/U");

        MenuItem menuItem1 = new MenuItem("1");
        MenuItem menuItem2 = new MenuItem("2");
        MenuItem menuItem3 = new MenuItem("X");
        MenuItem menuItem4 = new MenuItem("Over");
        MenuItem menuItem5 = new MenuItem("Under");
        menu1.getItems().addAll(menuItem1, menuItem2, menuItem3);
        menu2.getItems().addAll(menuItem1, menuItem2);
        menu3.getItems().addAll(menuItem4, menuItem5);
        category_MenuButton.getItems().addAll(menu1, menu2, menu3);
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }
}