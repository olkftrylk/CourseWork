package algorithmMinPath;

import java.util.List;

public class PreparationForDijkstra3D {
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;
    private int[][] ofVisit;

    private int startingPoin;

    public int getStartingPoin() {
        return startingPoin;
    }

    private int endPoint;

    public int getEndPoint() {
        return endPoint;
    }

    private int numberOfBalls;

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    private int numberOfTheTop;

    public int getNumberOfTheTop() {
        return numberOfTheTop;
    }

    private int[][] vertexList;
    private int[][] newRibs;
    private int[][] ribs;
    private int edgeNumber;
    private int[][] matrixOfShortestArcs;

    public int[][] getMatrixOfShortestArcs() {
        return matrixOfShortestArcs;
    }

    private List<String> walls;

    public PreparationForDijkstra3D(int x1, int y1, int z1, int x2, int y2, int z2, List<String> walls, int numberOfBalls ) {
       this.x1=x1;
       this.y1=y1;
       this.z1=z1;
       this.x2=x2;
       this.y2=y2;
       this.z2=z2;
       this.walls=walls;
       this.numberOfBalls = numberOfBalls;
       vertexList=new int[walls.size()][4];
        transformationDataFromStringsToCoordinates();
    }
    private void transformationDataFromStringsToCoordinates() {
        int numberRibs = 0;
        for (String wall : walls) {
            String forZ = wall.substring(wall.indexOf(",") + 1);
            String forZ1 = forZ.substring(forZ.indexOf(",") + 1);
            vertexList[numberRibs][0] = Integer.parseInt(wall.substring(1, wall.indexOf(",")));
            vertexList[numberRibs][1] = Integer.parseInt(forZ.substring(1, forZ.indexOf(",")));
            vertexList[numberRibs][2] = Integer.parseInt(forZ1.substring(1, forZ1.indexOf(")")));
            vertexList[numberRibs][3] = Integer.parseInt(wall.substring(wall.indexOf(":") + 2));
            numberRibs++;
        }
    }
    public int transformationDataFromCoordinatesToNumbers(int numberOfIteration, boolean isFirstCoordinate) {
        int pointerOnCoordinate;
        if (isFirstCoordinate) {
            pointerOnCoordinate = 0;
        } else {
            pointerOnCoordinate = 3;
        }
        if ((ofVisit[numberOfIteration][pointerOnCoordinate] == -1) &&
                (ofVisit[numberOfIteration][pointerOnCoordinate + 1] == -1)&&
                    (ofVisit[numberOfIteration][pointerOnCoordinate + 2] == -1)) {
            newRibs[numberOfIteration][pointerOnCoordinate] = numberOfTheTop;
            ofVisit[numberOfIteration][pointerOnCoordinate] = 1;
            ofVisit[numberOfIteration][pointerOnCoordinate + 1] = 1;
            ofVisit[numberOfIteration][pointerOnCoordinate + 2] = 1;
            numberOfTheTop++;
            checkRepeat(numberOfIteration, pointerOnCoordinate, true);
            checkRepeat(numberOfIteration, pointerOnCoordinate, false);
        }
        return 0;
    }
    public int convertData() {     // main convertData     transformationDataFromCoordinatesToNumbers   checkRepeat
        ofVisit = new int[ribs.length][6];
        newRibs = new int[ribs.length][5];
        int[][] finishMatrix = new int[ribs.length][3];
        for (int firstCount = 0; firstCount < ribs.length; firstCount++) {
            for (int secondCount = 0; secondCount < 6; secondCount++) {
                ofVisit[firstCount][secondCount] = -1;
            }
            finishMatrix[firstCount][2] = newRibs[firstCount][4] = ribs[firstCount][6];
        }
        numberOfTheTop = 0;
        for (int first = 0; first < ribs.length; first++) {
            transformationDataFromCoordinatesToNumbers(first, true);
            transformationDataFromCoordinatesToNumbers(first, false);
        }
        int maxElement = -1;
        for (int first = 0; first < ribs.length; first++) {
            for (int second = 0; second < 3; second = second + 2) {
                if (newRibs[first][second] > maxElement)
                    maxElement = newRibs[first][second];
                if (second == 0) {
                    finishMatrix[first][second] = newRibs[first][second];
                } else {
                    finishMatrix[first][second-1] = newRibs[first][second+1];
                }
            }
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
        for (int firstCounter = 0; firstCounter < matrixOfShortestArcs.length; firstCounter++) {
            for (int secondCounter = 0; secondCounter < matrixOfShortestArcs.length; secondCounter++) {
            }
        }
        startingPoin = findNode(x1, y1,z1);
        endPoint = findNode(x2, y2,z2);
        return 0;
    }

    private int findNode(int x, int y,int z) {
        int result = -1;

        for (int i = 0; i < ribs.length; i++) {
            if (x == ribs[i][0] && y == ribs[i][1]&& z == ribs[i][2])
                result = newRibs[i][0];
            if (x == ribs[i][3] && y == ribs[i][4]&& z == ribs[i][5])
                result = newRibs[i][3];
        }

        return result;
    }
    public int checkRepeat(int numberOfIteration, int pointerOnCoordinate, boolean isFirst) {
        int pointerOnCoordinateRepeat;
        if (isFirst) {
            pointerOnCoordinateRepeat = 0;
        } else {
            pointerOnCoordinateRepeat = 3;
        }

        for (int firstCount = numberOfIteration; firstCount < ribs.length; firstCount++) {
            if ((ribs[numberOfIteration][pointerOnCoordinate] == ribs[firstCount][pointerOnCoordinateRepeat]) &&
                    (ribs[numberOfIteration][pointerOnCoordinate + 1] == ribs[firstCount][pointerOnCoordinateRepeat + 1])&&
                        (ribs[numberOfIteration][pointerOnCoordinate + 2] == ribs[firstCount][pointerOnCoordinateRepeat + 2])) {
                if ((ofVisit[firstCount][pointerOnCoordinateRepeat] == -1) &&
                        (ofVisit[firstCount][pointerOnCoordinateRepeat + 1] == -1)&&
                            (ofVisit[firstCount][pointerOnCoordinateRepeat + 2] == -1)) {
                    newRibs[firstCount][pointerOnCoordinateRepeat] = newRibs[numberOfIteration][pointerOnCoordinate];
                    ofVisit[firstCount][pointerOnCoordinateRepeat] = 1;
                    ofVisit[firstCount][pointerOnCoordinateRepeat + 1] = 1;
                    ofVisit[firstCount][pointerOnCoordinateRepeat + 2] = 1;
                }
            }
        }
        return 0;
    }
    public int checkTheEntryIntoThePyramid(int x, int y, int z, int vertexNumber) {
        if ((z >= 0) && (z <= numberOfBalls - 1) && (x >= 0) && (x <= numberOfBalls - 1 - z) && (y >= x) && (y <= numberOfBalls - 1 - z)) {
            ribs[edgeNumber][0] = vertexList[vertexNumber][0];
            ribs[edgeNumber][1] = vertexList[vertexNumber][1];
            ribs[edgeNumber][2] = vertexList[vertexNumber][2];
            ribs[edgeNumber][3] = x;
            ribs[edgeNumber][4] = y;
            ribs[edgeNumber][5] = z;
            for (int counter = 0; counter <vertexList.length ; counter++) {
                if ((vertexList[counter][0]==x)&&(vertexList[counter][1]==y)&&(vertexList[counter][2]==z)){
                    ribs[edgeNumber][6] = (vertexList[vertexNumber][3]+vertexList[counter][3]);
                }
            }
            edgeNumber++;
        }
        return 0;
    }
    public String toPoint(int node){
        String result=null;
        for (int counter = 0; counter < newRibs.length; counter++) {
            if (node==newRibs[counter][0]){
                result=ribs[counter][0]+", "+ribs[counter][1]+", "+ribs[counter][2];
                break;
            }
            if (node==newRibs[counter][3]){
                result=ribs[counter][3]+", "+ribs[counter][4]+", "+ribs[counter][5];
                break;
            }
        }
        return result;
    }
    public int createEdges(int x, int y, int z, int vartexNumber) {
        checkTheEntryIntoThePyramid(x, y - 1, z + 1, vartexNumber);
        checkTheEntryIntoThePyramid(x, y, z + 1, vartexNumber);
        checkTheEntryIntoThePyramid(x - 1, y - 1, z + 1, vartexNumber);
        checkTheEntryIntoThePyramid(x, y - 1, z, vartexNumber);
        checkTheEntryIntoThePyramid(x - 1, y - 1, z, vartexNumber);
        checkTheEntryIntoThePyramid(x - 1, y, z, vartexNumber);
        checkTheEntryIntoThePyramid(x, y + 1, z, vartexNumber);
        checkTheEntryIntoThePyramid(x + 1, y + 1, z, vartexNumber);
        checkTheEntryIntoThePyramid(x + 1, y, z, vartexNumber);
        checkTheEntryIntoThePyramid(x, y, z - 1, vartexNumber);
        checkTheEntryIntoThePyramid(x, y + 1, z - 1, vartexNumber);
        checkTheEntryIntoThePyramid(x + 1, y + 1, z - 1, vartexNumber);
        return 0;
    }

    public int processOfFindingEdges() {
        edgeNumber = 0;
        int ribsLenth;
        ribsLenth=(int)(2*(numberOfBalls-1)*numberOfBalls*(numberOfBalls+1));
        ribs = new int[ribsLenth][7];
        for (int vertex = 0; vertex < vertexList.length; vertex++) {
            createEdges(vertexList[vertex][0], vertexList[vertex][1], vertexList[vertex][2],vertex);
        }
        return 0;
    }
}
