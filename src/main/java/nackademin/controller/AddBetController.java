package nackademin.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import nackademin.model.Bet;
import nackademin.view.View;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AddBetController extends Controller {

    @FXML
    private DatePicker date_Picker;
    @FXML
    private TextField sport_Field, league_Field, odds_Field, stake_Field, team1_Field, team2_Field, line_Field;
    @FXML
    private MenuButton category_MenuButton, period_MenuButton;
    @FXML
    private Button add_Button;
    @FXML
    private Label versus_Label,date_Label, period_Label, sport_Label, league_Label, team1_Label, team2_Label;
    @FXML
    private VBox betDetails_Box;

    private View view;
    private ArrayList<TextField> fields;
    private String category, period, date, bet;

    @FXML
    public void initialize() {


        fields = new ArrayList<>();
        Collections.addAll(fields, sport_Field, league_Field, odds_Field, stake_Field, team1_Field, team2_Field, line_Field);

        betDetails_Box.setStyle("-fx-border-style: solid; -fx-border-width: 5px; -fx-border-color: black; -fx-background-color: white");
        period_MenuButton.setGraphic(new ImageView(String.valueOf(getClass().getResource("/stopwatch.png"))));

        odds_Field.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String c = "1234567890.";
            if (!c.contains(event.getCharacter())) {
                event.consume();
            }
        });
        stake_Field.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String c = "1234567890.";
            if (!c.contains(event.getCharacter())) {
                event.consume();
            }
        });

        sport_Field.textProperty().addListener((observable, oldValue, newValue) -> {
            sport_Label.setText(newValue);
        });
        league_Field.textProperty().addListener((observable, oldValue, newValue) -> {
            league_Label.setText(newValue);
        });
        team1_Field.textProperty().addListener((observable, oldValue, newValue) -> {
            team1_Label.setText(newValue);
            if (!team1_Label.getText().isEmpty())
                versus_Label.setText("v.");

        });
        team2_Field.textProperty().addListener((observable, oldValue, newValue) -> {
            team2_Label.setText(newValue);
        });



    }


    @FXML
    private void addBet() {
        for (TextField tf : fields) {
            if (tf.getText().isEmpty()) {
                return;
            }
        }
        if (category.isEmpty() || period.isEmpty() || date_Label.getText().isEmpty()){
            return;
        }

        super.getDatabase().addBet(date, sport_Label.getText(), league_Label.getText(), team1_Label.getText(),
                team2_Label.getText(), period, category, bet, line_Field.getText(), Double.valueOf(odds_Field.getText()),
                Double.valueOf(stake_Field.getText()));

        view.closeAddBetView();

    }

    @FXML
    private void setDateLabel() {
        if (date_Picker.getValue() == null)
            return;
        date_Label.setText(date_Picker.getValue().format(DateTimeFormatter.ofPattern("yy-MM-dd")));

    }

    @FXML
    private void setPeriod(ActionEvent event) {
        period_MenuButton.setGraphic(null);
        period_MenuButton.setText(((MenuItem) event.getSource()).getText());
        period = ((MenuItem) event.getSource()).getText();
        period_Label.setText(period);

    }

    @FXML
    private void setCategory(ActionEvent event) {
        category_MenuButton.setGraphic(null);
        bet = ((MenuItem) event.getSource()).getText();
        category =  ((MenuItem) event.getSource()).getParentMenu().getText();
        category_MenuButton.setText(category + " (" + bet + ")");

    }

    @Override
    public void setView(View view) {
        this.view = view;
    }
}