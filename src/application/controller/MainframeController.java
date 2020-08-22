package application.controller;

// java imports
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

// javafx imports
import application.Main;
import com.gtranslate.Language;
import com.gtranslate.Translator;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

// local imports
import application.model.fxcircuitry.CellWidget;
import application.model.util.fxtools.AutoCompleteComboBoxListener;

public class MainframeController implements Initializable {
    @FXML public VBox win;

    @FXML public AnchorPane menu;
    @FXML public MenuBar menubar;

    @FXML public SplitPane split;
    @FXML public ScrollPane scroller;
    @FXML public Pane canvas;

    @FXML public Label title, component, cellLabel, resistorLabel, earthLabel, cursorCoords, emfLabel, ReffLabel,
            IeffLabel, PLabel;
    @FXML public TextField emf, reff, Ieff, power;
    @FXML public ComboBox translator;
    @FXML public Hyperlink minimize, close;

    @FXML public Font x1, x3;
    @FXML public Color x2, x4;

    public Node draggingNode;

    public Map<String, Locale> locales;
    public Locale locale;
    public ObservableList<Label> nodes = FXCollections.observableArrayList();

    private Translator translate = Translator.getInstance();

    @FXML
    public void closeWin(ActionEvent event) {
        Stage stage = (Stage)((Hyperlink)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizeWin(ActionEvent event) {
        Stage stage = (Stage)((Hyperlink)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @FXML
    public void open(ActionEvent event) {
        
    }

    public void generateStage(String fxml, String title) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/"+fxml));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle(title);
            stage.getIcons().add(new Image(("file:src/resources/images/icons/phyton.png")));
            // stage.initModality(Modality.WINDOW_MODAL);
            // stage.initOwner(menu.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void about(ActionEvent event) {
        generateStage("about/about.fxml", "About phyton");
    }

    @FXML
    public void aboutMe(ActionEvent event) {
        generateStage("about/aboutMe.fxml", "About the Programmer");
    }

    @FXML
    public void cellDragDetected(MouseEvent event) {
        draggingNode = new CellWidget();
        System.out.println("Drag detected");
    }

    @FXML
    public void dragCellOver(MouseDragEvent event) {
        System.out.println("entered drag");

        System.out.println("Node made");
    }

    @FXML
    public void dropCell(MouseEvent event) {
        canvas.getChildren().add(draggingNode);
        draggingNode.relocate(event.getX(), event.getY());
    }

    @FXML
    public void cursorEnter(MouseEvent e) {
        cursorCoords.setVisible(true);
    }

    @FXML
    public void cursorMove(MouseEvent event) {
        cursorCoords.setText(String.format("Cursor at (%.2f, %.2f)", event.getX()-4000, 4000-event.getY()));
    }

    @FXML
    public void cursorExit(MouseEvent event) {
        cursorCoords.setVisible(false);
    }

    public Locale getLocale(String display) {
        return locales.get(display);
    }

    @FXML
    public void setLocale(ActionEvent event) {
        try{
            // Locale prevLocale = locale;
            locale = getLocale(translator.getSelectionModel().getSelectedItem().toString());

            /*for(Label node: nodes) {
                node.setText(translate.translate(node.getText(), prevLocale.toString(), locale.toString()));
            } */

        } catch (NullPointerException ex) {}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting to point (0.0, 0.0)
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);

        // getting nodes
        nodes.addAll(title, component, cellLabel, resistorLabel, earthLabel, cursorCoords, emfLabel, ReffLabel,
                IeffLabel, PLabel);
        // System.out.println(resources);

        // setting up internationalization
        locales = getLocales();
        translator.getItems().addAll(locales.keySet());
        translator.setPromptText("Language");
        new AutoCompleteComboBoxListener(translator);
        translator.setValue((locale = Locale.getDefault()).getDisplayName());
    }

    public Map<String, Locale> getLocales() {
        HashMap<String, Locale> localeListing = new HashMap<>();
        for(Locale locale: DateFormat.getAvailableLocales()) localeListing.put(locale.getDisplayName(), locale);
        return new TreeMap<>(localeListing);
    }
}
