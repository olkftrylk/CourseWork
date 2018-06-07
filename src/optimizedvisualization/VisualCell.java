package optimizedvisualization;

import algorithm.AlgorithmCell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class VisualCell extends Polygon {
    AlgorithmCell cell;

    private static VisualField2D field = VisualField2D.getInstance();
    private static final Color CELL_FILL = Color.TRANSPARENT;
    private static final Color CELL_STROKE_COLOR = Color.BLACK;

    private static final double INFO_LABEL_HEIGHT = 16;
    private ImageView arrowView;

    //    private static final Font NUMBER_WAYS_FONT = Font.font("System", FontWeight.BOLD, 20);
    private static final Color NUMBER_WAYS_COLOR = Color.GREEN;

    private double visualX;
    private double visualY;

    private Label numberWaysLbl = new Label();
    private HBox numberWaysHolder = new HBox();

    private Label coordinatesLbl = new Label();
    private HBox coordinatesHolder = new HBox();

    private static int sideLength = 45;


    public VisualCell(double visualX, double visualY) {
        this.visualX = visualX;
        this.visualY = visualY;
        Pane pane = field.getRoot();
        getPoints().addAll(0.0 + visualX - sideLength, 0.0 + visualY,
                0.0 + visualX - sideLength / 2, 0.0 + visualY + Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX + sideLength / 2, 0.0 + visualY + Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX + sideLength, 0.0 + visualY,
                0.0 + visualX + sideLength / 2, 0.0 + visualY - Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX - sideLength / 2, 0.0 + visualY - Math.sqrt(3) / 2 * sideLength);
//        pane.getChildren().add(coordinatesLbl);
//        setTranslateX(visualX);
//        setTranslateY(visualY);
        numberWaysHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                sideLength + INFO_LABEL_HEIGHT);
        numberWaysHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT * 1.5);
        setFixedWidth(numberWaysHolder, 2 * sideLength - 2 * INFO_LABEL_HEIGHT);
        setFixedHeight(numberWaysHolder, INFO_LABEL_HEIGHT);

        numberWaysLbl.setTextFill(NUMBER_WAYS_COLOR);
        numberWaysLbl.setText("");
        numberWaysHolder.setAlignment(Pos.CENTER);
        numberWaysHolder.getChildren().add(numberWaysLbl);


        coordinatesHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                sideLength + INFO_LABEL_HEIGHT / 2);
        coordinatesHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT / 2);
        setFixedWidth(coordinatesHolder, 2 * sideLength - INFO_LABEL_HEIGHT);
        setFixedHeight(coordinatesHolder, INFO_LABEL_HEIGHT);
        coordinatesHolder.setAlignment(Pos.BASELINE_RIGHT);
        coordinatesHolder.getChildren().add(coordinatesLbl);
//
        setFill(CELL_FILL);
        setStroke(CELL_STROKE_COLOR);

    }

    private void setFixedHeight(Region region, double height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setPrefHeight(height);
    }

    public void init() {
        field.getRoot().getChildren().add(this);
        field.getRoot().getChildren().add(coordinatesHolder);
        field.getRoot().getChildren().add(numberWaysHolder);
    }

    public void showBase() {
        showHexagon();
        showCoordinate();
    }


    public void hideHexagon() {
        setVisible(false);
    }

    public void showHexagon(){
        setVisible(true);
    }

    public void showCoordinate(){
        coordinatesHolder.setVisible(true);
    }

    public void hideCoordinate(){
        coordinatesHolder.setVisible(false);
    }

    public void hideBase() {
        hideHexagon();
        hideCoordinate();
    }

    public void drawNumberWays() {
        field.getRoot().getChildren().add(numberWaysHolder);
    }

    private void setFixedWidth(Region region, double width) {
        region.setMaxWidth(width);
        region.setMinWidth(width);
        region.setPrefWidth(width);
    }


    public static int getSideLength() {
        return sideLength;
    }

    public double getVisualX() {
        return visualX;
    }

    public double getVisualY() {
        return visualY;
    }

    public AlgorithmCell getCell() {
        return cell;
    }

    private void setCell(AlgorithmCell cell) {
        this.cell = cell;
//        coordinatesLbl.setText(cell.getX() + ", " + cell.getY() );
    }

    public void updateCellCoordinates(AlgorithmCell cell) {
        setCell(cell);
        coordinatesLbl.setText(cell.getX() + ", " + cell.getY());
    }


    public void showNumberWays() {
        long numberWays = field.getHexagonAlgorithmField().getCell(cell.getX(), cell.getY()).getNumberOfWays();
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

    public void addTopArrow() {
        arrowView = new ImageView("resources/top40.png");
        arrowView.setTranslateX(visualX - 10);
        arrowView.setTranslateY(visualY - VisualCell.getSideLength() * Math.sqrt(3) / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }


    public void addTopRightArrow() {
        arrowView = new ImageView("resources/topRight40.png");
        arrowView.setTranslateX(visualX + 3 * VisualCell.getSideLength() / 4 - 10);
        arrowView.setTranslateY(visualY - VisualCell.getSideLength() * Math.sqrt(3) / 4 - 10);
        field.getRoot().getChildren().add(arrowView);
    }


    public void addTopLeftArrow() {
        arrowView = new ImageView("resources/topLeft40.png");
        arrowView.setTranslateX(visualX - 3 * VisualCell.getSideLength() / 4 - 10);
        arrowView.setTranslateY(visualY - VisualCell.getSideLength() * Math.sqrt(3) / 4 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addBottomLeftArrow() {
        arrowView = new ImageView("resources/bottomLeft40.png");
        arrowView.setTranslateX(visualX - 3 * VisualCell.getSideLength() / 4 - 10);
        arrowView.setTranslateY(visualY + VisualCell.getSideLength() * Math.sqrt(3) / 4 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addBottomRightArrow() {
        arrowView = new ImageView("resources/bottomRight40.png");
        arrowView.setTranslateX(visualX + 3 * VisualCell.getSideLength() / 4 - 10);
        arrowView.setTranslateY(visualY + VisualCell.getSideLength() * Math.sqrt(3) / 4 - 10);
        field.getRoot().getChildren().add(arrowView);
    }

    public void addBottomArrow() {
        arrowView = new ImageView("resources/bottom40.png");
        arrowView.setTranslateX(visualX - 10);
        arrowView.setTranslateY(visualY + VisualCell.getSideLength() * Math.sqrt(3) / 2 - 10);
        field.getRoot().getChildren().add(arrowView);
    }


    public ImageView getArrowView() {
        return arrowView;
    }
}
