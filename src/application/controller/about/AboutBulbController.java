package application.controller.about;

import application.controller.MainframeController;
import application.model.fxcircuitry.BulbWidget;
import application.model.fxcircuitry.CircuitPane;
import application.model.util.fxtools.ZoomableScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutBulbController implements Initializable {
    @FXML
    CircuitPane canvas;
    @FXML
    ZoomableScrollPane scroller;
    @FXML
    Label label1, label2, label3, label4;
    @FXML
    SplitPane split;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);
        canvas.draggingNode = BulbWidget.create(canvas);
        canvas.draggingNode.setLayoutX(75000 - canvas.draggingNode.getPrefWidth() / 2);
        canvas.draggingNode.setLayoutY(75000 - canvas.draggingNode.getPrefHeight() / 2);

        ResourceBundle bundle = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
        label1.setText(bundle.getString("bulb"));
        label2.setText(bundle.getString("aboutBulblabel2"));
        label3.setText(bundle.getString("aboutBulblabel3"));
        label4.setText(bundle.getString("aboutBulblabel4"));

        MainframeController.currentOccurrence.locale.addListener(((observable, oldValue, newValue) -> {
            ResourceBundle bund = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
            label1.setText(bund.getString("bulb"));
            label2.setText(bund.getString("aboutBulblabel2"));
            label3.setText(bund.getString("aboutBulblabel3"));
            label4.setText(bund.getString("aboutBulblabel4"));
        }));
    }
}
