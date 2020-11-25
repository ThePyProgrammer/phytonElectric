package application.model.fxcircuitry;

import application.controller.TabController;
import application.model.circuitry.*;
import application.model.quantity.Current;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import application.model.util.File;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircuitPane extends Pane {
    public static Wire beingDragged;
    private static final Lock lock = new ReentrantLock();
    public ComponentPane draggingNode, newNode;
    HashMap<ComponentPane, DoubleProperty> xCoords = new HashMap<>();
    HashMap<ComponentPane, DoubleProperty> yCoords = new HashMap<>();
    HashMap<Component, Series> series = new HashMap<>();
    ArrayList<ComponentPane> panes = new ArrayList<>();
    private TabController controller;
    private String saved = toString();

    public CircuitPane() {
        setOnMouseDragOver(this::handleDrag);
        setOnMouseDragged(this::handleDrag);
    }

    public void handleDrag(MouseEvent event) {
        if (beingDragged != null) beingDragged.setEnd(event.getX(), event.getY());
        else if (newNode != null) {
            Point2D point = sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
            newNode.setLayoutX(point.getX() - newNode.getPrefWidth() / 2);
            newNode.setLayoutY(point.getY() - newNode.getPrefHeight() / 2);
        } else if (draggingNode != null)
            draggingNode.getWidget().getStyleClass().remove("focused");
    }

    public void add(ComponentPane widget, DoubleProperty x, DoubleProperty y) {
        getChildren().add(widget);
        widget.setPane(this);
        xCoords.put(widget, x);
        yCoords.put(widget, y);
        series.put(widget.getComponent(), new Series(widget.getComponent()));
        widget.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            x.set((double) newValue + widget.getWidth() / 2 - 75000);
        });
        widget.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            y.set(75000 - (double) newValue - widget.getHeight() / 2);
        });
        panes.add(widget);
    }

    public void remove(ComponentPane widget) {
        getChildren().remove(widget);
        xCoords.remove(widget);
        yCoords.remove(widget);
        series.remove(widget.getComponent());
        panes.remove(widget);
    }

    public void add(ComponentPane widget) {
        DoubleProperty x = new SimpleDoubleProperty();
        x.set(0);
        DoubleProperty y = new SimpleDoubleProperty();
        y.set(0);
        add(widget, x, y);
    }

    public void clear() {
        for (ComponentPane pane : panes) remove(pane);
    }

    public void parse(File file) {
        lock.lock();

        try {
            String reader = file.read();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    reader.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Series");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    boolean isClosed = Boolean.parseBoolean(eElement.getAttribute("isClosed"));
                    NodeList children = eElement.getChildNodes();
                    ComponentPane previous = null, original = null;
                    for (int i = 0; i < children.getLength(); i++) {
                        org.w3c.dom.Node node = children.item(i);
                        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            String name = element.getTagName().trim();
                            NodeCircle prev = null;

                            ComponentPane pane;

                            switch (name) {
                                case "Resistor":
                                    pane = ResistorWidget.create(this);
                                    pane.toFront();
                                    if (element.hasAttribute("R"))
                                        pane.getComponent().setResistance(new Resistance(Double.parseDouble(element.getAttribute("R").substring(0, element.getAttribute("R").length() - 1).trim())));

                                    prev(previous, element, pane);
                                    break;
                                case "Cell":
                                    pane = CellWidget.create(this);
                                    pane.toFront();
                                    if (element.hasAttribute("emf"))
                                        ((Cell) pane.getComponent()).setEMF(new Voltage(Double.parseDouble(element.getAttribute("emf").substring(0, element.getAttribute("emf").length() - 1).trim())));
                                    if (element.hasAttribute("prev") && previous != null && element.getAttribute("prev").trim().length() == 2) {
                                        String val = element.getAttribute("prev").trim();
                                        if (val.equals("c1")) prev = pane.getC1();
                                        else if (val.equals("c2")) prev = pane.getC2();
                                        NodeCircle c = previous.getAvailableC();
                                        c.newWire();
                                        c.connectTo(prev);
                                    } else if (previous != null) {
                                        boolean isNegative = false;
                                        if (element.hasAttribute("isNegative"))
                                            isNegative = Boolean.parseBoolean(element.getAttribute("isNegative"));
                                        NodeCircle cOther = previous.getAvailableC(), cSelf;
                                        if (isNegative && previous.getComponent() instanceof Cell) {
                                            if (cOther.equals(previous.getC1())) cSelf = pane.getC1();
                                            else cSelf = pane.getC2();
                                        } else if (!isNegative && previous.getComponent() instanceof Cell) {
                                            if (cOther.equals(previous.getC1())) cSelf = pane.getC2();
                                            else cSelf = pane.getC1();
                                        } else {
                                            cSelf = cOther.bestC(pane);
                                        }
                                        cSelf.newWire();
                                        cSelf.connectTo(cOther);


                                    }
                                    break;
                                case "Bulb":
                                    pane = BulbWidget.create(this);
                                    pane.toFront();
                                    if (element.hasAttribute("R"))
                                        pane.getComponent().setResistance(new Resistance(Double.parseDouble(element.getAttribute("R").substring(0, element.getAttribute("R").length() - 1).trim())));
                                    prev(previous, element, pane);
                                    break;
                                default:
                                    continue;
                            }

                            double x = 0, y = 0, angle = 0;
                            if (element.hasAttribute("x")) x = Double.parseDouble(element.getAttribute("x"));
                            if (element.hasAttribute("y")) y = Double.parseDouble(element.getAttribute("y"));
                            if (element.hasAttribute("angle"))
                                angle = Double.parseDouble(element.getAttribute("angle"));
                            pane.toFront();
                            pane.setLayoutX(75000 + x - pane.getWidth() / 2);
                            pane.setLayoutY(75000 - y - pane.getHeight() / 2);
                            pane.getWidget().setRotate(angle);
                            previous = pane;
                            if (original == null) original = pane;
                        }

                    }

                    if (isClosed && original != null && original.getAvailableC() != null && previous.getAvailableC() != null) {
                        NodeCircle c = original.getAvailableC();
                        c.newWire();
                        c.connectTo(previous.getAvailableC());
                    }
                    beingDragged = null;
                    saved = reader;
                }
            }
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    private void prev(ComponentPane previous, Element element, ComponentPane pane) {
        NodeCircle prev;
        if (element.hasAttribute("prev") && previous != null && element.getAttribute("prev").trim().length() == 2) {
            String val = element.getAttribute("prev").trim();
            if (val.equals("c1")) prev = pane.getC1();
            else prev = pane.getC2();
            NodeCircle c = previous.getAvailableC();
            c.newWire();
            c.connectTo(prev);
        } else if (previous != null) {
            NodeCircle c = previous.getAvailableC();
            c.newWire();
            c.connectTo(c.bestC(pane));
        }
    }

    public void save() {
        saved = toString();
    }

    public boolean isSaved() {
        return saved.equals(toString());
    }

    public double getX(ComponentPane pane) {
        return xCoords.get(pane).get();
    }

    public double getY(ComponentPane pane) {
        return yCoords.get(pane).get();
    }

    public HashMap<Component, Series> getSeries() {
        return series;
    }

    public TabController getController() {
        return controller;
    }

    public void setController(TabController controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        for (ComponentPane pane : panes) {
            processComponent(pane);
        }
        ArrayList<String> sortedArray = new ArrayList<>();
        ArrayList<Series> coveredSeries = new ArrayList<>();
        Set<Component> componentsConsidered = new HashSet<>();

        StringBuilder builder = new StringBuilder("<CircuitPane>\n");
        series.forEach((component, series) -> {
            if (!componentsConsidered.contains(component)) {
                if (series == null) builder.append("\t").append(component).append("\n\n");
                else {
                    if (!contains(sortedArray, series.sortedToString()) && !contains(coveredSeries, series)) {
                        sortedArray.add(series.sortedToString());
                        for (String sub : series.toString().split("\n")) {
                            builder.append("\t" + sub + "\n");
                        }
                        builder.append("\n");
                        coveredSeries.add(series);
                        componentsConsidered.add(component);
                        componentsConsidered.addAll(series.getArray());
                    }
                }
            }
        });
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n\n" + builder.toString() + "</CircuitPane>";
    }

    public boolean contains(ArrayList list, Object o) {
        for (Object obj : list) {
            if (obj.equals(o)) return true;
        }
        return false;
    }

    public Parallel processParallel(ComponentPane pane) {
        ParallelCircle node = ((ParallelCircle) pane.getC1());
        Parallel parallel = new Parallel(node.getNode());
        for (NodeCircle connectedTo : node.connectedNodes()) {
            Series series = processComponent(connectedTo.getSuperPane());
            parallel.add(series);
        }
        return parallel;
    }

    public Series processComponent(ComponentPane pane) {
        Component main = pane.getComponent();
        ArrayList<Cell> left_to_right = new ArrayList<>(), right_to_left = new ArrayList<>();
        if (main instanceof Cell) left_to_right.add((Cell) main);
        ArrayList<Component> components = new ArrayList<>(), actual = new ArrayList<>();
        components.add(main);
        application.model.circuitry.Node left = main.getLeft();
        application.model.circuitry.Node right = main.getRight();
        boolean isClosed = false;
        while (left.getConnected() != null) {
            if (left instanceof ParallelNode) {
                return processParallel(((ParallelNode) left).getPane());
            }
            if (components.contains(left.getConnected().getSelf())) {
                isClosed = true;
                break;
            }

            if (left.getConnected().getSelf() instanceof Cell) {
                if (left.getConnected().getSelf().getRight().equals(left.getConnected()))
                    left_to_right.add((Cell) left.getConnected().getSelf());
                else right_to_left.add((Cell) left.getConnected().getSelf());
            }

            components.add(0, left.getConnected().getSelf());
            actual.add(0, left.getConnected().getSelf());
            left = left.getConnected().getPair();
        }
        while (right.getConnected() != null) {
            if (right instanceof ParallelNode) {
                return processParallel(((ParallelNode) right).getPane());
            }
            if (components.contains(right.getConnected().getSelf())) {
                isClosed = true;
                break;
            }
            //series.add(right.getConnected().getSelf());

            if (right.getConnected().getSelf() instanceof Cell) {
                if (right.getConnected().getSelf().getLeft().equals(right.getConnected()))
                    left_to_right.add((Cell) right.getConnected().getSelf());
                else right_to_left.add((Cell) right.getConnected().getSelf());
            }

            components.add(right.getConnected().getSelf());
            actual.add(right.getConnected().getSelf());
            right = right.getConnected().getPair();
        }

        Voltage emf_ltr = new Voltage(0), emf_rtl = new Voltage(0);
        for (Cell cell : left_to_right) emf_ltr.iadd(cell.getEMF());
        for (Cell cell : right_to_left) emf_rtl.iadd(cell.getEMF());
        if (emf_ltr.compareTo(emf_rtl) > 0) {
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof Cell && right_to_left.contains(components.get(i))) {
                    components.set(i, ((Cell) components.get(i)).negate());
                }
            }
        } else if (emf_ltr.compareTo(emf_rtl) < 0) {
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof Cell && left_to_right.contains(components.get(i))) {
                    components.set(i, ((Cell) components.get(i)).negate());
                }
            }
        } else if (emf_ltr.compareTo(emf_rtl) == 0) {
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof Cell && right_to_left.contains(components.get(i))) {
                    components.set(i, ((Cell) components.get(i)).negate());
                }
            }
        }
        Series series = new Series(components);
        series.setClosed(isClosed);

        for (Component component : actual) {
            getSeries().put(component, series);
        }

        if (!isClosed) {
            series.setCurrent(new Current(0));
        }

        pane.setTooltip();

        return series;
    }
}
