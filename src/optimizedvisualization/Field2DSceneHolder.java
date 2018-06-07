package optimizedvisualization;

import algorithm.Algorithm2D;
import algorithm.Algorithm3D;
import algorithm.HexagonAlgorithmField;
import algorithm.SphereAlgorithmField;
import algorithmMinPath.AntAlgoritm;
import algorithmMinPath.Dijkstra;
import algorithmMinPath.PreparationForDijkstra2D;
import algorithmMinPath.PreparationForDijkstra3D;
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
import javafx.scene.text.TextAlignment;
import model.FieldGenerator2D;
import model.FieldGenerator3D;
import optimizedvisualization.util.ImageFillingRegion;

import java.util.*;

public class Field2DSceneHolder implements SceneHolder {
    private static final Field2DSceneHolder instance = new Field2DSceneHolder();
    private RobotApplication parent;

    private int fieldCounter = 1;

    private int lengthOfSide;
    private int minWidth;
    private int maxWidth;

    private Boolean is2DMode;

    private FieldGenerator2D fieldGenerator2D;
    private FieldGenerator3D fieldGenerator3D;


    private static final Color ALARM_COLOR = Color.RED;
    private static final Color BACKGROUND_COLOR = Color.web("E3DCC0");
    private static final Color BUTTON_FILL_COLOR = Color.web("#9BAF8E");
    private static final Color INFO_COLOR = Color.DARKGREEN;
    private static final Color MENU_ITEM_COLOR = Color.WHITESMOKE;
    private static final Color GENERAL_BACKGROUND_COLOR = Color.BLACK;

    private static final Font LABEL_FONT = Font.font("Calibri", FontWeight.BOLD, 16);
    private static final Font BUTTON_FONT = Font.font("Calibri", FontWeight.BOLD, 16);
    private static final Font MODE_FONT = Font.font("Courier", FontWeight.BOLD, 20);
    private static final Font INFO_FONT = Font.font("System", FontWeight.MEDIUM, 12);

    private HBox root = new HBox();

    //    private HBox wallsPaneContainer = new HBox();
    private HBox menuPaneContainer = new HBox();

    private VBox menuBox = new VBox();
    private Pane pane = new Pane();
    //    private ScrollPane scrollPane = new ScrollPane();
    private VBox wallsBox = new VBox();

    private HBox wallsTitleContainer = new HBox();
    private Label wallsTitle = new Label("Walls\n");
    private TextArea listWalls = new TextArea();

    private HBox navigatorTitleContainer = new HBox();
    private Label navigatorTitle = new Label("Navigator\n");
    private HBox mainNavigationContainer = new HBox();
    private Pane subNavigationContainer = new Pane();
    private Button topCellBtn = new Button();
    private Button topRightCellBtn = new Button();
    private Button bottomRightCellBtn = new Button();
    private Button bottomCellBtn = new Button();
    private Button bottomLeftCellBtn = new Button();
    private Button topLeftCellBtn = new Button();

    private HBox numberRoomSideContainer = new HBox();
    private HBox minWidthWallContainer = new HBox();
    private HBox maxWidthWallContainer = new HBox();
    private Label numberRoomSideLbl = new Label("Number of rooms in side :");
    private Label minWallWidthLbl = new Label("Minimum wall width :");
    private Label maxWallWidthLbl = new Label("Maximum wall width :");
    private TextField numberRoomSideTxt = new TextField();
    private TextField minWallWidthTxt = new TextField();
    private TextField maxWallWidthTxt = new TextField();

    private Label initialErrorLbl = new Label();
    private HBox submittedGeneratorsParametersInputContainer = new HBox();
    private Label submittedGeneratorsParametersInput = new Label("");
    private Button submitGeneratorsParameterBtn = new Button("Submit");

    private HBox modeButtonsContainer = new HBox();
    private HBox modeLabelContainer = new HBox();
    private Label modeLbl = new Label("");
    private Button mode2DButton = new Button("2D");
    private Button mode3DButton = new Button("3D");

    private Label generateErrorLbl = new Label("");
    private Label successfulGeneration = new Label("");
    private Button generateBtn = new Button("Generate field");


    private HBox coordinatesAContainer = new HBox();
    private HBox coordinatesBContainer = new HBox();
    private Label coordinatesALbl = new Label("Room A:");
    private Label coordinatesBLbl = new Label("Room B:");
    private TextField coordinateAxTxt = new TextField("");
    private TextField coordinateAyTxt = new TextField("");
    private TextField coordinateAzTxt = new TextField("");
    private TextField coordinateBxTxt = new TextField("");
    private TextField coordinateByTxt = new TextField("");
    private TextField coordinateBzTxt = new TextField("");

    private Label invalidCoordinatesLbl = new Label("");
    private HBox submittedCoordinatesInputContainer = new HBox();
    private Label submittedCoordinatesInput = new Label("");
    private Button submitCoordinatesBtn = new Button("Submit");

    private Label wrongAccessToNavigatorLbl = new Label("");

    private Button findNumberOfWaysBtn = new Button("Number of ways");
    private Button findDijkstraPathBtn = new Button("Dijkstra");
    private Button findAntPathBtn = new Button("Ant");

    private Label illegalAccessAlgorithmButtonsLbl = new Label("");
    private List<Button> buttons3D = new ArrayList<>();
    private Map<Button, int[]> deltas = new LinkedHashMap<>();

    private HBox cellSwitchContainer = new HBox();
    private Label cellSwitchLbl = new Label("Cell:");
    private Button cellSwitchBtn = new Button();
    private Boolean isCellVisible = true;

    private HBox coordinateSwitchContainer = new HBox();
    private Label coordinateSwitchLbl = new Label("Coordinate:");
    private Button coordinateSwitchBtn = new Button();
    private Boolean isCoordinateVisible = true;

    private HBox pathSwitchContainer = new HBox();
    private Label pathSwitchLbl = new Label("Path:");
    private Button pathSwitchBtn = new Button();
    private Boolean isPathVisible = true;

    private HBox numberWaysSwitchContainer = new HBox();
    private Label numberWaysSwitchLbl = new Label("Number of ways:");
    private Button numberWaysSwitchBtn = new Button();
    private Boolean isNumberWaysVisible = true;

