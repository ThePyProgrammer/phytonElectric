package application.model.fxcircuitry;

import application.Main;
import application.model.circuitry.Component;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public abstract class ComponentWidget extends Pane {
    protected ImageView iv;
    Component component;

    public ComponentWidget(Component component) {
        this.component = component;
        getChildren().add(createWidget());
        getStyleClass().add("widget");
        component.getRotation().bindBidirectional(rotateProperty());

    }

    abstract String IMAGE_PATH();

    public DisplayPane createWidget() {
        Image image = new Image(Main.class.getResource(IMAGE_PATH()).toExternalForm());
        iv = new ImageView(image);
        DisplayPane root = new DisplayPane();
        root.setGraphic(iv);
        return root;
    }


    public Component getComponent() {
        return component;
    }

    public static class DisplayPane extends Pane {
        private ImageView graphic;
        private Text text;

        public ImageView getGraphic() {
            return graphic;
        }

        public void setGraphic(ImageView graphic) {
            if (this.graphic != null) getChildren().remove(this.graphic);
            this.graphic = graphic;
            getChildren().add(this.graphic);
        }

        public Text getText() {
            return text;
        }

        public void setText(Text text) {
            if (this.text != null) getChildren().remove(this.text);
            this.text = text;
            getChildren().add(this.text);
        }
    }
}

