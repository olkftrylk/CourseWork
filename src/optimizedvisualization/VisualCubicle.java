package optimizedvisualization;

import algorithm.AlgorithmCubicle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VisualCubicle extends Circle {
    AlgorithmCubicle cubicle;

    private static VisualField3D field = VisualField3D.getInstance();
    private static final Color CELL_FILL = Color.TRANSPARENT;
    private static final Color CELL_STROKE_COLOR = Color.WHITE;

    private static final double INFO_LABEL_HEIGHT = 16;
    private ImageView arrowView;

    private static final Color NUMBER_WAYS_COLOR = Color.GREEN;


    private double visualX;
    private double visualY;

    private Label numberWaysLbl = new Label();
    private HBox numberWaysHolder = new HBox();

    private Label coordinatesLbl = new Label();
    private HBox coordinatesHolder = new HBox();

    private static int radius = 45;

    public VisualCubicle(double visualX, double visualY){
        this.visualX = visualX;
        this.visualY = visualY;
        Pane pane = field.getRoot();

        setTranslateX(visualX);
        setTranslateY(visualY);
        setRadius(radius);

        numberWaysHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                radius + INFO_LABEL_HEIGHT);
        numberWaysHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT * 1.5);
        setFixedWidth(numberWaysHolder, 2 * radius - 2 * INFO_LABEL_HEIGHT);
        setFixedHeight(numberWaysHolder, INFO_LABEL_HEIGHT);

        numberWaysLbl.setTextFill(NUMBER_WAYS_COLOR);
        numberWaysLbl.setText("");
        numberWaysHolder.setAlignment(Pos.CENTER);
        numberWaysHolder.getChildren().add(numberWaysLbl);

        coordinatesHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                radius + INFO_LABEL_HEIGHT / 2);
        coordinatesHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT / 2);
        setFixedWidth(coordinatesHolder, 2 * radius - INFO_LABEL_HEIGHT);
        setFixedHeight(coordinatesHolder, INFO_LABEL_HEIGHT);
        coordinatesHolder.setAlignment(Pos.BASELINE_RIGHT);
        coordinatesHolder.getChildren().add(coordinatesLbl);

        setFill(CELL_FILL);
        setStroke(CELL_STROKE_COLOR);
    }

    private void setFixedHeight(Region region, double height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setPrefHeight(height);
    }

    private void setFixedWidth(Region region, double width) {
        region.setMaxWidth(width);
        region.setMinWidth(width);
        region.setPrefWidth(width);
    }

    public void init() {
        field.getRoot().getChildren().add(this);
        field.getRoot().getChildren().add(coordinatesHolder);
        field.getRoot().getChildren().add(numberWaysHolder);
    }

    public void showCircle() {
        setVisible(true);
    }

    public void hideCircle() {
        setVisible(false);
    }

    public void showCoordinate() {
        coordinatesHolder.setVisible(true);
    }

    public void hideCoordinate() {
        coordinatesHolder.setVisible(false);
    }

    public void showBase() {
        showCircle();
        showCoordinate();
    }

    public void hideBase() {
        hideCircle();
        hideCoordinate();
    }

    public void drawNumberWays() {
        field.getRoot().getChildren().add(numberWaysHolder);
    }

    public double getVisualX() {
        return visualX;
    }

    public double getVisualY() {
        return visualY;
    }

    public AlgorithmCubicle getCell() {
        return cubicle;
    }

    private void setCell(AlgorithmCubicle cell) {
        this.cubicle = cell;
//        coordinatesLbl.setText(cell.getX() + ", " + cell.getY() );
    }

    public void updateCellCoordinates(AlgorithmCubicle cell) {
        setCell(cell);
        coordinatesLbl.setText(cell.getX() + ", " + cell.getY() + ", " + cell.getZ());
    }

    public void showNumberWays() {
        int numberWays = field.getSphereAlgorithmField().getCell(cubicle.getX(), cubicle.getY(), cubicle.getZ()).getNumberOfWays();
        if (numberWays > 0) {
            numberWaysLbl.setText("" + numberWays);
            numberWaysHolder.setVisible(true);
        } else {
            hideNumberWays();
        }
    }


    public void hideNumberWays() {
        numberWaysLbl.setText("");
        numberWaysHolder.setVisible(false);
    }



    public void addBottomLeftArrow() {
//        arrowView.setFitWidth(40);
//        arrowView.setFitHeight(40);
        arrowView = new ImageView("resources/bottomLeft3D.png");
        arrowView.setTranslateX(visualX - radius / 2 - 10);
        arrowView.setTranslateY(visualY + radius * Math.sqrt(3) / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addTopLeftArrow() {
        arrowView = new ImageView("resources/topLeft3D.png");
        arrowView.setTranslateX(visualX - radius - 10);
        arrowView.setTranslateY(visualY - radius * Math.sqrt(3) - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addTopRightArrow() {
        arrowView = new ImageView("resources/topRight3D.png");
        arrowView.setTranslateX(visualX + radius / 3 - 10);
        arrowView.setTranslateY(visualY - radius * Math.sqrt(3) / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addBottomRightArrow() {
        arrowView = new ImageView("resources/bottomRight3D.png");
        arrowView.setTranslateX(visualX + radius / 2 - 10);
        arrowView.setTranslateY(visualY + radius * Math.sqrt(3) / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addLeftArrow(){
        arrowView = new ImageView("resources/left3D.png");
        arrowView.setTranslateX(visualX - radius - 10);
        arrowView.setTranslateY(visualY - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addRightArrow(){
        arrowView = new ImageView("resources/right3D.png");
        arrowView.setTranslateX(visualX + radius - 10);
        arrowView.setTranslateY(visualY - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addDownTopLeftArrow(){
        arrowView = new ImageView("resources/topLeft40.png");
        arrowView.setTranslateX(visualX - radius * Math.sqrt(3) / 2 - 10);
        arrowView.setTranslateY(visualY - radius / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addDownTopRightArrow(){
        arrowView = new ImageView("resources/topRight40.png");
        arrowView.setTranslateX(visualX + radius * Math.sqrt(3) / 2 - 10);
        arrowView.setTranslateY(visualY - radius / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addDownBottomArrow(){
        arrowView = new ImageView("resources/bottom40.png");
        arrowView.setTranslateX(visualX - 10);
        arrowView.setTranslateY(visualY + radius - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addUpBottomLeftArrow(){
        arrowView = new ImageView("resources/bottomLeft40.png");
        arrowView.setTranslateX(visualX - radius * Math.sqrt(3) / 2 - 10);
        arrowView.setTranslateY(visualY + radius / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addUpBottomRightArrow(){
        arrowView = new ImageView("resources/bottomRight40.png");
        arrowView.setTranslateX(visualX + radius * Math.sqrt(3) / 2 - 10);
        arrowView.setTranslateY(visualY + radius / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addUpTopArrow(){
        arrowView = new ImageView("resources/top40.png");
        arrowView.setTranslateX(visualX - 10);
        arrowView.setTranslateY(visualY - radius - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public ImageView getArrowView() {
        return arrowView;
    }
}