    private List<Label> switchLabels = new ArrayList<>();
    private List<HBox> switchContainers = new ArrayList<>();
    private List<Button> switchButtons = new ArrayList<>();

    private Label currentCellLbl = new Label();

    {
        Button button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{-1, 0, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, -1, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{-1, -1, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{1, 0, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, 1, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{1, 1, 0});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, 0, 1});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{-1, -1, 1});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, -1, 1});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{1, 1, -1});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, 1, -1});

        button = new Button();
        buttons3D.add(button);
        deltas.put(button, new int[]{0, 0, -1});


        switchButtons.add(cellSwitchBtn);
        switchButtons.add(coordinateSwitchBtn);
        switchButtons.add(pathSwitchBtn);
        switchButtons.add(numberWaysSwitchBtn);

        switchContainers.add(cellSwitchContainer);
        switchContainers.add(coordinatesBContainer);
        switchContainers.add(pathSwitchContainer);
        switchContainers.add(numberWaysSwitchContainer);

        switchLabels.add(cellSwitchLbl);
        switchLabels.add(coordinateSwitchLbl);
        switchLabels.add(pathSwitchLbl);
        switchLabels.add(numberWaysSwitchLbl);

    }

//    private Button


    private Field2DSceneHolder() {

    }

    public static Field2DSceneHolder getInstance() {
        return instance;
    }


    @Override
    public Scene getScene() {
//        HBox root = new HBox();
        root.setBackground(new Background(new BackgroundFill(GENERAL_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setSpacing(10);
        root.setPadding(new Insets(10, 10, 10, 10));

//        wallsPaneContainer.setPrefWidth(950);
//        wallsPaneContainer.setBackground(new Background(new BackgroundFill(GENERAL_BACKGROUND_COLOR,CornerRadii.EMPTY, Insets.EMPTY)));
//        wallsPaneContainer.setSpacing(10);

        menuPaneContainer.setBackground(new Background(new BackgroundFill(GENERAL_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        menuPaneContainer.setPrefWidth(990);
        menuPaneContainer.setPrefHeight(680);
        menuPaneContainer.setSpacing(10);
        menuPaneContainer.setAlignment(Pos.TOP_RIGHT);

//        VBox menuBox = new VBox();
        menuBox.setPrefWidth(270);
        menuBox.setPrefHeight(680);
        menuBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        menuBox.setPadding(new Insets(5, 5, 5, 5));
        menuBox.setSpacing(5);

//        Pane pane = new Pane();
        VisualField2D.getInstance().setRoot(pane);
        VisualField3D.getInstance().setRoot(pane);
        pane.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPrefWidth(710);
        pane.setPrefHeight(680);

//        ScrollPane scrollPane = new ScrollPane();
//        VisualField2D.getInstance().setRoot(scrollPane);
//        VisualField2D.getInstance().setRoot(pane);
//        scrollPane.setPrefWidth(700);

//        VBox wallsBox = new VBox();
        wallsBox.setPrefWidth(240);
        wallsBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        wallsBox.setPadding(new Insets(5, 5, 5, 5));
        wallsBox.setSpacing(5);

//        HBox wallsTitleContainer = new HBox();
        wallsTitleContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        wallsTitleContainer.setPrefWidth(230);
        wallsTitleContainer.setAlignment(Pos.CENTER);

//        Label wallsTitle = new Label("Walls\n");
        wallsTitle.setWrapText(true);
        wallsTitle.setPrefHeight(30);
        wallsTitle.setPrefHeight(30);
        wallsTitle.setTextAlignment(TextAlignment.CENTER);
        wallsTitle.setFont(LABEL_FONT);

//        TextArea listWalls = new TextArea();
        listWalls.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        listWalls.setFont(LABEL_FONT);
        listWalls.setPrefWidth(230);
        listWalls.setPrefHeight(300);

        navigatorTitleContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        navigatorTitleContainer.setPrefWidth(230);
        navigatorTitleContainer.setAlignment(Pos.CENTER);

        navigatorTitle.setWrapText(true);
        navigatorTitle.setPrefHeight(30);
        navigatorTitle.setTextAlignment(TextAlignment.CENTER);
        navigatorTitle.setFont(LABEL_FONT);

        mainNavigationContainer.setAlignment(Pos.CENTER);
        mainNavigationContainer.setPrefWidth(230);
        mainNavigationContainer.setPrefHeight(150);

        subNavigationContainer.setPrefWidth(210);
        subNavigationContainer.setPrefHeight(150);

        topCellBtn.setPrefWidth(40);
        topLeftCellBtn.setPrefWidth(40);
        topRightCellBtn.setPrefWidth(40);
        bottomCellBtn.setPrefWidth(40);
        bottomLeftCellBtn.setPrefWidth(40);
        bottomRightCellBtn.setPrefWidth(40);

        topCellBtn.setPrefHeight(40);
        topLeftCellBtn.setPrefHeight(40);
        topRightCellBtn.setPrefHeight(40);
        bottomCellBtn.setPrefHeight(40);
        bottomLeftCellBtn.setPrefHeight(40);
        bottomRightCellBtn.setPrefHeight(40);

        topCellBtn.setTranslateX(50);
        topCellBtn.setTranslateY(0);
        topLeftCellBtn.setTranslateX(0);
        topLeftCellBtn.setTranslateY(20);
        topRightCellBtn.setTranslateX(100);
        topRightCellBtn.setTranslateY(20);

        bottomCellBtn.setTranslateX(50);
        bottomCellBtn.setTranslateY(90);
        bottomLeftCellBtn.setTranslateX(0);
        bottomLeftCellBtn.setTranslateY(70);
        bottomRightCellBtn.setTranslateX(100);
        bottomRightCellBtn.setTranslateY(70);


//        HBox numberRoomSideContainer = new HBox();
        numberRoomSideContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        numberRoomSideContainer.setSpacing(5);
//        numberRoomSideContainer.setPrefHeight(30);
        numberRoomSideContainer.setAlignment(Pos.CENTER_LEFT);


//        HBox maxWidthWallContainer = new HBox();
        maxWidthWallContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        maxWidthWallContainer.setSpacing(5);
//        maxWidthWallContainer.setPrefHeight(30);
        maxWidthWallContainer.setAlignment(Pos.CENTER_LEFT);

//        HBox minWidthWallContainer = new HBox();
        minWidthWallContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        minWidthWallContainer.setSpacing(5);
//        minWidthWallContainer.setPrefHeight(30);
        minWidthWallContainer.setAlignment(Pos.CENTER_LEFT);


//        Label numberRoomSideLbl = new Label("Number of rooms in side :");
        numberRoomSideLbl.setPrefWidth(175);
        numberRoomSideLbl.setFont(LABEL_FONT);

//        TextField numberRoomSideTxt = new TextField();
        numberRoomSideTxt.setPrefWidth(60);


//        Label maxWallWidthLbl = new Label("Maximum wall width :");
        maxWallWidthLbl.setPrefWidth(175);
        maxWallWidthLbl.setFont(LABEL_FONT);

//        TextField maxWallWidthTxt = new TextField();
        maxWallWidthTxt.setPrefWidth(60);


//        Label minWallWidthLbl = new Label("Minimum wall width :");
        minWallWidthLbl.setPrefWidth(175);
        minWallWidthLbl.setFont(LABEL_FONT);

//        TextField minWallWidthTxt = new TextField();
        minWallWidthTxt.setPrefWidth(60);

//        Label initialErrorLbl = new Label();
        initialErrorLbl.setFont(INFO_FONT);
        initialErrorLbl.setPrefWidth(260);
        setFixedHeight(initialErrorLbl, 0);
        initialErrorLbl.setTextFill(ALARM_COLOR);

        invalidCoordinatesLbl.setFont(INFO_FONT);
        invalidCoordinatesLbl.setPrefWidth(260);
        setFixedHeight(invalidCoordinatesLbl, 0);
        invalidCoordinatesLbl.setTextFill(ALARM_COLOR);

        submittedGeneratorsParametersInputContainer.setAlignment(Pos.CENTER);
        submittedGeneratorsParametersInputContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submittedGeneratorsParametersInputContainer.setPrefWidth(260);
        submittedGeneratorsParametersInputContainer.setPrefHeight(50);

//        Label submittedGeneratorsParametersInput = new Label("");
        submittedGeneratorsParametersInput.setFont(INFO_FONT);
        submittedGeneratorsParametersInput.setTextAlignment(TextAlignment.CENTER);
        submittedGeneratorsParametersInput.setPrefHeight(50);

//        Button submitGeneratorsParameterBtn = new Button("Submit");
        submitGeneratorsParameterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fieldGenerator2D = null;
                fieldGenerator3D = null;
                VisualField2D.getInstance().setPath(null);
                VisualField3D.getInstance().setPath(null);
                listWalls.setText("");
                submittedCoordinatesInput.setText("");
                pane.getChildren().clear();
                String numberRoomSide = numberRoomSideTxt.getText();
                String maxWallWidth = maxWallWidthTxt.getText();
                String minWallWidth = minWallWidthTxt.getText();

                if (validateGeneratorParameters(numberRoomSide, maxWallWidth, minWallWidth)) {
                    lengthOfSide = Integer.parseInt(numberRoomSide);
                    maxWidth = Integer.parseInt(maxWallWidth);
                    minWidth = Integer.parseInt(minWallWidth);
                    submittedGeneratorsParametersInput.setText("SUBMITTED INPUT\n" +
                            "Length of side: " + numberRoomSide + ".\n" +
                            "Width of walls: from " + minWallWidth + " to " + maxWallWidth + ".");
                }
            }
        });
        submitGeneratorsParameterBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submitGeneratorsParameterBtn.setPrefWidth(260);
        submitGeneratorsParameterBtn.setPrefHeight(30);
        submitGeneratorsParameterBtn.setFont(BUTTON_FONT);

        modeLabelContainer.setPrefWidth(260);
        modeLabelContainer.setPrefHeight(20);
        modeLabelContainer.setAlignment(Pos.CENTER);

//        Label modeLbl = new Label();
        modeLbl.setTextAlignment(TextAlignment.CENTER);
        modeLbl.setPrefHeight(20);
        modeLbl.setFont(MODE_FONT);
        modeLbl.setTextFill(INFO_COLOR);

//        Button mode2DButton = new Button("2D");
        mode2DButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                set2DMode();
            }
        });
        mode2DButton.setPrefWidth(127);
        mode2DButton.setPrefHeight(30);
        mode2DButton.setFont(BUTTON_FONT);
        mode2DButton.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));


