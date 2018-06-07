package visualization;

import algorithm.AlgorithmCell;
import algorithm.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.FieldGenerator2D;
import model.FieldGenerator3D;

public class Field2DSceneHolder implements SceneHolder {
    private int lengthOfSide;
    private int minWidth;
    private int maxWidth;

    private Boolean is2DMode;

    private FieldGenerator2D fieldGenerator2D;
    private FieldGenerator3D fieldGenerator3D;

    private static final Field2DSceneHolder instance = new Field2DSceneHolder();
    private RobotApplication parent;

    private static final Color ALARM_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.web("E3DCC0");
    private static final Color BUTTON_FILL_COLOR = Color.web("#9BAF8E");
    private static final Color INFO_COLOR = Color.DARKGREEN;
    private static final Color MENU_ITEM_COLOR = Color.WHITESMOKE;

    private static final Font LABEL_FONT = Font.font("Calibri", FontWeight.BOLD, 16);
    private static final Font BUTTON_FONT = Font.font("Calibri", FontWeight.BOLD, 16);
    private static final Font MODE_FONT = Font.font("Courier", FontWeight.BOLD, 24);
    private static final Font INFO_FONT = Font.font("System", FontWeight.MEDIUM,12);

    private Field2DSceneHolder() {

    }

    public static Field2DSceneHolder getInstance() {
        return instance;
    }


