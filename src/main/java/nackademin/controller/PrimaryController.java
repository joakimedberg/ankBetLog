package nackademin.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import nackademin.model.Model;
import nackademin.model.entity.Bet;
import nackademin.model.entity.Game;
import nackademin.model.entity.Statistics;
import nackademin.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class PrimaryController implements Controller {

    @FXML
    private Label name_Label, roi_Label, net_Label, stats_Label;
    @FXML
    private TableView<DataForTable> bets_Table;
    @FXML
    private TableColumn<DataForTable, Number> id_Column;
    @FXML
    private TableColumn<DataForTable, String> date_Column;
    @FXML
    private TableColumn<DataForTable, String> competition_Column;
    @FXML
    private TableColumn<DataForTable, String> game_Column;
    @FXML
    private TableColumn<DataForTable, Number> odds_Column;
    @FXML
    private TableColumn<DataForTable, Number> stake_Column;
    @FXML
    private TableColumn<DataForTable, Number> net_Column;
    @FXML
    private TableColumn<DataForTable, String> edit_Column;
    @FXML
    private Button addBet_Button;


    private View view;
    private int count;
    private Model model;
    private ObservableList<DataForTable> bets;


    public void updateTable(){

        Bet bet = model.getBetDatabase().getBets().getFirst();
        bets.add(0,new DataForTable(bet, bet.getGame()));
        bets_Table.refresh();
    }



    private void init() {
        name_Label.setText(model.getUserDatabase().getUser().getUsername());
        bets_Table.getStyleClass().add("no-header");
        updateStatistics();
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

    @Override
    public void setModel(Model model) {
        this.model = model;
        init();
    }

    private void initTable() {
        count = 0;
        id_Column.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBet().getId()));
        date_Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getDate()));
        competition_Column
                .setCellValueFactory(cellData -> new SimpleStringProperty(
                        cellData.getValue().getBet().getGame().getLeague()));
       
        game_Column.setCellFactory(cellData -> {

            TextFlow flow = new TextFlow();
            Text period = new Text();
            period.setStyle("-fx-font-style: italic;");
            Text team1 = new Text();
            Text team2 = new Text();
            Text versus = new Text(" v. ");
            Text line = new Text("");
            line.setStyle("-fx-font-style : italic; -fx-font-weight : bold; -fx-font-size : 80%;");

            flow.getChildren().addAll(period, team1, versus, team2, line);
            return new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        DataForTable data = getTableView().getItems().get(getIndex());

                        period.setText(data.getBet().getPeriod() + " ");

                        switch (data.getBet().getCategory()) {
                            case "HCP":
                                if (data.getBet().getBet().equals("1")) {
                                    team1.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    line.setText(" (" + data.getBet().getLine() + ") ");
                                    team2.setText(data.getGame().getTeam2());

                                } else if (data.getBet().getBet().equals("2")) {
                                    team2.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    line.setText(" (" + data.getBet().getLine() + ")");
                                    team2.setText(data.getGame().getTeam2());

                                }
                                break;
                            case "OU":
                                if (data.getBet().getBet().equals("Over")) {
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());
                                    line.setText(" (OVER " + data.getBet().getLine() + ")");
                                } else if (data.getBet().getBet().equals("Under")) {
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());
                                    line.setText(" (UNDER " + data.getBet().getLine() + ")");
                                }
                                break;
                            case "ML":
                                if (data.getBet().getBet().equals("1")) {
                                    team1.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());

                                } else if (data.getBet().getBet().equals("2")) {
                                    team2.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());

                                }
                                break;
                        }
                        if (data.getBet().getCategory().equals("3WAY")) {
                            switch (data.getBet().getBet()) {
                                case "1":
                                    team1.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());

                                    break;
                                case "2":
                                    team2.setStyle("-fx-font-weight : bold;");
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());
                                    break;
                                case "X":
                                    team1.setText(data.getGame().getTeam1());
                                    team2.setText(data.getGame().getTeam2());
                                    line.setText(" X ");
                                    break;
                            }
                        }

                        setPrefHeight(flow.prefHeight(game_Column.getWidth()) + 4);
                        setGraphic(flow);
                    }
                }
            };
        });
        odds_Column.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBet().getOdds()));
        stake_Column.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBet().getStake()));
        net_Column.setCellFactory(cellData -> new TableCell<>() {
            @Override
            public void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    DataForTable data = getTableView().getItems().get(getIndex());

                    setText(String.valueOf(data.getBet().getNet()));
                    if (data.getBet().isTbd()) {
                        setText("TBD");
                    } else if (data.getBet().isVoided()) {
                        setText("void");
                    } else if (data.getBet().getNet() < 0.0) {
                        setTextFill(Color.RED);
                    } else if (data.getBet().getNet() == 0.0) {
                        setTextFill(Color.BLACK);
                    } else
                        setTextFill(Color.GREEN);

                }
            }
        });

        edit_Column.setCellFactory(cellData -> {

            ArrayList<MenuItem> list = new ArrayList<>();
            Collections.addAll(list, new MenuItem("WIN"), new MenuItem("1/2WIN"), new MenuItem("LOSS"),
                    new MenuItem("1/2LOSS"), new MenuItem("PUSH"), new MenuItem("void"));
            MenuButton menuButton = new MenuButton();
            Menu menu = new Menu("GRADE");
            menuButton.getItems().add(menu);
            menu.getItems().addAll(list);

            return new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    for (MenuItem i : list) {
                        i.setOnAction(event -> {

                            if (i.getText().equals("void")) {
                                getTableView().getItems().get(getIndex()).getBet().setVoided(true);
                                i.setText("void");
                            } else {
                                getTableView().getItems().get(getIndex()).getBet().setOutcome(i.getText());
                            }

                            bets_Table.refresh();
                            updateStatistics();
                        });
                    }
                    if (empty) {
                        setGraphic(null);
                    } else {
                        if (!getTableView().getItems().get(getIndex()).getBet().isTbd()) {
                            for (int i = 0; i < 5; i++) {
                                list.get(i).setDisable(true);
                            }
                        }
                        if (!getTableView().getItems().get(getIndex()).getBet().isVoided()) {
                            setGraphic(menuButton);
                        }

                    }
                }
            };
        });
    }

    private void populateTable() {
        bets = FXCollections.observableArrayList();
        for (Bet b : model.getBetDatabase().getBets()) {
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

    private void updateStatistics() {
        Statistics statistics = model.getStatisticsDatabase().getStatistics();
        double roi = Math.round(statistics.getRoi() * 100 * 100) / 100;
        roi_Label.setText("ROI:" + roi + "%");
        net_Label.setText("NET:" + statistics.getNet());
        stats_Label.setText("WLP:" + statistics.getWon() + "/"
                + statistics.getLose() + "/" + statistics.getPush());

    }
}