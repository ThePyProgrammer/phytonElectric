package application.controller;

import application.model.circuitry.Cell;
import application.model.circuitry.Component;
import application.model.circuitry.Series;
import application.model.fxcircuitry.*;
import application.model.quantity.Current;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import application.model.util.fxtools.AngleChooser;
import application.model.util.fxtools.ZoomableScrollPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class TabController implements Initializable {
    public static HashMap<VBox, TabController> panes = new HashMap<>();

    @FXML
    public SplitPane split;
    @FXML
    public ZoomableScrollPane scroller;
    @FXML
    public CircuitPane canvas;
    @FXML
    public GridPane details;
    @FXML
    public VBox parent;
    @FXML
    public Label component, cellLabel, resistorLabel, bulbLabel, cursorCoords, emfLabel, ReffLabel,
            IeffLabel, PLabel, rLabel, widgets, detailsLabel, property, value;
    @FXML
    public TextField emf, reff, Ieff, power, resistance;
    @FXML
    public AngleChooser angler;
    public ObjectProperty<File> file = new SimpleObjectProperty<>();
    @FXML
    TitledPane powerSources, components;

    public static TabController getController(Node node) {
        if (panes.containsKey(node)) return panes.get(node);
        return null;
    }

    @FXML
    public void cellDragDetected(MouseEvent event) {
        createDrag(CellWidget.class, event);
    }

    @FXML
    public void resistorDragDetected(MouseEvent event) {
        createDrag(ResistorWidget.class, event);
    }

    @FXML
    public void bulbDragDetected(MouseEvent event) {
        createDrag(BulbWidget.class, event);
    }

    @FXML
    public void parallelNodeDragDetected(MouseEvent event) {
        createDrag(ParallelNodeWidget.class, event);
    }

    public void createDrag(Class<? extends ComponentWidget> cls, MouseEvent event) {
        try {
            if (cls.getSuperclass() == ComponentWidget.class) {
                canvas.newNode = (ComponentPane) cls.getMethod("create", CircuitPane.class).invoke(null, canvas);
                Point2D point = canvas.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                canvas.newNode.setLayoutX(point.getX() - canvas.newNode.getPrefWidth() / 2);
                canvas.newNode.setLayoutY(point.getY() - canvas.newNode.getPrefHeight() / 2);
                canvas.newNode.startFullDrag();
                canvas.newNode.getWidget().getStyleClass().add("focused");

            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        }
    }

    @FXML
    public void drop(MouseEvent event) {
        if (canvas.newNode != null) {
            Point2D point = canvas.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

            canvas.newNode.setLayoutX(point.getX() - canvas.newNode.getPrefWidth() / 2);
            canvas.newNode.setLayoutY(point.getY() - canvas.newNode.getPrefHeight() / 2);
            canvas.newNode.getWidget().getStyleClass().remove("focused");
            canvas.draggingNode = canvas.newNode;
            canvas.newNode = null;
            setMainNode(canvas.draggingNode);
        }
    }

    public void normaliseComponent(MouseEvent event) {
        canvas.draggingNode.setLayoutX(75000 - canvas.draggingNode.getPrefWidth() / 2);
        canvas.draggingNode.setLayoutY(75000 - canvas.draggingNode.getPrefHeight() / 2);
        canvas.draggingNode.getWidget().getStyleClass().add("focused");
    }

    @FXML
    public void addCell(MouseEvent ev) {
        if (ev.getClickCount() >= 2) {
            canvas.draggingNode = CellWidget.create(canvas);
            normaliseComponent(ev);
        }
    }

    @FXML
    public void addResistor(MouseEvent ev) {
        if (ev.getClickCount() >= 2) {
            canvas.draggingNode = ResistorWidget.create(canvas);
            normaliseComponent(ev);
        }
    }

    @FXML
    public void addBulb(MouseEvent ev) {
        if (ev.getClickCount() >= 2) {
            canvas.draggingNode = BulbWidget.create(canvas);
            normaliseComponent(ev);
        }
    }

    @FXML
    public void cursorEnter(MouseEvent e) {
        cursorCoords.setVisible(true);
    }

    @FXML
    public void cursorMove(MouseEvent event) {
        cursorCoords.setText(String.format("Cursor at (%.2f, %.2f)", event.getX() - 75000, 75000 - event.getY()));
    }

    @FXML
    public void cursorExit(MouseEvent event) {
        cursorCoords.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        panes.put(parent, this);
        canvas.setController(this);

        // setting to point (0.0, 0.0)
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);
    }

    public File getFile() {
        return file.get();
    }

    public void setFile(File file) {
        this.file.set(file);
    }

    public ObjectProperty<File> fileProperty() {
        return file;
    }

    public void setMainNode(ComponentPane pane) {
        if (canvas.draggingNode != null) canvas.draggingNode.getWidget().getStyleClass().remove("focused");
        canvas.draggingNode = pane;
        canvas.draggingNode.getWidget().getStyleClass().add("focused");
        details.setDisable(false);
        component.setText("phyton.workspace." + MainframeController.currentOccurrence.getNameOfTab() + "." + pane.getComponent().getClass().getSimpleName());
        try {
            angler.bind(pane);
        } catch (NullPointerException ex) {
        }
        if (pane.getComponent() instanceof Cell) {
            emfLabel.setText(ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get()).getString("emf"));
            emf.setDisable(false);
            Cell main = ((Cell) pane.getComponent());
            if (main.getLeft().getConnected() == null && main.getRight().getConnected() == null) {
                main.setCurrent(new Current(0));
                emf.setText(main.getEMF().toString());
                power.setText(main.getPower().toString());
                reff.setText(main.getResistance().toString());
                Ieff.setText(main.getCurrent().toString());
                resistance.setDisable(true);
                rLabel.setDisable(true);
                resistance.setText("");
                emf.setOnAction(event -> {
                    String text = emf.getText().trim();
                    if (text.endsWith("V"))
                        main.setEMF(new Voltage(Double.parseDouble(text.substring(0, text.length() - 1).trim())));
                    if (text.endsWith(Voltage.unit))
                        main.setEMF(new Voltage(Double.parseDouble(text.substring(0, text.length() - Voltage.unit.length()).trim())));
                    try {
                        main.setEMF(new Voltage(Double.parseDouble(text.trim())));
                    } catch (NumberFormatException ex) {
                    }
                    setMainNode(pane);
                });
                return;
            }
        } else {
            emfLabel.setText(ResourceBundle.getBundle("langBundle", MainframeController.currentOccurrence.locale.get()).getString("pd"));
            emf.setDisable(true);
        }
        Component main = pane.getComponent();
        Series series = canvas.processComponent(pane);
        if (main instanceof Cell) {
            if (!series.getArray().contains(main)) {
                main.setCurrent(new Current(series.getCurrent().negate().getValue()));
            }
            emf.setText(((Cell) main).getEMF().toString(MainframeController.currentOccurrence.locale.get()));
            power.setText(main.getPower().toString(MainframeController.currentOccurrence.locale.get()));
            resistance.setDisable(true);
            rLabel.setDisable(true);
            resistance.setText("");
            emf.setOnAction(event -> {
                String text = emf.getText().trim();
                if (text.endsWith("V"))
                    ((Cell) main).setEMF(new Voltage(Double.parseDouble(text.substring(0, text.length() - 1).trim())));
                if (text.endsWith(Voltage.unit))
                    ((Cell) main).setEMF(new Voltage(Double.parseDouble(text.substring(0, text.length() - Voltage.unit.length()).trim())));
                try {
                    ((Cell) main).setEMF(new Voltage(Double.parseDouble(text.trim())));
                } catch (NumberFormatException ex) {
                }

                setMainNode(pane);
            });
        } else {
            emf.setText(main.getVoltage().toString(MainframeController.currentOccurrence.locale.get()));
            power.setText(series.getPower().toString(MainframeController.currentOccurrence.locale.get()));
            resistance.setDisable(false);
            rLabel.setDisable(false);
            resistance.setText(main.getResistance().toString(MainframeController.currentOccurrence.locale.get()));
            resistance.setOnAction(event -> {
                String text = resistance.getText().trim();
                try {
                    if (text.endsWith("Ω"))
                        main.setResistance(new Resistance(Double.parseDouble(text.substring(0, text.length() - 1).trim())));
                    else if (text.endsWith(Resistance.unit))
                        main.setResistance(new Resistance(Double.parseDouble(text.substring(0, text.length() - Resistance.unit.length()).trim())));
                    else main.setResistance(new Resistance(Double.parseDouble(text.trim())));
                } catch (NumberFormatException ex) {
                }

                setMainNode(pane);
            });
        }
        reff.setText(series.getResistance().toString(MainframeController.currentOccurrence.locale.get()));
        Ieff.setText(main.getCurrent().toString(MainframeController.currentOccurrence.locale.get()));
        if (!series.isClosed()) {
            Ieff.setText("0 A");
            power.setText("0 W");
        }
    }
}
