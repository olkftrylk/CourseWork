package optimizedvisualization;

import algorithm.AlgorithmCubicle;
import algorithm.SphereAlgorithmField;
import javafx.scene.layout.Pane;
import model.FieldGenerator3D;

import java.util.ArrayList;
import java.util.List;

public class VisualField3D {
    private static VisualField3D instance = new VisualField3D();

    private double length = 45;
    private FieldGenerator3D fieldGenerator;

    private List<VisualCubicle> cells;
    private int[][] visibleCells = new int[31][3];

    private double centerVisualX;
    private double centerVisualY;

    private int centerX;
    private int centerY;
    private int centerZ;

    private Pane root;

    private int currentViewCellX;
    private int currentViewCellY;
    private int currentViewCellZ;

    private SphereAlgorithmField sphereAlgorithmField;
    private String path;

    private VisualField3D() {

    }

    public void setFieldGenerator(FieldGenerator3D fieldGenerator) {
        this.fieldGenerator = fieldGenerator;
    }

    public void buildDrawCells() {
        buildCells();
        drawCells();
        showCell(0, 0, 0);

//        root.getChildren().add(new Circle(centerVisualX, centerVisualY, 10));
    }

    private void drawCells() {
        root.getChildren().remove(0, root.getChildren().size());
        for (VisualCubicle cell : cells) {
            cell.init();
        }
    }

    private void buildCells() {
        centerVisualX = root.getWidth() / 2;
        centerVisualY = root.getHeight() / 2;

        centerX = 1;
        centerY = 2;
        centerZ = 1;

        double visualDeltaXTranslateX = -length;
        double visualDeltaXTranslateY = Math.sqrt(3) * length;
        double visualDeltaYTranslateX = 2 * length;
        double visualDeltaYTranslateY = 0;
        double visualDeltaZTranslateX = length;
        double visualDeltaZTranslateY = length / Math.sqrt(3);

        cells = new ArrayList<>();
        for (int level = 0; level < 3; level++) {
            for (int row = 0; row < 5 - level; row++) {
                for (int column = row; column < 5 - level; column++) {
                    int deltaX = row - centerX;
                    int deltaY = column - centerY;
                    int deltaZ = level - centerZ;
                    VisualCubicle cubicle = new VisualCubicle(centerVisualX
                            + visualDeltaXTranslateX * deltaX
                            + visualDeltaYTranslateX * deltaY
                            + visualDeltaZTranslateX * deltaZ,

                            centerVisualY + +visualDeltaXTranslateY * deltaX
                                    + visualDeltaYTranslateY * deltaY
                                    + visualDeltaZTranslateY * deltaZ);
                    cells.add(cubicle);
                }
            }
        }
    }

    public void showCell(int x, int y, int z) {
        Field2DSceneHolder.getInstance().getListWalls().setText("");
        currentViewCellX = x;
        currentViewCellY = y;
        currentViewCellZ = z;
        Field2DSceneHolder.getInstance().getCurrentCellLbl().setText("Current cell: (" + currentViewCellX + ", "
                + currentViewCellY + ", "
                + currentViewCellZ + ")");

        int leftUpX = x - centerX;
        int leftUpY = y - centerY;
        int leftUpZ = z - centerZ;

        int counter = 0;
        for (int level = 0; level < 3; level++) {
            for (int row = 0; row < 5 - level; row++) {
                for (int column = row; column < 5 - level; column++) {
                    int currentX = leftUpX + row;
                    int currentY = leftUpY + column;
                    int currentZ = leftUpZ + level;

                    if (fieldGenerator.isExist(currentX, currentY, currentZ)) {
                        cells.get(counter).updateCellCoordinates(new AlgorithmCubicle(currentX, currentY, currentZ));
                        if (Field2DSceneHolder.getInstance().isCellVisible()) {
                            cells.get(counter).showCircle();
                        }else {
                            cells.get(counter).hideCircle();
                        }
                        if (Field2DSceneHolder.getInstance().isCoordinateVisible()){
                            cells.get(counter).showCoordinate();
                        }else {
                            cells.get(counter).hideCoordinate();
                        }
                        setWallsToList(currentX, currentY, currentZ);
                        if (sphereAlgorithmField != null) {
                            if (Field2DSceneHolder.getInstance().isNumberWaysVisible()) {
                                cells.get(counter).showNumberWays();
                            }else {
                                cells.get(counter).hideNumberWays();
                            }
                        }
                    } else {
                        cells.get(counter).hideBase();
                        //
//                        cells.get(counter).updateCellCoordinates(new AlgorithmCubicle(currentX, currentY, currentZ));
//                        cells.get(counter).showBase();
                        //
                        if (sphereAlgorithmField != null) {
                            cells.get(counter).hideNumberWays();
                        }
                    }

                    visibleCells[counter][0] = currentX;
                    visibleCells[counter][1] = currentY;
                    visibleCells[counter][2] = currentZ;
                    counter++;
                }
            }
        }
        clearArrows();
        if(Field2DSceneHolder.getInstance().isPathVisible()){
            addRobotPath(path);
        }
    }

