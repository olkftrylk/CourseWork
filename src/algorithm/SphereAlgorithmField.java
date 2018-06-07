package algorithm;

import java.util.ArrayList;

public class SphereAlgorithmField {

    private ArrayList<AlgorithmCubicle> cells = new ArrayList<>();
    private int lengthOfSide;

    public SphereAlgorithmField(int n){
        lengthOfSide = n;

        for (int index = 0; index < lengthOfSide; index++) {
            for (int row = 0; row < lengthOfSide - index ; row++) {
                for (int level = 0; level < row+1; level++) {
                    cells.add (new AlgorithmCubicle (index, lengthOfSide - 1 - row,level, -1));
                }
            }
        }
    }

    public AlgorithmCubicle getCell(int x, int y, int z){
        AlgorithmCubicle result = null;
        for (AlgorithmCubicle cell : cells) {
            if((cell.getX() == x) && (cell.getY() == y)
                && (cell.getZ() == z)){
                result = cell;
            }
        }
        return result;
    }

    public int getLengthOfSide(){
        return lengthOfSide;
    }

    public void setLengthOfSide(int lengthOfSide) {
        this.lengthOfSide = lengthOfSide;
    }
}
