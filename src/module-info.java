module pbl.project {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.fxml;
    requires javafx.swt;
    requires javafx.media;
    requires javafx.web;
    requires java.sql;

    exports main.dao;
    exports main;

    opens main.dao;
    opens main;

    opens main.controllers to javafx.fxml;
    opens main.model to javafx.base;
}