    @Override
    public Scene getScene() {
        HBox root = new HBox();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        VBox menuBox = new VBox();
        menuBox.setPrefWidth(270);
        menuBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        menuBox.setPadding(new Insets(5, 5, 5, 5));
        menuBox.setSpacing(5);

        Pane pane = new Pane();
        VisualField2D.getInstance().setPane(pane);
        pane.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
//        pane.setPrefWidth(700);

        ScrollPane scrollPane = new ScrollPane();
        VisualField2D.getInstance().setRoot(scrollPane);
        scrollPane.setPrefWidth(700);

        VBox wallsBox = new VBox();
        wallsBox.setPrefWidth(240);
        wallsBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        wallsBox.setPadding(new Insets(5, 5, 5, 5));
        wallsBox.setSpacing(5);

        HBox wallsTitleContainer = new HBox();
        wallsTitleContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        wallsTitleContainer.setPrefWidth(230);
        wallsTitleContainer.setAlignment(Pos.CENTER);

        Label wallsTitle = new Label("Walls\n");
        wallsTitle.setWrapText(true);
        wallsTitle.setPrefHeight(30);
        wallsTitle.setTextAlignment(TextAlignment.CENTER);
        wallsTitle.setFont(LABEL_FONT);

        TextArea listWalls = new TextArea();
        listWalls.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        listWalls.setFont(LABEL_FONT);
        listWalls.setPrefWidth(230);
        listWalls.setPrefHeight(600);

        HBox numberRoomSideContainer = new HBox();
        numberRoomSideContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        numberRoomSideContainer.setSpacing(5);
        numberRoomSideContainer.setPrefHeight(20);
        numberRoomSideContainer.setAlignment(Pos.CENTER_LEFT);



        HBox maxWidthWallContainer = new HBox();
        maxWidthWallContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        maxWidthWallContainer.setSpacing(5);
        maxWidthWallContainer.setPrefHeight(20);
        maxWidthWallContainer.setAlignment(Pos.CENTER_LEFT);

        HBox minWidthWallContainer = new HBox();
        minWidthWallContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        minWidthWallContainer.setSpacing(5);
        minWidthWallContainer.setPrefHeight(20);
        minWidthWallContainer.setAlignment(Pos.CENTER_LEFT);


        Label numberRoomSideLbl = new Label("Number of rooms in side :");
        numberRoomSideLbl.setPrefWidth(175);
        numberRoomSideLbl.setFont(LABEL_FONT);

        TextField numberRoomSideTxt = new TextField();
        numberRoomSideTxt.setPrefWidth(60);


        Label maxWallWidthLbl = new Label("Maximum wall width :");
        maxWallWidthLbl.setPrefWidth(175);
        maxWallWidthLbl.setFont(LABEL_FONT);

        TextField maxWallWidthTxt = new TextField();
        maxWallWidthTxt.setPrefWidth(60);


        Label minWallWidthLbl = new Label("Minimum wall width :");
        minWallWidthLbl.setPrefWidth(175);
        minWallWidthLbl.setFont(LABEL_FONT);

        TextField minWallWidthTxt = new TextField();
        minWallWidthTxt.setPrefWidth(60);

        Label initialErrorLbl = new Label();
        initialErrorLbl.setFont(INFO_FONT);
        initialErrorLbl.setPrefWidth(260);
        setFixedHeight(initialErrorLbl, 0);
        initialErrorLbl.setTextAlignment(TextAlignment.CENTER);
        initialErrorLbl.setTextFill(ALARM_COLOR);

        Label submittedInput = new Label("");
        submittedInput.setFont(INFO_FONT);
        submittedInput.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submittedInput.setTextAlignment(TextAlignment.CENTER);
        submittedInput.setPrefWidth(260);
        submittedInput.setPrefHeight(50);

        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String numberRoomSide = numberRoomSideTxt.getText();
                String maxWallWidth = maxWallWidthTxt.getText();
                String minWallWidth = minWallWidthTxt.getText();

                if (validateGeneratorParameters(numberRoomSide, maxWallWidth, minWallWidth,
                        initialErrorLbl)) {
                    lengthOfSide = Integer.parseInt(numberRoomSide);
                    maxWidth = Integer.parseInt(maxWallWidth);
                    minWidth = Integer.parseInt(minWallWidth);
                    submittedInput.setText("                        SUBMITTED INPUT                        \n" +
                            "Length of side: " + numberRoomSide + ".\n" +
                            "Width of walls: from " + minWallWidth + " to " + maxWallWidth + ".");
                }
            }
        });
        submitBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submitBtn.setPrefWidth(260);
        submitBtn.setPrefHeight(30);
        submitBtn.setFont(BUTTON_FONT);

        Label modeLbl = new Label();
        modeLbl.setTextAlignment(TextAlignment.CENTER);
        modeLbl.setPrefHeight(50);
        modeLbl.setPrefWidth(260);
        modeLbl.setFont(MODE_FONT);
        modeLbl.setTextFill(INFO_COLOR);

        Button mode2DButton = new Button("2D");
        mode2DButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                set2DMode(modeLbl);
            }
        });
        mode2DButton.setPrefWidth(127);
        mode2DButton.setPrefHeight(30);
        mode2DButton.setFont(BUTTON_FONT);
        mode2DButton.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));


        Button mode3DButton = new Button("3D");
        mode3DButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                set3DMode(modeLbl);
            }
        });
        mode3DButton.setPrefHeight(30);
        mode3DButton.setPrefWidth(127);
        mode3DButton.setFont(BUTTON_FONT);
        mode3DButton.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox modeButtonsContainer = new HBox();
        modeButtonsContainer.setPrefHeight(30);
        modeButtonsContainer.setPrefWidth(260);
        modeButtonsContainer.setSpacing(6);
        modeButtonsContainer.getChildren().add(mode2DButton);
        modeButtonsContainer.getChildren().add(mode3DButton);

        Label generateErrorLbl = new Label("");
        generateErrorLbl.setPrefWidth(260);
        setFixedHeight(generateErrorLbl, 0);
        generateErrorLbl.setTextAlignment(TextAlignment.CENTER);
        generateErrorLbl.setTextFill(ALARM_COLOR);

        Label successfulGeneration = new Label("");
        successfulGeneration.setFont(INFO_FONT);
        successfulGeneration.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        successfulGeneration.setTextAlignment(TextAlignment.CENTER);
        successfulGeneration.setPrefWidth(260);
        successfulGeneration.setPrefHeight(40);

        Button generateBtn = new Button("Generate field");
        generateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateModeAndInput(submittedInput.getText(), is2DMode, generateErrorLbl)) {
                    generateField(successfulGeneration,listWalls);
                }

            }
        });
        generateBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        generateBtn.setPrefWidth(260);
        generateBtn.setPrefHeight(30);
        generateBtn.setFont(BUTTON_FONT);



        menuBox.getChildren().add(modeLbl);
        menuBox.getChildren().add(modeButtonsContainer);
        menuBox.getChildren().add(numberRoomSideContainer);
        menuBox.getChildren().add(minWidthWallContainer);
        menuBox.getChildren().add(maxWidthWallContainer);
        menuBox.getChildren().add(submitBtn);
        menuBox.getChildren().add(initialErrorLbl);
        menuBox.getChildren().add(submittedInput);
        menuBox.getChildren().add(generateBtn);
        menuBox.getChildren().add(successfulGeneration);
        menuBox.getChildren().add(generateErrorLbl);

        numberRoomSideContainer.getChildren().add(numberRoomSideLbl);
        numberRoomSideContainer.getChildren().add(numberRoomSideTxt);
        maxWidthWallContainer.getChildren().add(maxWallWidthLbl);
        maxWidthWallContainer.getChildren().add(maxWallWidthTxt);
        minWidthWallContainer.getChildren().add(minWallWidthLbl);
        minWidthWallContainer.getChildren().add(minWallWidthTxt);

        wallsTitleContainer.getChildren().add(wallsTitle);

        wallsBox.getChildren().add(wallsTitleContainer);
        wallsBox.getChildren().add(listWalls);

        scrollPane.setContent(pane);

        root.getChildren().add(menuBox);
        root.getChildren().add(scrollPane);
        root.getChildren().add(wallsBox);



        Scene scene = new Scene(root, 1250, 650, Color.DARKGREY);



        return scene;
    }

    private void set2DMode(Label modeLabel) {
        is2DMode = true;
        modeLabel.setText("           Mode: 2D          ");
        fieldGenerator2D = null;
    }

    private void set3DMode(Label modeLabel) {
        is2DMode = false;
        modeLabel.setText("           Mode: 3D          ");
        fieldGenerator3D = null;
    }

    private void generateField(Label successfulGeneration, TextArea listWalls){
        if (is2DMode) {
            fieldGenerator2D = new FieldGenerator2D(lengthOfSide, minWidth, maxWidth);
            fieldGenerator2D.generateBuilding();
            VisualField2D.getInstance().setFieldGenerator(fieldGenerator2D);
            VisualField2D.getInstance().buildDrawCells();
            listWalls.setText("");
            for (String wall : fieldGenerator2D.getWalls()) {
                listWalls.appendText(wall + "\n");
            }

        } else {
            fieldGenerator3D = new FieldGenerator3D(lengthOfSide, minWidth, maxWidth);
            fieldGenerator3D.generateBuilding();
            listWalls.setText("");
            for (String wall : fieldGenerator3D.getWalls()) {
                listWalls.appendText(wall + "\n");
            }
        }
        successfulGeneration.setText("       Generation of field finished successfully.       ");
    }
    private boolean validateGeneratorParameters(String numberRoomSide, String maxWallWidth, String minWallWidth,
                                                Label errLabel) {
        try {
            if ((1 <= Integer.parseInt(numberRoomSide) && (Integer.parseInt(numberRoomSide) <= 100) &&
                    (1 <= Integer.parseInt(maxWallWidth) && (Integer.parseInt(maxWallWidth) <= 1000) &&
                            (1 <= Integer.parseInt(minWallWidth) && (Integer.parseInt(minWallWidth) <= 1000))))) {
                if (Integer.parseInt(minWallWidth) <= Integer.parseInt(maxWallWidth)) {
                    setFixedHeight(errLabel, 0);
                    errLabel.setText("");
                    return true;
                } else {
                    errLabel.setText("          Maximum width must be bigger\n            then minimum width.          ");
                    setFixedHeight(errLabel, 50);
                    return false;
                }
            } else {
                errLabel.setText("Length of side must be in range from 1 to 100,\n" +
                        "bounds of width must be \nin range from 1 to 1000.");
                setFixedHeight(errLabel, 50);
                return false;
            }
        } catch (Exception e) {
            errLabel.setText("Fields must contain only numbers");
            setFixedHeight(errLabel, 50);
            return false;
        }
    }

    private boolean validateModeAndInput(String submittedInput, Boolean is2DMode, Label errLabel) {
        if (is2DMode != null && !submittedInput.equals("")) {
            errLabel.setText("");
            setFixedHeight(errLabel, 0);
            return true;
        } else {
            errLabel.setText("You must type correct values in text fields above," +
                    "\nsubmit them and set mode(press \"2D\" or \"3D\").");
            setFixedHeight(errLabel, 50);
            return false;
        }
    }

    private void setFixedHeight(Region region, int height) {
        region.setMaxHeight(height);
        region.setMaxHeight(height);
        region.setMinHeight(height);
    }

    public void setParent(RobotApplication parent) {
        this.parent = parent;
    }
}
