package visualization;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class IntroductionSceneHolder implements SceneHolder{
    private static final IntroductionSceneHolder instance = new IntroductionSceneHolder();
    private RobotApplication parent;

    private static final Color  TITLE_BACKGROUND_COLOR = Color.web("#AB9C73");
    private static final Color  TITLE_TEXT_FILL_COLOR = Color.web("#D2BE96");
    private static final Color  BUTTON_TEXT_FILL_COLOR = Color.web("6A7E8D");
    private static final Color  BUTTON_FILL_COLOR = Color.web("#9BAF8E");
    private static final Color  BUTTONS_BACKGROUND_COLOR = Color.web("E3DCC0");

    private static final Font TITLE_FONT = Font.font("Dezen",FontWeight.BOLD,40);
    private static final Font BUTTON_FONT = Font.font("Dezen",FontWeight.BOLD,20);

    private IntroductionSceneHolder(){

    }

    public static IntroductionSceneHolder getInstance() {
        return instance;
    }

    public void setParent(RobotApplication parent) {
        IntroductionSceneHolder.getInstance().parent = parent;
    }

    @Override
    public Scene getScene() {
        VBox root = new VBox();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setSpacing(10);
        root.setPadding(new Insets(10,10,10,10));

        VBox buttonsContainer = new VBox();
        buttonsContainer.setBackground( new Background(new BackgroundFill(BUTTONS_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonsContainer.setMinHeight(420);
        buttonsContainer.setSpacing(20);
        buttonsContainer.setAlignment(Pos.CENTER);

        HBox titleBackground = new HBox();
        titleBackground.setBackground(new Background(new BackgroundFill(TITLE_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        titleBackground.setMaxHeight(200);
        titleBackground.setMinHeight(200);
        titleBackground.setAlignment(Pos.CENTER);


        Label title = new Label("ROBOT PATH");
        title.setTextFill(TITLE_TEXT_FILL_COLOR);
        title.setFont(TITLE_FONT);

        Button startButton = new Button("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setField2DScene();
            }
        });
        startButton.setBackground(new Background(new BackgroundFill(BUTTON_FILL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setTextFill(BUTTON_TEXT_FILL_COLOR);
        startButton.setFont(Font.font("Dezen",FontWeight.BOLD,20));
        startButton.setMaxHeight(50);
        startButton.setMinHeight(50);
        startButton.setMinWidth(200);
        startButton.setMaxWidth(200);


        Scene scene = new Scene(root, 1250,650,Color.DARKGREY);

        root.getChildren().add(titleBackground);
        root.getChildren().add(buttonsContainer);

        titleBackground.getChildren().add(title);

        buttonsContainer.getChildren().add(startButton);

        return scene;
    }
}
