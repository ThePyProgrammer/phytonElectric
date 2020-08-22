package application.model.fxcircuitry;

import application.model.quantity.*;
import application.model.circuitry.*;

public class CellWidget extends ComponentWidget {
    private static final Voltage emf = Cell.genericEMF();
    private static final Current I = Cell.genericI();
    private Cell cell;


    String IMAGE_PATH() { return "src/resources/images/widgets/cell.png"; }
    double height() { return 30; }
    double width() { return 20; }

    public CellWidget() {
        cell = new Cell(emf, I);
    }
}
