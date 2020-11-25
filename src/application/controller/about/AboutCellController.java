package application.controller.about;

import application.controller.MainframeController;
import application.model.fxcircuitry.CellWidget;
import application.model.fxcircuitry.CircuitPane;
import application.model.util.fxtools.ZoomableScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutCellController implements Initializable {
    @FXML
    CircuitPane canvas;
    @FXML
    ZoomableScrollPane scroller;
    @FXML
    Label label1, label2, label3, label4, label5;
    @FXML
    SplitPane split;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);
        canvas.draggingNode = CellWidget.create(canvas);
        canvas.draggingNode.setLayoutX(75000 - canvas.draggingNode.getPrefWidth() / 2);
        canvas.draggingNode.setLayoutY(75000 - canvas.draggingNode.getPrefHeight() / 2);

        ResourceBundle bundle = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
        label1.setText(bundle.getString("cell"));
        label2.setText(bundle.getString("aboutCelllabel2"));
        label3.setText(bundle.getString("aboutCelllabel3"));
        label4.setText(bundle.getString("aboutCelllabel4"));
        label5.setText(bundle.getString("references"));

        MainframeController.currentOccurrence.locale.addListener(((observable, oldValue, newValue) -> {
            ResourceBundle bund = ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get());
            label1.setText(bund.getString("cell"));
            label2.setText(bund.getString("aboutCelllabel2"));
            label3.setText(bund.getString("aboutCelllabel3"));
            label4.setText(bund.getString("aboutCelllabel4"));
            label5.setText(bund.getString("references"));
        }));
    }

    @FXML
    public void openURL(ActionEvent event) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("http://resources.schoolscience.co.uk/britishenergy/11-14/circh1pg2.html"));
            }
        } catch (URISyntaxException | IOException ex) {
        }
    }
}
