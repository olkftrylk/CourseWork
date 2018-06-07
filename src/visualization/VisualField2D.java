package visualization;

import algorithm.AlgorithmCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.FieldGenerator2D;

import java.util.ArrayList;
import java.util.List;

public class VisualField2D {
    private static VisualField2D instance = new VisualField2D();

    private double fieldHeight;
    private double fieldWidth;

    private double newLength;

    private Pane pane;
    private ScrollPane scrollPane;
    private FieldGenerator2D fieldGenerator;

    private List<VisualCell> cells;

    private int numberOfRoomsInSide;

    private double widthFromWallToWall = 5;

    private double startVisualX;
    private double startVisualY;

    private VisualField2D() {

    }

    public static VisualField2D getInstance() {
        return instance;
    }

    public void setFieldGenerator(FieldGenerator2D fieldGenerator) {
        this.fieldGenerator = fieldGenerator;
        numberOfRoomsInSide = fieldGenerator.getLengthSide();
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


    public void buildDrawCells() {
        buildCells();
        drawCells();
    }

    private void drawCells() {
        pane.getChildren().remove(0, pane.getChildren().size());
        for (VisualCell cell : cells) {
            cell.drawBase();
        }
    }


    private void buildCells() {

        newLength = VisualCell.getSideLength() + widthFromWallToWall;
        fieldHeight = newLength * (2 * numberOfRoomsInSide - 1) * Math.sqrt(3) - widthFromWallToWall;
        fieldWidth = newLength * 1.5 * (2 * numberOfRoomsInSide - 2) /*- newLength*/ + 2 * newLength;

        startVisualX = newLength;
        startVisualY = Math.sqrt(3) / 2 * newLength * numberOfRoomsInSide;


        cells = new ArrayList<>();

        List<String> rooms = fieldGenerator.getRooms();


        for (String room : rooms) {
            int x = Integer.parseInt(room.substring(1, room.indexOf(",")));
            int y = Integer.parseInt(room.substring(room.indexOf(" ") + 1, room.indexOf(")")));
            cells.add(new VisualCell(new AlgorithmCell(x, y), calculatePaneX(x), calculatePaneY(x, y)));
            scrollToX (calculatePaneX(0));;
            scrollToY(calculatePaneY(0,0));
//            scrollPane.setVvalue(0.05);
//            scrollPane.setHvalue(calculatePaneX(11)/fieldWidth - scrollPane.getWidth()/2);
//            pane.getChildren().add(new VisualCell(new AlgorithmCell(x, y),
//                    0.0 + startVisualX + x * newLength * 1.5,
//                    0.0 + startVisualY + y * newLength * Math.sqrt(3)
//                            - x * newLength * Math.sqrt(3) / 2));
        }
    }

//    public void showCell(int x, int y) {
//        scrollPane.setHvalue(findVisualCell(x, y).getVisualX() / fieldWidth);
//        scrollPane.setVvalue(findVisualCell(x, y).getVisualY() / fieldHeight);
//    }

//    private VisualCell findVisualCell(int x, int y) {
//        for (VisualCell cell : cells) {
//            if ((cell.getCell().getX() == x) && (cell.getCell().getY() == y)) {
//                return cell;
//            }
//        }
//        return null;
//    }

    public double getWidthFromWallToWall() {
        return widthFromWallToWall;
    }

    public ScrollPane getRoot() {
        return scrollPane;
    }


    public void setRoot(ScrollPane root) {
        this.scrollPane = root;
    }

    public Pane getPane() {
        return pane;
    }

    private double calculatePaneX(int x) {
        return 0.0 + startVisualX + x * newLength * 1.5;
    }

    private double calculatePaneY(int x, int y) {
        return 0.0 + startVisualY + y * newLength * Math.sqrt(3)
                - x * newLength * Math.sqrt(3) / 2;
    }

    private void scrollToX(double x) {
        scrollPane.setHvalue((x - scrollPane.getWidth() / 2) / (fieldWidth - scrollPane.getWidth()));
    }

    private void scrollToY(double y) {
        scrollPane.setVvalue((y - scrollPane.getHeight() / 2) / (fieldHeight - scrollPane.getHeight()));
    }
}
