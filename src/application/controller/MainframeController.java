package application.controller;

// java imports

import application.Main;
import application.model.circuitry.Bulb;
import application.model.circuitry.Cell;
import application.model.fxcircuitry.*;
import application.model.quantity.Resistance;
import application.model.quantity.Voltage;
import application.model.util.File;
import application.model.util.fxtools.AutoCompleteComboBoxListener;
import application.model.util.fxtools.DraggableTab;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainframeController implements Initializable {
    public static MainframeController currentOccurrence;
    private final HashMap<String, DraggableTab> files = new HashMap<>();
    @FXML
    public VBox win;
    @FXML
    public MenuBar menubar;
    @FXML
    public Menu file, saveAsMenu, edit, help;
    @FXML
    public MenuItem newMenu, openMenu, saveMenu, exmlOption, xmlOption, imageOption, cutItem, copyItem, pasteItem, duplicateItem, deleteItem, backspaceItem, aboutItem, aboutMeItem, aboutCellItem, aboutBulbItem, aboutResistorItem;
    @FXML
    public TabPane notebook;
    @FXML
    public Label title;
    @FXML
    public ComboBox translator;
    @FXML
    public Hyperlink minimize, maximize, close;
    public Map<String, Locale> locales;
    public ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());

    /**
     * @param event Closes window
     */
    @FXML
    public void closeWin(ActionEvent event) {
        for (Tab tab : notebook.getTabs()) {
            tab.getOnCloseRequest().handle(null);
        }
        Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * @param event Minimizes window
     */
    @FXML
    public void minimizeWin(ActionEvent event) {
        Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void setFullScreen(ActionEvent event) {
        Main.fullScreen();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @FXML
    public void openEXML(ActionEvent event) throws IOException {
        try {
            File file = File.getEXML();

            DraggableTab tab = getByFile(file.getAbsolutePath());

            if (tab != null) {
                notebook.getSelectionModel().select(tab);
                return;
            }

            newEXML(event);

            tab = (DraggableTab) notebook.getSelectionModel().getSelectedItem();
            TabController controller = getTabController();
            controller.setFile(new java.io.File(file.getAbsolutePath()));
            files.put(file.getAbsolutePath(), tab);
            String[] path = file.getAbsolutePath().split("\\\\");
            tab.setLabelText("      " + path[path.length - 1] + "      ");
            controller.canvas.parse(file);
            file.close();

            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(30000);
                        Platform.runLater(() -> {
                            try {
                                File.writeTo(controller.getFile(), controller.canvas.toString());
                                controller.canvas.save();
                            } catch (IOException e) {
                            }
                        });
                    }
                } catch (InterruptedException ex) {
                }
            }).start();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void newEXML(ActionEvent event) throws IOException {
        Parent node = FXMLLoader.load(Main.class.getResource("view/tab.fxml"));

        DraggableTab untitled = new DraggableTab("      *untitled.exml       ");
        untitled.setClosable(true);
        untitled.setDetachable(true);
        untitled.getLabel().setStyle("-fx-background-color: #ffffbf;");
        untitled.setStyle("-fx-background-color: #ffffbf;");
        untitled.getLabel().getStyleClass().add("tablabel");
        untitled.setContent(node);

        untitled.setOnCloseRequest(e -> {
            try {
                TabController controller = TabController.getController(untitled.getContent());
                if (controller.canvas.getSeries().keySet().size() > 0 && !controller.canvas.isSaved()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Closing Dialog");
                    alert.setHeaderText("Did you intend to save it?");
                    alert.setContentText("Would you like to save " + untitled.getLabel().getText().trim() + "?");

                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeYes) {
                        if (controller.getFile() != null) {
                            if (!controller.canvas.isSaved()) save();
                        } else {
                            saveAs();
                        }
                        files.remove(controller.getFile().getAbsolutePath());

                    } else if (result.get() == buttonTypeNo) {
                        files.remove(controller.getFile().getAbsolutePath());
                    } else {
                        if (e != null) e.consume();
                    }
                } else
                    files.remove(controller.getFile().getAbsolutePath());
            } catch (NullPointerException ex) {
            }
        });

        notebook.getTabs().add(untitled);
        notebook.getSelectionModel().select(untitled);
    }

    public String getNameOfTab() {
        DraggableTab tab = (DraggableTab) notebook.getSelectionModel().getSelectedItem();
        String text = tab.getLabel().getText().trim();
        return text.substring(0, text.length() - 5);
    }

    public void generateStage(String fxml, String key) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/application/view/" + fxml));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            ResourceBundle bundle = ResourceBundle.getBundle("langBundle", locale.get());
            stage.setTitle(bundle.getString(key));
            stage.getIcons().add(Main.getIcon());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void findOut(MouseEvent event) {
        generateStage("about/about.fxml", "about");
    }

    @FXML
    public void about(ActionEvent event) {
        generateStage("about/about.fxml", "about");
    }

    @FXML
    public void aboutMe(ActionEvent event) {
        generateStage("about/aboutMe.fxml", "aboutMe");
    }

    @FXML
    public void aboutCell(ActionEvent event) {
        generateStage("about/aboutCell.fxml", "aboutCell");
    }

    @FXML
    public void aboutBulb(ActionEvent event) {
        generateStage("about/aboutBulb.fxml", "aboutBulb");
    }

    @FXML
    public void aboutResistor(ActionEvent event) {
        generateStage("about/aboutResistor.fxml", "aboutResistor");
    }

    public Locale getLocale(String display) {
        return locales.get(display);
    }

    @FXML
    public void setLocale(ActionEvent event) {
        try {
            locale.set(new Locale(getLocale(translator.getSelectionModel().getSelectedItem().toString()).getLanguage()));
            ResourceBundle bundle = ResourceBundle.getBundle("langBundle", locale.get());
            file.setText(bundle.getString("file"));
            edit.setText(bundle.getString("edit"));
            help.setText(bundle.getString("help"));
            newMenu.setText(bundle.getString("new"));
            openMenu.setText(bundle.getString("open"));
            saveMenu.setText(bundle.getString("save"));
            saveAsMenu.setText(bundle.getString("saveas"));
            exmlOption.setText(bundle.getString("exml"));
            xmlOption.setText(bundle.getString("xml"));
            imageOption.setText(bundle.getString("image"));
            cutItem.setText(bundle.getString("cut"));
            copyItem.setText(bundle.getString("copy"));
            pasteItem.setText(bundle.getString("paste"));
            duplicateItem.setText(bundle.getString("duplicate"));
            deleteItem.setText(bundle.getString("delete"));
            backspaceItem.setText(bundle.getString("backspace"));
            aboutItem.setText(bundle.getString("about"));
            aboutMeItem.setText(bundle.getString("aboutMe"));
            aboutCellItem.setText(bundle.getString("aboutCell"));
            aboutBulbItem.setText(bundle.getString("aboutBulb"));
            aboutResistorItem.setText(bundle.getString("aboutResistor"));
            title.setText(bundle.getString("title"));

            for (TabController controller : getTabControllers()) {
                // property, value
                controller.cellLabel.setText(bundle.getString("cell") + " (〢)");
                controller.powerSources.setText(bundle.getString("powersources"));
                controller.components.setText(bundle.getString("components"));
                controller.emfLabel.setText(bundle.getString("emf"));
                controller.IeffLabel.setText(bundle.getString("ieff"));
                controller.resistorLabel.setText(bundle.getString("resistor") + " (⏛)");
                controller.bulbLabel.setText(bundle.getString("bulb") + " (" + Bulb.getRepr() + ")");
                controller.ReffLabel.setText(bundle.getString("reff"));
                controller.PLabel.setText(bundle.getString("power"));
                controller.widgets.setText(bundle.getString("widgets"));
                controller.detailsLabel.setText(bundle.getString("details"));
                controller.rLabel.setText(bundle.getString("resistance"));
                controller.property.setText(bundle.getString("property"));
                controller.value.setText(bundle.getString("value"));
            }
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notebook.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                DraggableTab old = (DraggableTab) oldValue;
                old.setStyles("-fx-background-color: #ffffbf;");
            }
            if (newValue != null) {
                DraggableTab newTab = (DraggableTab) newValue;
                newTab.setStyleClasses("focusedTab");
                newTab.setStyles("-fx-background-color: #add8e6;");
            }
        }));


        // setting up internationalization
        locales = getLocales();
        translator.getItems().addAll(locales.keySet());
        translator.setPromptText("Language");
        new AutoCompleteComboBoxListener(translator);
        translator.setValue(locale.get().getDisplayName());

        currentOccurrence = this;

        try {
            newEXML(new ActionEvent());
        } catch (IOException e) {
        }
    }

    public Map<String, Locale> getLocales() {
        HashMap<String, Locale> localeListing = new HashMap<>();
        for (Locale locale : DateFormat.getAvailableLocales()) {
            if (locale.getLanguage().equals("hi") || locale.getLanguage().equals("en") || locale.getLanguage().equals("es") || locale.getLanguage().equals("fr"))
                localeListing.put(locale.getDisplayName(), locale);
        }
        return new TreeMap<>(localeListing);
    }

    public void save() {
        Node node = notebook.getSelectionModel().getSelectedItem().getContent();
        TabController controller = TabController.getController(node);
        assert controller != null;
        if (controller.getFile() == null) {
            saveAs();
        } else {
            try {
                File.writeTo(controller.getFile(), controller.canvas.toString());
                controller.canvas.save();
            } catch (IOException e) {
            }
        }
    }

    public void saveAs() {
        try {
            DraggableTab tab = (DraggableTab) notebook.getSelectionModel().getSelectedItem();
            TabController controller = TabController.getController(tab.getContent());
            java.io.File file = File.saveEXML(Main.getStage()).toGeneric();
            File.writeTo(file, controller.canvas.toString());
            controller.setFile(file);
            files.put(file.getAbsolutePath(), tab);
            String[] path = file.getAbsolutePath().split("\\\\");
            tab.setLabelText("      " + path[path.length - 1] + "      ");
            controller.canvas.save();

            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(30000);
                        Platform.runLater(() -> {
                            try {
                                File.writeTo(controller.getFile(), controller.canvas.toString());
                                controller.canvas.save();
                            } catch (IOException e) {
                            }
                        });
                    }
                } catch (InterruptedException ex) {
                }
            }).start();
        } catch (NullPointerException | IOException ex) {
        }
    }

    public void saveAsXML() {
        try {
            DraggableTab tab = (DraggableTab) notebook.getSelectionModel().getSelectedItem();
            TabController controller = TabController.getController(tab.getContent());
            java.io.File file = File.saveXML(Main.getStage()).toGeneric();
            File.writeTo(file, controller.canvas.toString());
        } catch (NullPointerException | IOException ex) {
        }
    }

    public void saveViewAsImage() {
        try {
            DraggableTab tab = (DraggableTab) notebook.getSelectionModel().getSelectedItem();
            TabController controller = TabController.getController(tab.getContent());
            WritableImage img = controller.scroller.snapshot(new SnapshotParameters(), null);
            BufferedImage img2 = SwingFXUtils.fromFXImage(img, null);
            java.io.File file = File.saveImageIO(Main.getStage()).toGeneric();
            ImageIO.write(img2, "png", file);
        } catch (NullPointerException | IOException ex) {
        }
    }

    @FXML
    public void home(ActionEvent e) {
        Node node = notebook.getSelectionModel().getSelectedItem().getContent();
        ScrollPane scroller = TabController.getController(node).scroller;
        scroller.setHvalue(0.5);
        scroller.setVvalue(0.5);
    }

    @FXML
    public void delete(ActionEvent e) {
        Node node = notebook.getSelectionModel().getSelectedItem().getContent();
        TabController controller = TabController.getController(node);
        CircuitPane canvas = controller.canvas;
        controller.emf.clear();
        controller.Ieff.clear();
        controller.power.clear();
        controller.resistance.clear();
        controller.reff.clear();
        controller.details.setDisable(true);
        canvas.remove(canvas.draggingNode);
        try {
            canvas.getChildren().remove(canvas.draggingNode.getC1());
        } catch (NullPointerException ex) {
        }
        try {
            canvas.getChildren().remove(canvas.draggingNode.getC2());
        } catch (NullPointerException ex) {
        }

        if (canvas.draggingNode.getC1() instanceof ParallelCircle) {
            ((ParallelCircle) canvas.draggingNode.getC1()).disconnectCompletely();
        }

        try {
            canvas.getChildren().remove(canvas.draggingNode.getC1().getConnectedTo());
            canvas.draggingNode.getComponent().getLeft().getConnected().disconnect();
            canvas.draggingNode.getComponent().getLeft().disconnect();
        } catch (NullPointerException ex) {
        }
        try {
            canvas.getChildren().remove(canvas.draggingNode.getC2().getConnectedTo());
            canvas.draggingNode.getComponent().getRight().getConnected().disconnect();
            canvas.draggingNode.getComponent().getRight().disconnect();
        } catch (NullPointerException ex) {
        }
    }

    @FXML
    public void copy(ActionEvent e) {
        try {
            Node node = notebook.getSelectionModel().getSelectedItem().getContent();
            CircuitPane canvas = TabController.getController(node).canvas;
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(canvas.draggingNode.getComponent().toString());
            clipboard.setContent(content);
        } catch (NullPointerException ex) {
        }
    }

    @FXML
    public void cut(ActionEvent e) {
        copy(e);
        delete(e);
    }

    @FXML
    public void paste(ActionEvent e) {
        try {
            Node node = notebook.getSelectionModel().getSelectedItem().getContent();
            TabController controller = TabController.getController(node);
            assert controller != null;
            CircuitPane canvas = controller.canvas;
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            String copied = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
            List<String> text = Arrays.asList(copied.replaceAll("^(<|>|\\s)+|/(<|>|\\s)+$", "").trim().split("\\s"));
            String type = text.get(0), x = text.get(text.size() - 4), y = text.get(text.size() - 3);
            String rotate = text.get(text.size() - 2);
            text = text.subList(1, text.size() - 4);

            Pattern p = Pattern.compile("\\s*(emf|I|R|P)\\s*=\"\\s*([0-9]+(\\.[0-9]+)?)\\s*[a-zA-Z]+\\s*\"\\s*");
            Pattern xyrotate = Pattern.compile("\\s*(x|y|angle)\\s*=\\s*\"\\s*([0-9]+(\\.[0-9]+)?)\\s*\"\\s*");
            Matcher m;
            double emf = 1.5, I = 0, R = 1, P = 0, xCoord = 0, yCoord = 0, value, rotation = 0;

            for (String regex : text) {
                m = p.matcher(regex);
                if (!m.find()) continue;
                try {
                    value = Double.parseDouble(m.group(2));
                    if (m.group(1).equals("emf")) emf = value;
                    else if (m.group(1).equals("R")) R = value;
                } catch (NumberFormatException ignored) {
                }
            }


            try {
                m = xyrotate.matcher(x.trim());
                if (m.find()) {
                    value = Double.parseDouble(m.group(2));
                    xCoord = value;
                }
            } catch (NumberFormatException ignored) {
            }

            try {
                m = xyrotate.matcher(y.trim());
                if (m.find()) {
                    value = Double.parseDouble(m.group(2));
                    yCoord = value;
                }
            } catch (NumberFormatException ignored) {
            }

            try {
                m = xyrotate.matcher(rotate.trim());
                if (m.find()) {
                    value = Double.parseDouble(m.group(2));
                    rotation = value;
                }
            } catch (NumberFormatException ignored) {
            }

            xCoord += 20;
            yCoord += 20;

            ComponentPane widget;
            if (type.trim().equals("Resistor")) {
                widget = ResistorWidget.create(canvas);
                widget.getComponent().setResistance(new Resistance(R));

            } else if (type.trim().equals("Bulb")) {
                widget = BulbWidget.create(canvas);
                widget.getComponent().setResistance(new Resistance(R));
            } else if (type.trim().equals("Cell")) {
                widget = CellWidget.create(canvas);
                ((Cell) widget.getComponent()).setEMF(new Voltage(emf));
            } else return;
            widget.setLayoutX(xCoord - widget.getPrefWidth() / 2 + 75000);
            widget.setLayoutY(yCoord - widget.getPrefHeight() / 2 + 75000);
            widget.getWidget().setRotate(rotation);
            controller.setMainNode(widget);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
        }
    }

    @FXML
    public void duplicate(ActionEvent e) {
        copy(e);
        paste(e);
    }

    public TabController getTabController() {
        Node node = notebook.getSelectionModel().getSelectedItem().getContent();
        return TabController.getController(node);
    }

    public ArrayList<TabController> getTabControllers() {
        ArrayList<TabController> controllers = new ArrayList<>();
        for (Tab tab : notebook.getTabs()) {
            controllers.add(TabController.getController(tab.getContent()));
        }
        return controllers;
    }

    public DraggableTab getByFile(String filename) {
        ObjectProperty<DraggableTab> selTab = new SimpleObjectProperty<>(null);
        files.forEach((file, tab) -> {
            if (file.equals(filename)) selTab.set(tab);
        });
        return selTab.get();
    }
}