//        Button mode3DButton = new Button("3D");
        mode3DButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                set3DMode();
            }
        });
        mode3DButton.setPrefHeight(30);
        mode3DButton.setPrefWidth(127);
        mode3DButton.setFont(BUTTON_FONT);
        mode3DButton.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

//        HBox modeButtonsContainer = new HBox();
        modeButtonsContainer.setPrefHeight(30);
        modeButtonsContainer.setPrefWidth(260);
        modeButtonsContainer.setSpacing(6);
        modeButtonsContainer.getChildren().add(mode2DButton);
        modeButtonsContainer.getChildren().add(mode3DButton);

//        Label generateErrorLbl = new Label("");
        generateErrorLbl.setPrefWidth(260);
        setFixedHeight(generateErrorLbl, 0);
        generateErrorLbl.setTextAlignment(TextAlignment.CENTER);
        generateErrorLbl.setTextFill(ALARM_COLOR);

//        Label successfulGeneration = new Label("");
        successfulGeneration.setFont(INFO_FONT);
        successfulGeneration.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        successfulGeneration.setTextAlignment(TextAlignment.CENTER);
        successfulGeneration.setPrefWidth(260);
        successfulGeneration.setPrefHeight(40);

//        Button generateBtn = new Button("Generate field");
        generateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateModeAndInput(submittedGeneratorsParametersInput.getText(), modeLbl.getText())) {
                    generateField();
                }

            }
        });
        generateBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        generateBtn.setPrefWidth(260);
        generateBtn.setPrefHeight(30);
        generateBtn.setFont(BUTTON_FONT);

        coordinatesAContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        coordinatesAContainer.setSpacing(5);
        coordinatesAContainer.setPrefHeight(20);
        coordinatesAContainer.setAlignment(Pos.CENTER_LEFT);

        coordinatesBContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        coordinatesBContainer.setSpacing(5);
        coordinatesBContainer.setPrefHeight(20);
        coordinatesBContainer.setAlignment(Pos.CENTER_LEFT);

        coordinatesALbl.setPrefWidth(70);
        coordinatesALbl.setFont(LABEL_FONT);

        coordinatesBLbl.setPrefWidth(70);
        coordinatesBLbl.setFont(LABEL_FONT);

        coordinateAxTxt.setPrefWidth(55);

        coordinateAyTxt.setPrefWidth(55);

        coordinateAzTxt.setPrefWidth(55);

        coordinateBxTxt.setPrefWidth(55);

        coordinateByTxt.setPrefWidth(55);

        coordinateBzTxt.setPrefWidth(55);


        submitCoordinatesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                fieldGenerator2D = null;
