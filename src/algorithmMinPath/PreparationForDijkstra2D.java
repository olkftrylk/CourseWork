package algorithmMinPath;

import java.util.List;

public class PreparationForDijkstra2D {
    private int x1;
    private int y1;
    private int x2;
    private int y2;


    private int startPoint;

    public int getStartPoint() {
        return startPoint;
    }

    private int endPoint;

    public int getEndPoint() {
        return endPoint;
    }
    private List<String> walls;


    private int[][] newRibs;
    private int[][] ribs;
    private int[][] ofVisit;

    private int lengthSide;

    public int getLengthSide() {
        return lengthSide;
    }

    private int numberOfTheTop;

    public int getNumberOfTheTop() {
        return numberOfTheTop;
    }

    private int[][] matrixOfShortestArcs;

    public int[][] getMatrixOfShortestArcs() {
        return matrixOfShortestArcs;
    }

    public PreparationForDijkstra2D(int x1, int y1, int x2, int y2, List<String> walls, int n) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.walls = walls;
        lengthSide = n;
        ribs = new int[walls.size()][5];

    }

    private void transformationDataFromStringsToCoordinates() {
        int numberRibs = 0;
        for (String wall : walls) {
            String secondCoordinates = wall.substring(wall.indexOf(")") + 2);
            ribs[numberRibs][0] = Integer.parseInt(wall.substring(1, wall.indexOf(",")));
            ribs[numberRibs][1] = Integer.parseInt(wall.substring(wall.indexOf(" ") + 1, wall.indexOf(")")));
            ribs[numberRibs][2] = Integer.parseInt(secondCoordinates.substring(1, secondCoordinates.indexOf(",")));
            ribs[numberRibs][3] = Integer.parseInt(secondCoordinates.substring(secondCoordinates.indexOf(" ") + 1, secondCoordinates.indexOf(")")));
            ribs[numberRibs][4] = Integer.parseInt(wall.substring(wall.indexOf(":") + 2));
            numberRibs++;
        }
    }


    public int transformationDataFromCoordinatesToNumbers(int numberOfIteration, boolean isFirstCoordinate) {
        int pointerOnCoordinate;
        if (isFirstCoordinate) {
            pointerOnCoordinate = 0;
        } else {
            pointerOnCoordinate = 2;
        }
        if ((ofVisit[numberOfIteration][pointerOnCoordinate] == -1) && (ofVisit[numberOfIteration][pointerOnCoordinate + 1] == -1)) {
            newRibs[numberOfIteration][pointerOnCoordinate] = numberOfTheTop;
            ofVisit[numberOfIteration][pointerOnCoordinate] = 1;
            ofVisit[numberOfIteration][pointerOnCoordinate + 1] = 1;
            numberOfTheTop++;
            checkRepeat(numberOfIteration, pointerOnCoordinate, true);
            checkRepeat(numberOfIteration, pointerOnCoordinate, false);
        }
        return 0;
    }

    public int checkRepeat(int numberOfIteration, int pointerOnCoordinate, boolean isFirst) {
        int pointerOnCoordinateRepeat;
        if (isFirst) {
            pointerOnCoordinateRepeat = 0;
        } else {
            pointerOnCoordinateRepeat = 2;
        }

        for (int firstCount = numberOfIteration; firstCount < ribs.length; firstCount++) {
            if ((ribs[numberOfIteration][pointerOnCoordinate] == ribs[firstCount][pointerOnCoordinateRepeat]) && (ribs[numberOfIteration][pointerOnCoordinate + 1] ==
                    ribs[firstCount][pointerOnCoordinateRepeat + 1])) {
                if ((ofVisit[firstCount][pointerOnCoordinateRepeat] == -1) && (ofVisit[firstCount][pointerOnCoordinateRepeat + 1] == -1)) {
                    newRibs[firstCount][pointerOnCoordinateRepeat] = newRibs[numberOfIteration][pointerOnCoordinate];
                    ofVisit[firstCount][pointerOnCoordinateRepeat] = 1;
                    ofVisit[firstCount][pointerOnCoordinateRepeat + 1] = 1;
                }
            }
        }
        return 0;
    }

    public int convertData() {     // main convertData     transformationDataFromCoordinatesToNumbers   checkRepeat
        transformationDataFromStringsToCoordinates();
        ofVisit = new int[ribs.length][4];
        newRibs = new int[ribs.length][4];
        int[][] finishMatrix = new int[ribs.length][3];
        for (int firstCount = 0; firstCount < ribs.length; firstCount++) {
            for (int secondCount = 0; secondCount < 4; secondCount++) {
                ofVisit[firstCount][secondCount] = -1;
            }
            finishMatrix[firstCount][2] = newRibs[firstCount][3] = ribs[firstCount][4];
        }
        numberOfTheTop = 0;
        for (int first = 0; first < ribs.length; first++) {
            transformationDataFromCoordinatesToNumbers(first, true);
            transformationDataFromCoordinatesToNumbers(first, false);
        }



        int maxElement = -1;
        for (int first = 0; first < ribs.length; first++) {
            for (int second = 0; second < 3; second = second + 2) {
          //      System.out.print(newRibs[first][second] + " ");
                if (newRibs[first][second] > maxElement)
                    maxElement = newRibs[first][second];
                if (second != 0) {
                    finishMatrix[first][second - 1] = newRibs[first][second];
                } else {
                    finishMatrix[first][second] = newRibs[first][second];
                }
            }
        //    System.out.println(newRibs[first][3]);
        //    System.out.println("");
        }
        start(finishMatrix, maxElement);
        return 0;
    }


    public int start(int[][] newRibs, int maxElement) {
        matrixOfShortestArcs = new int[maxElement + 1][maxElement + 1];

        for (int firstCounter = 0; firstCounter < matrixOfShortestArcs.length; firstCounter++) {
            for (int secondCounter = 0; secondCounter < matrixOfShortestArcs.length; secondCounter++) {
                if (firstCounter == secondCounter) {
                    matrixOfShortestArcs[firstCounter][secondCounter] = 0;
                } else {
                    matrixOfShortestArcs[firstCounter][secondCounter] = Integer.MAX_VALUE;
                }
            }
        }

        for (int first = 0; first < newRibs.length; first++) {
            matrixOfShortestArcs[newRibs[first][0]][newRibs[first][1]] = matrixOfShortestArcs[newRibs[first][1]][newRibs[first][0]] = newRibs[first][2];
        }
//        for (int firstCounter = 0; firstCounter < matrixOfShortestArcs.length; firstCounter++) {
//            for (int secondCounter = 0; secondCounter < matrixOfShortestArcs.length; secondCounter++) {
//           //     System.out.print(matrixOfShortestArcs[firstCounter][secondCounter] + "     ");
//            }
//         //   System.out.println();
//        }
        startPoint = findNode(x1, y1);
        endPoint = findNode(x2, y2);
        return 0;
    }

    private int findNode(int x, int y) {
        int result = -1;

        for (int i = 0; i < ribs.length; i++) {
            if (x == ribs[i][0] && y == ribs[i][1])
                result = newRibs[i][0];
            if (x == ribs[i][2] && y == ribs[i][3])
                result = newRibs[i][2];
        }

        return result;
    }

    public String findPoint(int x) {
        String result = null;

        for (int i = 0; i < newRibs.length; i++) {
         if (x==newRibs[i][0]){
             result=ribs[i][0]+", "+ribs[i][1];
             break;
         }
            if (x==newRibs[i][2]){
                result=ribs[i][2]+", "+ribs[i][3];
                break;
            }
        }

        return result;
    }
}
