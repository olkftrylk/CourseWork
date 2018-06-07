package model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FieldGenerator3D {
    public List<String> walls = new ArrayList<>();

    private int lengthSide;
    private int minWeight;
    private int maxWeight;

    public FieldGenerator3D(int n, int minWeight, int maxWeight) {
        this.lengthSide = n;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public List<String> generateBuilding() {
        walls = new ArrayList<>();
        for (int index = 0; index < lengthSide; index++) {
            for (int row = 0; row < lengthSide - index ; row++) {
                for (int level = 0; level < row+1; level++) {
                    walls.add("("

//                            + index + ", "
//                            + (lengthSide - 1 - row) + ", "
//                            + (level) + "): "

                            +(index) + ", "
                            +(lengthSide - 1 - row) +", "
                            +(level) + "): "

                            + (ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1)));
                }
            }
        }
        return walls;
    }


    public static void main(String[] args) {
        FieldGenerator3D fieldGenerator = new FieldGenerator3D(3, 1, 5);
        int counter = 0;
        for (String s : fieldGenerator.generateBuilding()) {
            System.out.println(s);
            counter++;
        }
        System.out.println(counter);
    }


    public List<String> getWalls() {
        return walls;
    }

    public boolean isExist(int x, int y, int z) {
        int row = lengthSide - 1 - y;
        if ((x >= 0) && (x < lengthSide) &&
                (row >= 0) && (row < lengthSide - x) &&
                (z >= 0) && (z < row + 1)) {
            return true;
        }
        return false;
    }

    public int getLengthSide() {
        return lengthSide;
    }
}

