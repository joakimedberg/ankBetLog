package nackademin.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nackademin.model.Bet;
import nackademin.model.Game;
import nackademin.model.database.Database;
import nackademin.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class PrimaryController extends Controller {

    @FXML
    private Label name_Label, roi_Label, net_Label, stats_Label;
    @FXML
    TableView<DataForTable> bets_Table;
    @FXML
    TableColumn<DataForTable, Number> id_Column;
    @FXML
    TableColumn<DataForTable, String> date_Column;
    @FXML
    TableColumn<DataForTable, String> competition_Column;
    @FXML
    TableColumn<DataForTable, String> game_Column;
    @FXML
    TableColumn<DataForTable, Number> odds_Column;
    @FXML
    TableColumn<DataForTable, Number> stake_Column;
    @FXML
    TableColumn<DataForTable, Number> net_Column;
    @FXML
    TableColumn<DataForTable, String> edit_Column;
    @FXML
    Button addBet_Button;


    private View view;
    private int count;
    private ObservableList<DataForTable> bets;
    private String outcome;
    private Bet bet;


    public void updateTable(){

        bets.add(0,new DataForTable(Database.getBetDatabase().getBets().getLast(), Database.getBetDatabase().getBets().getLast().getGame()));
        bets_Table.refresh();
    }


    @FXML
    public void initialize() {

        name_Label.setText(Database.getUserDatabase().getUser().getUsername());
/*
        double roi = Math.round(super.getDatabase().getStatistics().getRoi() * 100 * 100) / 100;
        roi_Label.setText("ROI:" + roi + "%");
        net_Label.setText("NET:" + super.getDatabase().getStatistics().getNet());
        stats_Label.setText("WLP:" + super.getDatabase().getStatistics().getWon() + "/"
                + super.getDatabase().getStatistics().getLose() + "/" + super.getDatabase().getStatistics().getPush());
    */
        initTable();

        populateTable();

    }

    @FXML
    private void logout() {
        view.loadLobbyView();
    }

    @FXML
    private void addBet() {
        view.loadAddBetView(addBet_Button);
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }

    private void initTable() {
        count = 0;
        id_Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBet().getId()));
        date_Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getDate()));
        /* TODO
            competition is initially empty.
            just updates twice, one each.
         */
        competition_Column.setCellFactory(tc -> {

            TableCell<DataForTable, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };

            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty() && count == 0) {
                    competition_Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getSport()));
                    count = 1;

                } else if (!cell.isEmpty() && count == 1) {
                    competition_Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getLeague()));
                    count = 1;

                }
                bets_Table.refresh();
            });

            return cell;
        });
        game_Column.setCellFactory(cellData -> {

            TextFlow flow = new TextFlow();
            Text period = new Text();
            period.setStyle("-fx-font-style: italic;");
            Text team1 = new Text();
            Text team2 = new Text();
            Text versus = new Text(" v. ");
            Text line = new Text();
            line.setStyle("-fx-font-style : italic; -fx-font-weight : bold; -fx-font-size : 80%;");

            flow.getChildren().addAll(period, team1, versus, team2);
            TableCell<DataForTable, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        DataForTable data = getTableView().getItems().get(getIndex());
                        team1.setText(data.getGame().getTeam1());
                        team2.setText(data.getGame().getTeam2());
                        period.setText(data.getBet().getPeriod());
                        /*
                        if (data.getBet().getCategory().equals("hcp")) {
                            if (data.getBet().getBet().equals("1")) {
                                team1.setStyle("-fx-font-weight : bold;");
                                team1.setText(" (" + data.getBet().getLine() + ") " + data.getGame().getTeam1());
                                team2.setText(data.getGame().getTeam2());
                            } else if (data.getBet().getBet().equals("2")) {
                                team2.setStyle("-fx-font-weight : bold;");
                                team2.setText(data.getGame().getTeam1() + " (" + data.getBet().getLine() + ")");
                                team1.setText(data.getGame().getTeam2());
                            }
                        }
                        */
                        setPrefHeight(flow.prefHeight(game_Column.getWidth()) + 4);
                        setGraphic(flow);
                    }
                }
            };
            return cell;
        });
        odds_Column.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBet().getOdds()));
        stake_Column.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBet().getStake()));
        net_Column.setCellFactory(cellData -> {
            TableCell<DataForTable, Number> cell = new TableCell<>() {
                @Override
                public void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        DataForTable data = getTableView().getItems().get(getIndex());

                        setText(String.valueOf(data.getBet().getNet()));
                        if (data.getBet().getNet().equals("TBD")) {
                            setText("TBD");
                        }
                        else if (Double.valueOf(data.getBet().getNet()) < 0.0) {
                            setTextFill(Color.RED);
                        } else if (Double.valueOf(data.getBet().getNet()) == 0.0) {
                            setTextFill(Color.BLACK);
                        } else
                            setTextFill(Color.GREEN);

                    }
                }
            };
            return cell;
        });


        edit_Column.setCellFactory(cellData -> {

            ArrayList<MenuItem> list = new ArrayList<>();
            Collections.addAll(list, new MenuItem("WIN"), new MenuItem("1/2WIN"), new MenuItem("LOSS"), new MenuItem("1/2LOSS"), new MenuItem("PUSH"));
            MenuButton menuButton = new MenuButton();
            Menu menu = new Menu("GRADE");
            menuButton.getItems().add(menu);
            menu.getItems().addAll(list);

            TableCell<DataForTable, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    for (MenuItem i : list) {
                        i.setOnAction(event -> {
                            getTableView().getItems().get(getIndex()).getBet().setOutcome(i.getText());
                            //getDatabase().updateNetAndOutcome( getTableView().getItems().get(getIndex()).getBet());
                            bets_Table.refresh();
                        });
                    }
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(menuButton);
                    }
                }
            };
            return cell;
        });
    }

    private void populateTable() {
        bets = FXCollections.observableArrayList();
        for (Bet b : Database.getBetDatabase().getBets()) {
            bets.add(new DataForTable(b, b.getGame()));
        }
        bets_Table.setItems(bets);

    }
    private static class DataForTable {
        private final Bet bet;
        private final Game game;

        public DataForTable(Bet bet, Game game) {
            this.bet = bet;
            this.game = game;
        }

        public Bet getBet() {
            return bet;
        }

        public Game getGame() {
            return game;
        }
    }
}