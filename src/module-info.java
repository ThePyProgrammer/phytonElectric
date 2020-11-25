module phyton {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;
    requires java.desktop;
    requires org.apache.commons.io;
    opens application;
    opens application.controller;
    opens application.controller.about;
    opens application.model.fxcircuitry;
    exports application.model.fxcircuitry;
    opens application.model.util.fxtools;
    opens application.view;
}