//                fieldGenerator3D = null;
//                pane.getChildren().clear();
                if (is2DMode != null) {
                    VisualField2D.getInstance().setPath(null);
                    VisualField3D.getInstance().setPath(null);
                    if (is2DMode) {
                        if (validateCoordinates(coordinateAxTxt.getText(), coordinateAyTxt.getText(),
                                coordinateBxTxt.getText(), coordinateByTxt.getText())) {
                            submittedCoordinatesInput.setText("SUBMITTED COORDINATES\n" +
                                    "Room A:(" + coordinateAxTxt.getText() + ", " + coordinateAyTxt.getText() + ").\n" +
                                    "Room B:(" + coordinateBxTxt.getText() + ", " + coordinateByTxt.getText() + ").");
                        }
                    } else {
                        if (validateCoordinates(coordinateAxTxt.getText(), coordinateAyTxt.getText(), coordinateAzTxt.getText(),
                                coordinateBxTxt.getText(), coordinateByTxt.getText(), coordinateBzTxt.getText())) {
                            submittedCoordinatesInput.setText("SUBMITTED COORDINATES\n" +
                                    "Room A:(" + coordinateAxTxt.getText() + ", " + coordinateAyTxt.getText() + ", "
                                    + coordinateAzTxt.getText() + ").\n" +
                                    "Room B:(" + coordinateBxTxt.getText() + ", " + coordinateByTxt.getText() + ", "
                                    + coordinateBzTxt.getText() + ").");
                        }
                    }
                } else {
                    setFixedHeight(invalidCoordinatesLbl, 30);
                    invalidCoordinatesLbl.setText("Generate field, before setting coordinates.");
                }
            }
        });
        submitCoordinatesBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submitCoordinatesBtn.setPrefWidth(260);
        submitCoordinatesBtn.setPrefHeight(30);
        submitCoordinatesBtn.setFont(BUTTON_FONT);


        topCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX(),
                            VisualField2D.getInstance().getCurrentViewCellY() - 1);
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(topCellBtn,"resources/topCell.jpg");

        bottomCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX(),
                            VisualField2D.getInstance().getCurrentViewCellY() + 1);
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(bottomCellBtn,"resources/bottomCell.jpg");

        topRightCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX() + 1,
                            VisualField2D.getInstance().getCurrentViewCellY());
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(topRightCellBtn,"resources/topRightCell.jpg");

        bottomLeftCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX() - 1,
                            VisualField2D.getInstance().getCurrentViewCellY());
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(bottomLeftCellBtn,"resources/bottomLeftCell.jpg");

        topLeftCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX() - 1,
                            VisualField2D.getInstance().getCurrentViewCellY() - 1);
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(topLeftCellBtn,"resources/topLeftCell.jpg");


        bottomRightCellBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (validateExistenceOfField()) {
                    VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX() + 1,
                            VisualField2D.getInstance().getCurrentViewCellY() + 1);
                }
            }
        });
        ImageFillingRegion.setBackgroundWithImage(bottomRightCellBtn,"resources/bottomRightCell.jpg");

        for (int i = 0; i < 12; i++) {
            Button button = buttons3D.get(i);
            subNavigationContainer.getChildren().add(button);
            int row = i % 3;
            int column = i / 3;
            button.setTranslateX(column * 45);
            button.setTranslateY(row * 45);
            button.setPrefWidth(40);
            button.setPrefHeight(40);
            int[] delta = deltas.get(button);
            String path = "resources/"
                    + deltaToPeiceOfPath(delta[0])
                    + deltaToPeiceOfPath(delta[1])
                    + deltaToPeiceOfPath(delta[2])
                    + "3D.jpg";
            ImageFillingRegion.setBackgroundWithImage(button, path);
            button.setVisible(false);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int[] delta = deltas.get(event.getSource());
                    VisualField3D.getInstance().showCell(VisualField3D.getInstance().getCurrentViewCellX() + delta[0],
                            VisualField3D.getInstance().getCurrentViewCellY() + delta[1],
                            VisualField3D.getInstance().getCurrentViewCellZ() + delta[2]);
                }
            });

        }

