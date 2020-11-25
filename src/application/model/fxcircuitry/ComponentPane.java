package application.model.fxcircuitry;

import application.Main;
import application.model.circuitry.Bulb;
import application.model.circuitry.Cell;
import application.model.circuitry.Component;
import application.model.circuitry.Resistor;
import application.model.quantity.Area;
import application.model.quantity.Intensity;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import application.model.util.HTMLManager;
import application.model.util.fxtools.DraggableNode;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ComponentPane<T extends ComponentWidget> extends DraggableNode {
    private final Component component;
    private final ComponentWidget widget;
    private CircuitPane pane;
    private ChangeListener<? super Number> listenerX, listenerY, listenerRotate;
    private NodeCircle c1, c2;
    private Tooltip tooltip;

    public ComponentPane(T widget) {
        component = widget.getComponent();
        layoutXProperty().addListener((observable, oldValue, newValue) -> component.xProperty().set((double) newValue + widget.getWidth() / 2 - 75000));
        layoutYProperty().addListener((observable, oldValue, newValue) -> component.yProperty().set(75000 - (double) newValue - widget.getHeight() / 2));
        getChildren().add(widget);
        this.widget = widget;

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                case DELETE:
                    delete();
                    break;
                default:
                    break;
            }
        });

        setTooltip();
        setOnMouseEntered(this::mouseEntered);
    }

    public void setTooltip() {
        try {
            if (tooltip != null) Tooltip.uninstall(this, tooltip);
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            InputStream inputStream = Main.class.getResourceAsStream("view/template.html");
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            Intensity intensity = new Intensity(component.getPower(), new Area(4 * Math.PI));
            HTMLManager manager = new HTMLManager(result);
            manager.substitute("component", component.getClass().getSimpleName())
                    .substitute("status", component.getCurrent().getValue() == 0 ? "notFunctioning" : "functioning")
                    .substitute("current", component.getCurrent().toString())
                    .substitute("functionOfVoltage", component instanceof Cell ? "supplying" : "using")
                    .substitute("voltage", component.getVoltage().toString())
                    .substitute("isBulb", component instanceof Bulb ? "isBulb" : "notBulb")
                    .substitute("bulbInfo", "This " + component.getClass().getSimpleName() + " has an approximate intensity of <b>" + intensity.toString() + "</b> from <b>1 m</b> of distance.")
                    .substitute("resistanceInfo", "This " + component.getClass().getSimpleName() + " has an overall resistance of <b>" + component.getResistance().toString() + "</b>.")
                    .substitute("isCell", component instanceof Cell ? "isCell" : "notCell")
                    .substitute("cellInfo", "This " + component.getClass().getSimpleName() + " has a power of <b>" + component.getPower().toString() + "</b>.");
            webEngine.loadContent(manager.toString());
            webEngine.setUserStyleSheetLocation(Main.class.getResource("view/stylesheets/tooltip.css").toString());

            tooltip = new Tooltip();
            tooltip.setPrefSize(400, 250);
            tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            tooltip.setGraphic(webView);

            tooltip.setShowDelay(Duration.millis(50));

            Tooltip.install(this, tooltip);

        } catch (IOException ignored) {
        }
    }

    public void mouseEntered(MouseEvent event) {
        process();
    }

    public void process() {
        if (pane != null) pane.processComponent(this);
    }

    public NodeCircle getC1() {
        return c1;
    }

    public void setC1(NodeCircle c1) {
        this.c1 = c1;
    }

    public NodeCircle getC2() {
        return c2;
    }

    public void setC2(NodeCircle c2) {
        this.c2 = c2;
    }

    public void delete() {
        pane.remove(this);
    }

    public void setPane(CircuitPane pane) {
        this.pane = pane;
    }

    @Override
    public void toFront() {
        super.toFront();
        c1.toFront();
        c2.toFront();
        try {
            pane.getController().setMainNode(this);
        } catch (NullPointerException ignored) {
        }
    }

    public Component getComponent() {
        return component;
    }

    public ChangeListener<? super Number> getListenerRotate() {
        return listenerRotate;
    }

    public void setListener(ChangeListener<? super Number> listenerX, ChangeListener<? super Number> listenerY, ChangeListener<? super Number> listenerRotate) {
        this.listenerX = listenerX;
        this.listenerY = listenerY;
        this.listenerRotate = listenerRotate;
        layoutXProperty().addListener(listenerX);
        layoutYProperty().addListener(listenerY);
        widget.rotateProperty().addListener(listenerRotate);
    }

    public NodeCircle getAvailableC() {
        if (c1.connectedTo.get() == null) return c1;
        else return c2;
    }

    public void setElement(Element element) {
        toFront();
        if (getComponent() instanceof Cell) {
            if (element.hasAttribute("emf"))
                ((Cell) getComponent()).setEMF(
                        new Voltage(
                                Double.parseDouble(
                                        element.getAttribute("emf").substring(0, element.getAttribute("emf").length() - 1).trim()
                                )
                        )
                );
            boolean isNegative = false;
            if (element.hasAttribute("isNegative"))
                isNegative = Boolean.parseBoolean(element.getAttribute("isNegative"));
            ((Cell) getComponent()).setNegative(isNegative);

        } else if (getComponent() instanceof Bulb | getComponent() instanceof Resistor) {
            if (element.hasAttribute("R"))
                getComponent().setResistance(
                        new Resistance(
                                Double.parseDouble(
                                        element.getAttribute("R").substring(0, element.getAttribute("R").length() - 1).trim()
                                )
                        )
                );

        }
    }

    @Override
    public Node createWidget() {
        return new Pane();
    }

    public ComponentWidget getWidget() {
        return widget;
    }
}
