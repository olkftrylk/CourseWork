package optimizedvisualization;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RobotApplication extends Application{
    private Stage stage;

    private IntroductionSceneHolder introductionSceneHolder = IntroductionSceneHolder.getInstance();
    private Field2DSceneHolder field2DSceneHolder = Field2DSceneHolder.getInstance();


    @Override
    public void start(Stage primaryStage) throws Exception {
        introductionSceneHolder.setParent(this);
        field2DSceneHolder.setParent(this);

        primaryStage.setTitle("Robot path");

        primaryStage.setScene(introductionSceneHolder.getScene());


        primaryStage.show();
        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void setIntroductionScene(){
        stage.setScene(introductionSceneHolder.getScene());
    }

    public void setField2DScene(){
        stage.setScene(field2DSceneHolder.getScene());
    }

    public Stage getStage() {
        return stage;
    }
}
