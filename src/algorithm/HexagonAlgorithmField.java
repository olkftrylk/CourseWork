package algorithm;

import java.util.ArrayList;

public class HexagonAlgorithmField {
    private ArrayList<AlgorithmCell> cells = new ArrayList<>();
    private int lengthOfSide;

    public HexagonAlgorithmField(int n) {
        lengthOfSide = n;
        for (int i = 0; i < (2 * n - 1); i++) {
            //constructing main line
            cells.add(new AlgorithmCell(i, i));
        }

        for (int i = 0; i < (n - 1); i++) {
            //constructing top part
            for (int j = 0; j < (2 * n - 2 - i); j++) {
                cells.add(new AlgorithmCell(i+j+1, j));
            }
            //constructing bottom part
            for (int j = 0; j < (2 * n - 2 - i); j++) {
                cells.add(new AlgorithmCell(j, i+j+1));
            }
        }
    }

    //return null if cell has invalid coordinates
    public AlgorithmCell getCell(int x, int y) {
        AlgorithmCell result = null;
        for (AlgorithmCell cell : cells) {
            if ((cell.getX() == x) && (cell.getY()) == y) {
                result = cell;
            }
        }
        return result;
    }

    public int getLengthOfSide() {
        return lengthOfSide;
    }

    public void setLengthOfSide(int lengthOfSide) {
        this.lengthOfSide = lengthOfSide;
    }
}
