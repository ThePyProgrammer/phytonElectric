package application.model.fxcircuitry;

import application.model.quantity.*;
import application.model.circuitry.*;

public class BulbWidget extends ComponentWidget {
    private static final Voltage V = Bulb.genericV();
    private static final Current I = Bulb.genericI();
    private Bulb bulb;


    String IMAGE_PATH() { return "src/resources/images/widgets/cell.png"; }
    double height() { return 30; }
    double width() { return 20; }


    public BulbWidget() {
        bulb = new Bulb(V, I);
        // setStyle(" -fx-border-style: dotted; -fx-border-color: black; -fx-border-radius: 4px;");
    }
}
