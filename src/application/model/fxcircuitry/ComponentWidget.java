package application.model.fxcircuitry;

import application.model.circuitry.Component;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class ComponentWidget extends DraggableNode {

    abstract String IMAGE_PATH();
    abstract double width();
    abstract double height();

    public static Circle createCircle(double x, double y) {
        return new Circle(x, y, 5, Color.BLACK);
    }

    public Node createWidget() {
        ImageView iv;
        try {
            Image image = new Image(new FileInputStream(IMAGE_PATH()));
            iv = new ImageView(image);
            iv.resize(width(), height());
        } catch (IOException ex) {
            System.out.println("Image didn't work.");
            iv = new ImageView();
        }
        Pane root = new Pane(iv);
        return root;
    }

    public Node createCircles(Node win) {
        Pane root = (Pane) win;
        Node[] circles = new Node[]{
                createCircle(win.getScaleX(), win.getScaleY()+height()/2),
                createCircle(win.getScaleX()+width(),  win.getScaleY()+height()/2)
        };
        root.getChildren().addAll(circles);



        class DragStartHandler implements EventHandler<MouseEvent> {

            public Line line;

            @Override
            public void handle(MouseEvent event) {
                if (line == null) {
                    Node sourceNode = (Node) event.getSource();
                    line = new Line();
                    Bounds bounds = sourceNode.getBoundsInParent();

                    // start line at center of node
                    line.setStartX((bounds.getMinX() + bounds.getMaxX()) / 2);
                    line.setStartY((bounds.getMinY() + bounds.getMaxY()) / 2);
                    line.setEndX(line.getStartX());
                    line.setEndY(line.getStartY());
                    sourceNode.startFullDrag();
                    root.getChildren().add(0, line);
                }
            }
        }

        DragStartHandler startHandler = new DragStartHandler();
        EventHandler<MouseDragEvent> dragReleaseHandler = evt -> {
            if (evt.getGestureSource() == evt.getSource()) {
                // remove line, if it starts and ends in the same node
                root.getChildren().remove(startHandler.line);
            }
            evt.consume();
            startHandler.line = null;
        };
        EventHandler<MouseEvent> dragEnteredHandler = evt -> {
            if (startHandler.line != null) {
                // snap line end to node center
                Node node = (Node) evt.getSource();
                Bounds bounds = node.getBoundsInParent();
                startHandler.line.setEndX((bounds.getMinX() + bounds.getMaxX()) / 2);
                startHandler.line.setEndY((bounds.getMinY() + bounds.getMaxY()) / 2);
            }
        };

        for (Node n : circles) {
            // register handlers
            n.setOnDragDetected(startHandler);
            n.setOnMouseDragReleased(dragReleaseHandler);
            n.setOnMouseDragEntered(dragEnteredHandler);

            // add info allowing to identify this node as drag source/target
            n.setUserData(Boolean.TRUE);
        }

        root.setOnMouseReleased(evt -> {
            // mouse released outside of a target -> remove line
            root.getChildren().remove(startHandler.line);
            startHandler.line = null;
        });
        root.setOnMouseDragged(evt -> {
            if (startHandler.line != null) {
                Node pickResult = evt.getPickResult().getIntersectedNode();
                if (pickResult == null || pickResult.getUserData() != Boolean.TRUE) {
                    // mouse outside of target -> set line end to mouse position
                    startHandler.line.setEndX(evt.getX());
                    startHandler.line.setEndY(evt.getY());
                }
            }
        });

        return root;
    }
}

