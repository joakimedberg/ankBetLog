module ankBetLog {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens nackademin to javafx.fxml;
    opens nackademin.controller to javafx.fxml;
    opens nackademin.view to javafx.fxml;
    opens nackademin.model to javafx.fxml;
    exports nackademin.controller;
    exports nackademin.view;
    exports nackademin.model;
    exports nackademin;
}