//        button3DHandler = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                int[] delta = deltas.get(event.getSource());
//                VisualField3D.getInstance().showCell(VisualField3D.getInstance().getCurrentViewCellX() + delta[0],
//                        VisualField3D.getInstance().getCurrentViewCellY() + delta[1],
//                        VisualField3D.getInstance().getCurrentViewCellZ() + delta[2]);
//            }
//        };

        illegalAccessAlgorithmButtonsLbl.setFont(INFO_FONT);
        illegalAccessAlgorithmButtonsLbl.setPrefWidth(260);
        setFixedHeight(illegalAccessAlgorithmButtonsLbl, 0);
        illegalAccessAlgorithmButtonsLbl.setTextFill(ALARM_COLOR);

        wrongAccessToNavigatorLbl.setFont(INFO_FONT);
        wrongAccessToNavigatorLbl.setPrefWidth(260);
        setFixedHeight(wrongAccessToNavigatorLbl, 0);
        wrongAccessToNavigatorLbl.setTextFill(ALARM_COLOR);


        submittedCoordinatesInputContainer.setAlignment(Pos.CENTER);
        submittedCoordinatesInputContainer.setBackground(new Background(new BackgroundFill(MENU_ITEM_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        submittedCoordinatesInputContainer.setPrefWidth(260);
        submittedCoordinatesInputContainer.setPrefHeight(50);

        submittedCoordinatesInput.setFont(INFO_FONT);
        submittedCoordinatesInput.setTextAlignment(TextAlignment.CENTER);
        submittedCoordinatesInput.setPrefHeight(50);


        findNumberOfWaysBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (is2DMode != null) {
                    if (is2DMode) {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            input = input.substring(input.indexOf("\n"));
                            int aX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            int aY = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));
                            String secondCoordinates = input.substring(input.indexOf(")") + 1);
                            int bX = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf("(") + 1, secondCoordinates.indexOf(",")));
                            int bY = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf(",") + 2, secondCoordinates.indexOf(")")));

                            HexagonAlgorithmField hexagonAlgorithmField = new HexagonAlgorithmField(fieldGenerator2D.getLengthSide());
                            VisualField2D.getInstance().setHexagonAlgorithmField(hexagonAlgorithmField);
                            new Algorithm2D(hexagonAlgorithmField).calculateNumberWays(aX, aY, bX, bY);
                            VisualField2D.getInstance().showCell(0, 0);
                        }
                    } else {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            int aX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int aY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int aZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            input = input.substring(input.indexOf(")") + 1);
                            int bX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int bY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int bZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            SphereAlgorithmField sphereAlgorithmField = new SphereAlgorithmField(fieldGenerator3D.getLengthSide());
                            VisualField3D.getInstance().setSphereAlgorithmField(sphereAlgorithmField);
                            new Algorithm3D(sphereAlgorithmField).calculateNumberWays(aX, aY, aZ, bX, bY, bZ);
                            VisualField3D.getInstance().showCell(0, 0, 0);

                        }
                    }
                } else {
                    setFixedHeight(illegalAccessAlgorithmButtonsLbl, 30);
                    illegalAccessAlgorithmButtonsLbl.setText("Generate field, before choosing algorithm.");
                }
            }
        });
        findNumberOfWaysBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        findNumberOfWaysBtn.setPrefWidth(260);
        findNumberOfWaysBtn.setPrefHeight(30);
        findNumberOfWaysBtn.setFont(BUTTON_FONT);

        findDijkstraPathBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (is2DMode != null) {
                    if (is2DMode) {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            input = input.substring(input.indexOf("\n"));
                            int aX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            int aY = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));
                            String secondCoordinates = input.substring(input.indexOf(")") + 1);
                            int bX = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf("(") + 1, secondCoordinates.indexOf(",")));
                            int bY = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf(",") + 2, secondCoordinates.indexOf(")")));

                            PreparationForDijkstra2D preparation = new PreparationForDijkstra2D(aX, aY, bX, bY, fieldGenerator2D.getWalls(), fieldGenerator2D.getLengthSide());
                            preparation.convertData();
                            Dijkstra dijkstra = new Dijkstra(preparation.getStartPoint(), preparation.getEndPoint(),
                                    preparation.getNumberOfTheTop(), fieldGenerator2D.getLengthSide(), preparation.getMatrixOfShortestArcs());
                            dijkstra.shortestPathAlgorithm(preparation.getNumberOfTheTop());
                            dijkstra.fillStringOfAnswer2D(preparation, dijkstra.getAuxiliaryArray());
                            VisualField2D.getInstance().setPath(dijkstra.answerForDijkstra2D());

                            VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX(),
                                    VisualField2D.getInstance().getCurrentViewCellY());

                        }
                    } else {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            int startX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int startY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int startZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            input = input.substring(input.indexOf(")") + 1);
                            int endX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int endY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int endZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            PreparationForDijkstra3D preparationForDijkstra3D = new PreparationForDijkstra3D(startX, startY, startZ, endX, endY, endZ, fieldGenerator3D.getWalls(), fieldGenerator3D.getLengthSide());
                            preparationForDijkstra3D.processOfFindingEdges();
                            preparationForDijkstra3D.convertData();
                            Dijkstra dijkstraFor3D = new Dijkstra(preparationForDijkstra3D.getStartingPoin(), preparationForDijkstra3D.getEndPoint(), preparationForDijkstra3D.getNumberOfTheTop(), preparationForDijkstra3D.getNumberOfBalls(), preparationForDijkstra3D.getMatrixOfShortestArcs());
                            dijkstraFor3D.shortestPathAlgorithm(preparationForDijkstra3D.getNumberOfTheTop());
                            dijkstraFor3D.fillStringOfAnswer3D(preparationForDijkstra3D, dijkstraFor3D.getAuxiliaryArray());
//                            path = dijkstraFor3D.answerForDijkstra3D();
                            VisualField3D.getInstance().setPath(dijkstraFor3D.answerForDijkstra3D());
                            VisualField3D.getInstance().showCell(VisualField3D.getInstance().getCurrentViewCellX(),
                                    VisualField3D.getInstance().getCurrentViewCellY(),
                                    VisualField3D.getInstance().getCurrentViewCellZ());
                        }
                    }
                } else {
                    setFixedHeight(illegalAccessAlgorithmButtonsLbl, 30);
                    illegalAccessAlgorithmButtonsLbl.setText("Generate field, before choosing algorithm.");
                }
            }
        });
        findDijkstraPathBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        findDijkstraPathBtn.setPrefWidth(260);
        findDijkstraPathBtn.setPrefHeight(30);
        findDijkstraPathBtn.setFont(BUTTON_FONT);

        findAntPathBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (is2DMode != null) {
                    if (is2DMode) {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            input = input.substring(input.indexOf("\n"));
                            int aX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            int aY = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));
                            String secondCoordinates = input.substring(input.indexOf(")") + 1);
                            int bX = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf("(") + 1, secondCoordinates.indexOf(",")));
                            int bY = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf(",") + 2, secondCoordinates.indexOf(")")));

                            PreparationForDijkstra2D preparation = new PreparationForDijkstra2D(aX, aY, bX, bY, fieldGenerator2D.getWalls(), fieldGenerator2D.getLengthSide());
                            preparation.convertData();
                            AntAlgoritm antAlgorithm2D = new AntAlgoritm(preparation.getLengthSide(), preparation.getStartPoint(),
                                    preparation.getEndPoint(),
                                    preparation.getMatrixOfShortestArcs());
                            antAlgorithm2D.start();
                            VisualField2D.getInstance().setPath(antAlgorithm2D.answer2D(preparation));

                            VisualField2D.getInstance().showCell(VisualField2D.getInstance().getCurrentViewCellX(),
                                    VisualField2D.getInstance().getCurrentViewCellY());

                        }
                    } else {
                        if (validateFillingCoordinates(submittedCoordinatesInput.getText())) {
                            String input = submittedCoordinatesInput.getText();
                            int startX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int startY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int startZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            input = input.substring(input.indexOf(")") + 1);
                            int endX = Integer.parseInt(input.substring(input.indexOf("(") + 1, input.indexOf(",")));
                            input = input.substring(input.indexOf(",") + 2);
                            int endY = Integer.parseInt(input.substring(0, input.indexOf(",")));
                            int endZ = Integer.parseInt(input.substring(input.indexOf(",") + 2, input.indexOf(")")));

                            PreparationForDijkstra3D preparationForDijkstra3D = new PreparationForDijkstra3D(startX, startY, startZ, endX, endY, endZ, fieldGenerator3D.getWalls(), fieldGenerator3D.getLengthSide());
                            preparationForDijkstra3D.processOfFindingEdges();
                            preparationForDijkstra3D.convertData();
//                            Dijkstra dijkstraFor3D = new Dijkstra(preparationForDijkstra3D.getStartingPoin(), preparationForDijkstra3D.getEndPoint(), preparationForDijkstra3D.getNumberOfTheTop(), preparationForDijkstra3D.getNumberOfBalls(), preparationForDijkstra3D.getMatrixOfShortestArcs());
                            AntAlgoritm antAlgoritm3D=new AntAlgoritm(preparationForDijkstra3D.getNumberOfBalls(),preparationForDijkstra3D.getStartingPoin(),preparationForDijkstra3D.getEndPoint(),preparationForDijkstra3D.getMatrixOfShortestArcs());;
                            antAlgoritm3D.start();
//                            dijkstraFor3D.shortestPathAlgorithm(preparationForDijkstra3D.getNumberOfTheTop());
//                            dijkstraFor3D.fillStringOfAnswer3D(preparationForDijkstra3D, dijkstraFor3D.getAuxiliaryArray());
//                            path = dijkstraFor3D.answerForDijkstra3D();
                            VisualField3D.getInstance().setPath(antAlgoritm3D.answer3D(preparationForDijkstra3D));
                            VisualField3D.getInstance().showCell(VisualField3D.getInstance().getCurrentViewCellX(),
                                    VisualField3D.getInstance().getCurrentViewCellY(),
                                    VisualField3D.getInstance().getCurrentViewCellZ());
                        }
                    }
                } else {
                    setFixedHeight(illegalAccessAlgorithmButtonsLbl, 30);
                    illegalAccessAlgorithmButtonsLbl.setText("Generate field, before choosing algorithm.");
                }
            }
        });
        findAntPathBtn.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        findAntPathBtn.setPrefWidth(260);
        findAntPathBtn.setPrefHeight(30);
        findAntPathBtn.setFont(BUTTON_FONT);

        currentCellLbl.setFont(LABEL_FONT);
        currentCellLbl.setPrefHeight(30);
        currentCellLbl.setPrefWidth(150);

        for (HBox switchContainer : switchContainers) {
            switchContainer.setPrefHeight(30);
            switchContainer.setAlignment(Pos.CENTER_LEFT);
        }

        for (Button switchButton : switchButtons) {
            ImageFillingRegion.setBackgroundWithImage(switchButton,"resources/switchOn.jpg");
            switchButton.setPrefWidth(60);
            switchButton.setPrefHeight(30);
            switchButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boolean flag;
                    int index = switchButtons.indexOf(switchButton);
                    if (index == 0) {
                        flag = !isCellVisible;
                        isCellVisible = flag;
                    } else if (index == 1) {
                        flag = !isCoordinateVisible;
                        isCoordinateVisible = flag;
                    } else if (index == 2) {
                        flag = !isPathVisible;
                        isPathVisible = flag;
                    } else {
                        flag = !isNumberWaysVisible;
                        isNumberWaysVisible = flag;
                    }
                    if (flag) {
                        ImageFillingRegion.setBackgroundWithImage(switchButton,"resources/switchOn.jpg");
                    } else {
                        ImageFillingRegion.setBackgroundWithImage(switchButton,"resources/switchOff.jpg");
                    }
                    if (is2DMode != null) {
                        if (is2DMode) {
                            if (fieldGenerator2D != null) {
                                VisualField2D.getInstance().showCell(
                                        VisualField2D.getInstance().getCurrentViewCellX(), VisualField2D.getInstance().getCurrentViewCellY()
                                );
                            }
                        } else if (fieldGenerator3D != null) {
                            VisualField3D.getInstance().showCell(
                                    VisualField3D.getInstance().getCurrentViewCellX(), VisualField3D.getInstance().getCurrentViewCellY(),
                                    VisualField3D.getInstance().getCurrentViewCellX()
                            );
                        }
                    }
                }
            });

        }

        for (Label switchLabel : switchLabels) {
            switchLabel.setFont(LABEL_FONT);
            switchLabel.setPrefHeight(30);
            switchLabel.setPrefWidth(150);
        }


        menuBox.getChildren().add(modeLabelContainer);
        menuBox.getChildren().add(modeButtonsContainer);
        menuBox.getChildren().add(numberRoomSideContainer);
        menuBox.getChildren().add(minWidthWallContainer);
        menuBox.getChildren().add(maxWidthWallContainer);
        menuBox.getChildren().add(submitGeneratorsParameterBtn);
        menuBox.getChildren().add(submittedGeneratorsParametersInputContainer);
        menuBox.getChildren().add(initialErrorLbl);
        menuBox.getChildren().add(generateBtn);
        menuBox.getChildren().add(successfulGeneration);
        menuBox.getChildren().add(generateErrorLbl);
        menuBox.getChildren().add(coordinatesAContainer);
        menuBox.getChildren().add(coordinatesBContainer);
        menuBox.getChildren().add(submitCoordinatesBtn);
        menuBox.getChildren().add(submittedCoordinatesInputContainer);
        menuBox.getChildren().add(invalidCoordinatesLbl);
        menuBox.getChildren().add(findNumberOfWaysBtn);
        menuBox.getChildren().add(findDijkstraPathBtn);
        menuBox.getChildren().add(findAntPathBtn);
        menuBox.getChildren().add(currentCellLbl);
        menuBox.getChildren().add(illegalAccessAlgorithmButtonsLbl);


        coordinatesAContainer.getChildren().add(coordinatesALbl);
        coordinatesAContainer.getChildren().add(coordinateAxTxt);
        coordinatesAContainer.getChildren().add(coordinateAyTxt);
        coordinatesAContainer.getChildren().add(coordinateAzTxt);
        coordinatesBContainer.getChildren().add(coordinatesBLbl);
        coordinatesBContainer.getChildren().add(coordinateBxTxt);
        coordinatesBContainer.getChildren().add(coordinateByTxt);
        coordinatesBContainer.getChildren().add(coordinateBzTxt);
        numberRoomSideContainer.getChildren().add(numberRoomSideLbl);
        numberRoomSideContainer.getChildren().add(numberRoomSideTxt);
        maxWidthWallContainer.getChildren().add(maxWallWidthLbl);
        maxWidthWallContainer.getChildren().add(maxWallWidthTxt);
        minWidthWallContainer.getChildren().add(minWallWidthLbl);
        minWidthWallContainer.getChildren().add(minWallWidthTxt);
        modeLabelContainer.getChildren().add(modeLbl);
        submittedGeneratorsParametersInputContainer.getChildren().add(submittedGeneratorsParametersInput);
        submittedCoordinatesInputContainer.getChildren().add(submittedCoordinatesInput);

        wallsTitleContainer.getChildren().add(wallsTitle);
        navigatorTitleContainer.getChildren().add(navigatorTitle);
        wallsBox.getChildren().add(wallsTitleContainer);
        wallsBox.getChildren().add(listWalls);
        wallsBox.getChildren().add(navigatorTitleContainer);
        wallsBox.getChildren().add(mainNavigationContainer);
        wallsBox.getChildren().add(wrongAccessToNavigatorLbl);
        wallsBox.getChildren().add(cellSwitchContainer);
        wallsBox.getChildren().add(coordinateSwitchContainer);
        wallsBox.getChildren().add(pathSwitchContainer);
        wallsBox.getChildren().add(numberWaysSwitchContainer);

        cellSwitchContainer.getChildren().add(cellSwitchLbl);
        cellSwitchContainer.getChildren().add(cellSwitchBtn);
        coordinateSwitchContainer.getChildren().add(coordinateSwitchLbl);
        coordinateSwitchContainer.getChildren().add(coordinateSwitchBtn);
        pathSwitchContainer.getChildren().add(pathSwitchLbl);
        pathSwitchContainer.getChildren().add(pathSwitchBtn);
        numberWaysSwitchContainer.getChildren().add(numberWaysSwitchLbl);
        numberWaysSwitchContainer.getChildren().add(numberWaysSwitchBtn);


        mainNavigationContainer.getChildren().add(subNavigationContainer);

        subNavigationContainer.getChildren().add(topCellBtn);
        subNavigationContainer.getChildren().add(topLeftCellBtn);
        subNavigationContainer.getChildren().add(topRightCellBtn);
        subNavigationContainer.getChildren().add(bottomCellBtn);
        subNavigationContainer.getChildren().add(bottomLeftCellBtn);
        subNavigationContainer.getChildren().add(bottomRightCellBtn);

