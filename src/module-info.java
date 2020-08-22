module phyton {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires javafx.swing;
    requires java.desktop;
    requires java.logging;
    requires jdk.jsobject;
    requires gtranslateapi;
    opens application;
    opens application.controller;
    opens application.controller.about;
    opens application.model.fxcircuitry;
    exports application.model.fxcircuitry;
    opens application.view;
}