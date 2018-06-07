package optimizedvisualization;

import algorithm.AlgorithmCell;
import algorithm.HexagonAlgorithmField;
import javafx.scene.layout.Pane;
import model.FieldGenerator2D;

import java.util.ArrayList;
import java.util.List;

public class VisualField2D {
    private static VisualField2D instance = new VisualField2D();

//    private double fieldHeight;
//    private double fieldWidth;

    private double newLength;
//    private int counterShownCells = 0;

    //    private Pane pane;
//    private ScrollPane scrollPane;
    private FieldGenerator2D fieldGenerator;

    private List<VisualCell> cells;
    private int[][] visibleCells = new int[111][2];;

    private double widthFromWallToWall = 5;

//    private double startVisualX;
//    private double startVisualY;

    private double centerVisualX;
    private double centerVisualY;

    private int heightInCells;
    private int widthInCells;

    private int centerX;
    private int centerY;
    private Pane root;
    private int currentViewCellX;
    private int currentViewCellY;
    private HexagonAlgorithmField hexagonAlgorithmField;
    private String path;

    private VisualField2D() {

    }

    public static VisualField2D getInstance() {
        return instance;
    }

    public void setFieldGenerator(FieldGenerator2D fieldGenerator) {
        this.fieldGenerator = fieldGenerator;
//        numberOfRoomsInSide = fieldGenerator.getLengthSide();
    }


    public void buildDrawCells() {
        buildCells();
        drawCells();
        showCell(0, 0);

//        root.getChildren().add(new Circle(centerVisualX, centerVisualY, 10));
    }

    private void drawCells() {
        root.getChildren().remove(0, root.getChildren().size());
        for (VisualCell cell : cells) {
            cell.init();
        }
    }


    private void buildCells() {

        newLength = VisualCell.getSideLength() + widthFromWallToWall;
//        fieldHeight = newLength * (2 * numberOfRoomsInSide - 1) * Math.sqrt(3);
//        fieldWidth = newLength * 1.5 * (2 * numberOfRoomsInSide - 2) /*- newLength*/ + 2 * newLength;

//        startVisualX = newLength;
//        startVisualY = Math.sqrt(3) / 2 * newLength * numberOfRoomsInSide;
        centerVisualX = root.getWidth() / 2;
        centerVisualY = root.getHeight() / 2;

        heightInCells = (int) Math.floor(root.getHeight() / (newLength * Math.sqrt(3)));
        heightInCells += (1 + (heightInCells % 2));

        widthInCells = (int) Math.floor((root.getWidth() - 2 * newLength) / (1.5 * newLength)) + 1;
        widthInCells += (1 + (widthInCells % 2));
        widthInCells += ((widthInCells - 1) % 4);
//        widthInCells += (4 - ((widthInCells - 1) % 4));
//        if ((widthInCells - 1) % 4 == 0) {//3 1
//            widthInCells -= 4;
//        }

        centerX = widthInCells / 2;
        centerY = centerX / 2 + heightInCells / 2;

//        widthInCells = widthInCells + (1 + (widthInCells % 2));
//        widthInCells = widthInCells + (1 + (widthInCells % 4))
        cells = new ArrayList<>();
        for (int i = 0; i < widthInCells; i++) {

            int newI = i - centerX;
            int firstJ = (i + 1) / 2;


            for (int k = 0; k < (heightInCells - (i % 2)); k++) {
//            if (widthInCells % 2 == 1){
                int j = firstJ + k;
                int newJ = j - centerY;
                VisualCell cell = new VisualCell(newI * 1.5 * newLength + centerVisualX,
                        newJ * Math.sqrt(3) * newLength - newI * Math.sqrt(3) / 2 * newLength + centerVisualY
                        /*+ newLength * Math.sqrt(3)*/);
//                cell.updateCellCoordinates(new AlgorithmCell(i - centerX,j - centerY));
                cells.add(cell);
//                cell.init();
//                        centerVisualY + (i - (widthInCells - 1) / 2)            * newLength * Math.sqrt(3)/2  )       );
//            }
            }
        }

//        List<String> rooms = fieldGenerator.getRooms();


//        for (String room : rooms) {
//            int x = Integer.parseInt(room.substring(1, room.indexOf(",")));
//            int y = Integer.parseInt(room.substring(room.indexOf(" ") + 1, room.indexOf(")")));
//            cells.add(new VisualCell(new AlgorithmCell(x, y), calculatePaneX(x), calculatePaneY(x, y)));
//            scrollToX(calculatePaneX(0));
//            ;
//            scrollToY(calculatePaneY(0, 0));
//            scrollPane.setVvalue(0.05);
//            scrollPane.setHvalue(calculatePaneX(11)/fieldWidth - scrollPane.getWidth()/2);
//            pane.getChildren().add(new VisualCell(new AlgorithmCell(x, y),
//                    0.0 + startVisualX + x * newLength * 1.5,
//                    0.0 + startVisualY + y * newLength * Math.sqrt(3)
//                            - x * newLength * Math.sqrt(3) / 2));
//        }
    }