//        scrollPane.setContent(pane);
//        root.getChildren().add(wallsPaneContainer);

        root.getChildren().add(menuPaneContainer);

        menuPaneContainer.getChildren().add(pane);
        menuPaneContainer.getChildren().add(menuBox);
        root.getChildren().add(wallsBox);


        Scene scene = new Scene(root, 1250, 700, Color.DARKGREY);

        return scene;
    }

    private String deltaToPeiceOfPath(int number) {
        if (number == -1) {
            return ("_1");
        } else return "" + number;
    }


    private boolean validateFillingCoordinates(String input) {
        if (!input.isEmpty()) {
            setFixedHeight(illegalAccessAlgorithmButtonsLbl, 30);
            illegalAccessAlgorithmButtonsLbl.setText("");
            return true;
        } else {
            setFixedHeight(illegalAccessAlgorithmButtonsLbl, 30);
            illegalAccessAlgorithmButtonsLbl.setText("Choose coordinates of A and B");
            return false;
        }
    }

    private boolean validateExistenceOfField() {
        if (VisualField2D.getInstance().getCells() != null) {
            setFixedHeight(wrongAccessToNavigatorLbl, 0);
            wrongAccessToNavigatorLbl.setText("");
            return true;
        } else {
            setFixedHeight(wrongAccessToNavigatorLbl, 20);
            wrongAccessToNavigatorLbl.setText("Generate field, before using navigator");
            return false;
        }
    }

    private boolean validateCoordinates(String Ax, String Ay, String Az,
                                        String Bx, String By, String Bz) {
        try {
            int AxInt = Integer.parseInt(Ax);
            int AyInt = Integer.parseInt(Ay);
            int AzInt = Integer.parseInt(Az);
            int BxInt = Integer.parseInt(Bx);
            int ByInt = Integer.parseInt(By);
            int BzInt = Integer.parseInt(Bz);
            if (fieldGenerator3D != null) {
                if (!fieldGenerator3D.isExist(AxInt, AyInt, AzInt) || !fieldGenerator3D.isExist(BxInt, ByInt, BzInt)) {
                    setFixedHeight(invalidCoordinatesLbl, 50);
                    invalidCoordinatesLbl.setText("Coordinates are out of bounds.");
                    return false;
                } else {
                    if ((AxInt <= BxInt) && (AyInt <= ByInt) && (AzInt >= BzInt)) {
                        setFixedHeight(invalidCoordinatesLbl, 0);
                        invalidCoordinatesLbl.setText("");
                        return true;
                    } else {
                        setFixedHeight(invalidCoordinatesLbl, 50);
                        invalidCoordinatesLbl.setText("Ax must be less then Bx, same for y,\n Az must be bigger then Bz.");
                        return false;
                    }
                }
            } else {
                setFixedHeight(invalidCoordinatesLbl, 50);
                invalidCoordinatesLbl.setText("Generate field, before setting coordinates.");
                return false;
            }
        } catch (Exception e) {
            setFixedHeight(invalidCoordinatesLbl, 50);
            invalidCoordinatesLbl.setText("Fields must contain only numbers");
            return false;
        }
    }


    private boolean validateCoordinates(String Ax, String Ay, String Bx, String By) {
        try {
            int AxInt = Integer.parseInt(Ax);
            int AyInt = Integer.parseInt(Ay);
            int BxInt = Integer.parseInt(Bx);
            int ByInt = Integer.parseInt(By);
            if (fieldGenerator2D != null) {
                if (!fieldGenerator2D.isExist(AxInt, AyInt) || !fieldGenerator2D.isExist(BxInt, ByInt)) {
                    setFixedHeight(invalidCoordinatesLbl, 50);
                    invalidCoordinatesLbl.setText("Coordinates are out of bounds.");
                    return false;
                } else {
                    if ((AxInt <= BxInt) && (AyInt <= ByInt)) {
                        setFixedHeight(invalidCoordinatesLbl, 0);
                        invalidCoordinatesLbl.setText("");
                        return true;
                    } else {
                        setFixedHeight(invalidCoordinatesLbl, 50);
                        invalidCoordinatesLbl.setText("Ax must be less then Bx, same for y.");
                        return false;
                    }
                }
            } else {
                setFixedHeight(invalidCoordinatesLbl, 50);
                invalidCoordinatesLbl.setText("Generate field, before setting coordinates.");
                return false;
            }
        } catch (Exception e) {
            setFixedHeight(invalidCoordinatesLbl, 50);
            invalidCoordinatesLbl.setText("Fields must contain only numbers");
            return false;
        }
    }

    private void set2DMode(/*Label modeLabel*/) {
        is2DMode = true;
        pane.getChildren().clear();
        modeLbl.setText("Mode: 2D.");
        fieldGenerator2D = null;
        coordinateAzTxt.setVisible(false);
        coordinateBzTxt.setVisible(false);
        topCellBtn.setVisible(true);
        topLeftCellBtn.setVisible(true);
        topRightCellBtn.setVisible(true);
        bottomCellBtn.setVisible(true);
        bottomLeftCellBtn.setVisible(true);
        bottomRightCellBtn.setVisible(true);
        for (Button button : buttons3D) {
            button.setVisible(false);
        }
    }

    private void set3DMode(/*Label modeLabel*/) {
        is2DMode = false;
        pane.getChildren().clear();
        modeLbl.setText("Mode: 3D.");
        fieldGenerator3D = null;
        coordinateAzTxt.setVisible(true);
        coordinateBzTxt.setVisible(true);
        topCellBtn.setVisible(false);
        topLeftCellBtn.setVisible(false);
        topRightCellBtn.setVisible(false);
        bottomRightCellBtn.setVisible(false);
        bottomLeftCellBtn.setVisible(false);
        bottomCellBtn.setVisible(false);
        for (Button button : buttons3D) {
            button.setVisible(true);
        }
    }

    private void generateField(/*Label successfulGeneration, TextArea listWalls*/) {
        if (is2DMode) {
            fieldGenerator2D = new FieldGenerator2D(lengthOfSide, minWidth, maxWidth);
            fieldGenerator2D.generateBuilding();
            VisualField2D.getInstance().setFieldGenerator(fieldGenerator2D);
            VisualField2D.getInstance().buildDrawCells();
//            listWalls.setText("");
//            for (String wall : fieldGenerator2D.getWalls()) {
//                listWalls.appendText(wall + "\n");
//            }

        } else {
            fieldGenerator3D = new FieldGenerator3D(lengthOfSide, minWidth, maxWidth);
            fieldGenerator3D.generateBuilding();
//            listWalls.setText("");
//            for (String wall : fieldGenerator3D.getWalls()) {
//                listWalls.appendText(wall + "\n");
//            }
            VisualField3D.getInstance().setFieldGenerator(fieldGenerator3D);
            VisualField3D.getInstance().buildDrawCells();
        }
        successfulGeneration.setText("Generation of field №" + fieldCounter + " finished successfully.");
        fieldCounter++;


    }

    private boolean validateGeneratorParameters(String numberRoomSide, String maxWallWidth, String minWallWidth) {
        try {
            if ((1 <= Integer.parseInt(numberRoomSide) && (Integer.parseInt(numberRoomSide) <= 100) &&
                    (1 <= Integer.parseInt(maxWallWidth) && (Integer.parseInt(maxWallWidth) <= 1000) &&
                            (1 <= Integer.parseInt(minWallWidth) && (Integer.parseInt(minWallWidth) <= 1000))))) {
                if (Integer.parseInt(minWallWidth) <= Integer.parseInt(maxWallWidth)) {
                    setFixedHeight(initialErrorLbl, 0);
                    initialErrorLbl.setText("");
                    return true;
                } else {
                    initialErrorLbl.setText("Maximum width must be bigger then minimum\nwidth.");
                    setFixedHeight(initialErrorLbl, 50);
                    return false;
                }
            } else {
                initialErrorLbl.setText("Length of side must be in range from 1 to 100,\n" +
                        "bounds of width must be \nin range from 1 to 1000.");
                setFixedHeight(initialErrorLbl, 50);
                return false;
            }
        } catch (Exception e) {
            initialErrorLbl.setText("Fields must contain only numbers.");
            setFixedHeight(initialErrorLbl, 50);
            return false;
        }
    }

    private boolean validateModeAndInput(String submittedInput, String modeTxt) {
        if (!modeTxt.equals("") && !submittedInput.equals("")) {
            generateErrorLbl.setText("");
            setFixedHeight(generateErrorLbl, 0);
            return true;
        } else {
            generateErrorLbl.setText("You must type correct values in text fields above," +
                    "\nsubmit them and set mode(press \"2D\" or \"3D\").");
            setFixedHeight(generateErrorLbl, 50);
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

    public TextArea getListWalls() {
        return listWalls;
    }

    public boolean isCellVisible() {
        return isCellVisible;
    }

    public boolean isCoordinateVisible() {
        return isCoordinateVisible;
    }

    public boolean isPathVisible() {
        return isPathVisible;
    }

    public boolean isNumberWaysVisible() {
        return isNumberWaysVisible;
    }


    public Label getCurrentCellLbl() {
        return currentCellLbl;
    }
}