    private void setWallsToList(int x, int y, int z) {
        String text = Field2DSceneHolder.getInstance().getListWalls().getText();
        for (String wall : fieldGenerator.getWalls()) {
            int wallX = Integer.parseInt(wall.substring(wall.indexOf("(") + 1, wall.indexOf(",")));
            String subString = wall.substring(wall.indexOf(",") +1);
            int wallY = Integer.parseInt(subString.substring(subString.indexOf(" ") + 1, subString.indexOf(",")));
            int wallZ = Integer.parseInt(subString.substring(subString.indexOf(",") + 2, subString.indexOf(")")));
            if ((wallX == x) && (wallY == y) && (wallZ == z)) {
                text += (wall + "\n");
            }
        }
        Field2DSceneHolder.getInstance().getListWalls().setText(text);
    }

    private void clearArrows() {
        for (VisualCubicle cell : cells) {
            if (cell.getArrowView() != null) {
                root.getChildren().remove(cell.getArrowView());
            }
        }
    }


    public static VisualField3D getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        VisualField3D.getInstance().buildCells();
    }

    public Pane getRoot() {
        return root;
    }

    public SphereAlgorithmField getSphereAlgorithmField() {
        return sphereAlgorithmField;
    }

    public void addRobotPath(String path) {
        String currentInput = path;
        String subCurrentInput;

        boolean isEndOfPath = false;
        while (!isEndOfPath) {
            try {

                int startX = Integer.parseInt(currentInput.substring(currentInput.indexOf("(") + 1, currentInput.indexOf(",")));
                subCurrentInput = currentInput.substring(currentInput.indexOf(",") + 2);
                int startY = Integer.parseInt(subCurrentInput.substring(0, subCurrentInput.indexOf(",")));
                int startZ = Integer.parseInt(subCurrentInput.substring(subCurrentInput.indexOf(",") + 2, subCurrentInput.indexOf(")")));

                currentInput = currentInput.substring(currentInput.indexOf(")") + 1);

                int endX = Integer.parseInt(currentInput.substring(currentInput.indexOf("(") + 1, currentInput.indexOf(",")));
                subCurrentInput = currentInput.substring(currentInput.indexOf(",") + 2);
                int endY = Integer.parseInt(subCurrentInput.substring(0, subCurrentInput.indexOf(",")));
                int endZ = Integer.parseInt(subCurrentInput.substring(subCurrentInput.indexOf(",") + 2, subCurrentInput.indexOf(")")));

                addArrow(startX, startY, startZ, endX, endY, endZ);
            } catch (Exception e) {
                isEndOfPath = true;
            }
        }
    }

    private void addArrow(int startX, int startY, int startZ,
                          int endX, int endY, int endZ) {
        int index = getIndex(startX, startY, startZ);
        if (index > -1) {
            if (startZ - endZ == 0) {

                if ((startX - endX == -1) && (startY - endY == 0)) {
                    cells.get(index).addBottomLeftArrow();
                }

                if ((startX - endX == 0) && (startY - endY == -1)) {
                    cells.get(index).addRightArrow();
                }

                if ((startX - endX == -1) && (startY - endY == -1)) {
                    cells.get(index).addBottomRightArrow();
                }

                if ((startX - endX == 1) && (startY - endY == 0)) {
                    cells.get(index).addTopRightArrow();
                }

                if ((startX - endX == 0) && (startY - endY == 1)) {
                    cells.get(index).addLeftArrow();
                }

                if ((startX - endX == 1) && (startY - endY == 1)) {
                    cells.get(index).addTopLeftArrow();
                }
            }

            if (startZ - endZ == 1) {
                if ((startX - endX == 0) && (startY - endY == 0)) {
                    cells.get(index).addDownTopLeftArrow();
                }

                if ((startX - endX == -1) && (startY - endY == -1)) {
                    cells.get(index).addDownBottomArrow();
                }

                if ((startX - endX == 0) && (startY - endY == -1)) {
                    cells.get(index).addDownTopRightArrow();
                }
            }

            if (startZ - endZ == -1) {
                if ((startX - endX == 1) && (startY - endY == 1)) {
                    cells.get(index).addUpTopArrow();
                }
                if ((startX - endX == 0) && (startY - endY == 1)) {
                    cells.get(index).addUpBottomLeftArrow();
                }
                if ((startX - endX == 0) && (startY - endY == 0)) {
                    cells.get(index).addUpBottomRightArrow();
                }
            }


        }

    }

    private int getIndex(int x, int y, int z) {
        for (int i = 0; i < visibleCells.length; i++) {
            if ((visibleCells[i][0] == x) && (visibleCells[i][1] == y
                    && visibleCells[i][2] == z)) {
                return i;
            }
        }
        return -1;
    }

    public void setSphereAlgorithmField(SphereAlgorithmField sphereAlgorithmField) {
        this.sphereAlgorithmField = sphereAlgorithmField;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public int getCurrentViewCellX() {
        return currentViewCellX;
    }

    public int getCurrentViewCellY() {
        return currentViewCellY;
    }

    public int getCurrentViewCellZ() {
        return currentViewCellZ;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
