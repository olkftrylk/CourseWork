package visualization;

import algorithm.AlgorithmCell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class VisualCell extends Polygon {
    AlgorithmCell cell;

    private static VisualField2D field = VisualField2D.getInstance();
    private static final Color CELL_FILL = Color.TRANSPARENT;
    private static final Color CELL_STROKE_COLOR = Color.BLACK;

    private static final double INFO_LABEL_HEIGHT = 16;

    private double visualX;
    private double visualY;

    private Label numberWaysLbl = new Label();
    private HBox numberWaysHolder = new HBox();

    private Label coordinatesLbl = new Label();
    private HBox coordinatesHolder = new HBox();

    private static int sideLength = 45;


    public VisualCell(AlgorithmCell cell, double visualX, double visualY) {
        this.cell = cell;
        this.visualX = visualX;
        this.visualY = visualY;

        Pane pane = field.getPane();
        getPoints().addAll(0.0 + visualX - sideLength, 0.0 + visualY,
                0.0 + visualX - sideLength / 2, 0.0 + visualY + Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX + sideLength / 2, 0.0 + visualY + Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX + sideLength, 0.0 + visualY,
                0.0 + visualX + sideLength / 2, 0.0 + visualY - Math.sqrt(3) / 2 * sideLength,
                0.0 + visualX - sideLength / 2, 0.0 + visualY - Math.sqrt(3) / 2 * sideLength);
        pane.getChildren().add(coordinatesLbl);
//        setTranslateX(visualX);
//        setTranslateY(visualY);
        numberWaysHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                sideLength + INFO_LABEL_HEIGHT);
        numberWaysHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT * 1.5);
        setFixedWidth(numberWaysHolder, 2 * sideLength -  2 * INFO_LABEL_HEIGHT);
        setFixedHeight(numberWaysHolder, INFO_LABEL_HEIGHT);

        numberWaysLbl.setText(""+cell.getNumberOfWays());
        numberWaysHolder.setAlignment(Pos.CENTER);
        numberWaysHolder.getChildren().add(numberWaysLbl);
//        numberWaysHolder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        coordinatesHolder.setTranslateX(0.0 + visualX /*+ sideLength - sideLength/2*/ -
                sideLength + INFO_LABEL_HEIGHT / 2);
        coordinatesHolder.setTranslateY(0.0 + visualY - INFO_LABEL_HEIGHT / 2);
        setFixedWidth(coordinatesHolder, 2 * sideLength - INFO_LABEL_HEIGHT);
        setFixedHeight(coordinatesHolder, INFO_LABEL_HEIGHT);
        coordinatesLbl.setText(cell.getX() + ", " + cell.getY());
        coordinatesHolder.setAlignment(Pos.BASELINE_RIGHT);
        coordinatesHolder.getChildren().add(coordinatesLbl);
//        coordinatesHolder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//
        setFill(CELL_FILL);
        setStroke(CELL_STROKE_COLOR);
    }

    private void setFixedHeight(Region region, double height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setPrefHeight(height);
    }

    public void drawBase(){
        field.getPane().getChildren().add(this);
        field.getPane().getChildren().add(coordinatesHolder);
    }

    public void drawNumberWays(){
        field.getPane().getChildren().add(numberWaysHolder);
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
}