    public void showCell(int x, int y) {
        Field2DSceneHolder.getInstance().getListWalls().setText("");
        currentViewCellX = x;
        currentViewCellY = y;
        Field2DSceneHolder.getInstance().getCurrentCellLbl().setText("Current cell: (" + currentViewCellX + ", "
                + currentViewCellY + ")");
        int leftUpX = x - centerX;
        int leftUpY = y - centerY;

        int counter = 0;
        for (int i = 0; i < widthInCells; i++) {
            int firstJ = (i + 1) / 2;
            for (int k = 0; k < (heightInCells - (i % 2)); k++) {

                int j = firstJ + k + leftUpY;

                if (fieldGenerator.isExist(leftUpX + i, j)) {
                    cells.get(counter).updateCellCoordinates(new AlgorithmCell(leftUpX + i, j));
                    if (Field2DSceneHolder.getInstance().isCellVisible()) {
                        cells.get(counter).showHexagon();
                    }else {
                        cells.get(counter).hideHexagon();
                    }
                    if (Field2DSceneHolder.getInstance().isCoordinateVisible()){
                        cells.get(counter).showCoordinate();
                    }else {
                        cells.get(counter).hideCoordinate();
                    }
                    setWallsToList(leftUpX + i, j);
//                    counterShownCells++;
                    if (hexagonAlgorithmField != null) {
                        if (Field2DSceneHolder.getInstance().isNumberWaysVisible()) {
                            cells.get(counter).showNumberWays();
                        }else {
                            cells.get(counter).hideNumberWays();
                        }
                    }
                } else {
                    cells.get(counter).hideBase();
                    if (hexagonAlgorithmField != null) {
                        cells.get(counter).hideNumberWays();
                    }
//                    cells.get(counter).updateCellCoordinates();
                }

                visibleCells[counter][0] = leftUpX + i;
                visibleCells[counter][1] = j;

                counter++;
            }
        }
        clearArrows();
//        String path = "(2, 2) (2, 3) (3, 3) (4, 4)";
        if(Field2DSceneHolder.getInstance().isPathVisible()){
            addRobotPath(path);
        }

    }

    private void setWallsToList(int x, int y) {
        String text = Field2DSceneHolder.getInstance().getListWalls().getText();
        for (String wall : fieldGenerator.getWalls()) {
            int wallX = Integer.parseInt(wall.substring(wall.indexOf("(") + 1, wall.indexOf(",")));
            int wallY = Integer.parseInt(wall.substring(wall.indexOf(" ") + 1, wall.indexOf(")")));
            if ((wallX == x) && (wallY == y)) {
                text += (wall + "\n");
            }
        }
        Field2DSceneHolder.getInstance().getListWalls().setText(text);
    }

    private void clearArrows() {
        for (VisualCell cell : cells) {
            if (cell.getArrowView() != null){
                root.getChildren().remove(cell.getArrowView());
            }
        }
    }

//    public void showCell(int x, int y) {
//        scrollPane.setHvalue(findVisualCell(x, y).getVisualX() / fieldWidth);
//        scrollPane.setVvalue(findVisualCell(x, y).getVisualY() / fieldHeight);
//    }
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


    public void setRoot(Pane root) {
        this.root = root;
    }

    public Pane getRoot() {
        return root;
    }

    public List<VisualCell> getCells() {
        return cells;
    }

    public int getCurrentViewCellX() {
        return currentViewCellX;
    }

    public int getCurrentViewCellY() {
        return currentViewCellY;
    }

    public void setHexagonAlgorithmField(HexagonAlgorithmField hexagonAlgorithmField) {
        this.hexagonAlgorithmField = hexagonAlgorithmField;
    }

    public HexagonAlgorithmField getHexagonAlgorithmField() {
        return hexagonAlgorithmField;
    }

    public void addRobotPath(String path) {
        String currentInput = path;

        boolean isEndOfPath = false;
        while (!isEndOfPath) {
            try {
                int startX = Integer.parseInt(currentInput.substring(currentInput.indexOf("(") + 1, currentInput.indexOf(",")));
                int startY = Integer.parseInt(currentInput.substring(currentInput.indexOf(",") + 2, currentInput.indexOf(")")));
                currentInput = currentInput.substring(currentInput.indexOf(")") + 1);
                int endX = Integer.parseInt(currentInput.substring(currentInput.indexOf("(") + 1, currentInput.indexOf(",")));
                int endY = Integer.parseInt(currentInput.substring(currentInput.indexOf(",") + 2, currentInput.indexOf(")")));
                addArrow(startX, startY, endX, endY);
            } catch (Exception e) {
                isEndOfPath = true;
            }
        }
    }

    private void addArrow(int startX, int startY, int endX, int endY) {
        int index = getIndex(startX, startY);
        if (index > -1) {
            if (((startX - endX) == 0) && ((startY - endY) == 1)){
                cells.get(index).addTopArrow();
            }

            if (((startX - endX) == 1) && ((startY - endY) == 0)){
                cells.get(index).addBottomLeftArrow();
            }


            if (((startX - endX) == 1) && ((startY - endY) == 1)){
                cells.get(index).addTopLeftArrow();
            }

            if (((startX - endX) == 0) && ((startY - endY) == -1)){
                cells.get(index).addBottomArrow();
            }

            if (((startX - endX) == -1) && ((startY - endY) == 0)){
                cells.get(index).addTopRightArrow();
            }


            if (((startX - endX) == -1) && ((startY - endY) == -1)){
                cells.get(index).addBottomRightArrow();

            }
        }
    }

    private int getIndex(int x, int y) {
//        boolean result = false;
        for (int i = 0; i < visibleCells.length; i++) {
            if ((visibleCells[i][0] == x) && (visibleCells[i][1] == y)) {
                return i;
            }
        }
        return -1;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
