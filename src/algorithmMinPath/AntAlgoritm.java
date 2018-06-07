package algorithmMinPath;

import java.util.concurrent.ThreadLocalRandom;

public class AntAlgoritm {
    private int startPoint;
    private int endPoint;
    private int[] ofVisit;
    private int[][] matrixOfShortestArcs;
    private double sumEta;
    private int lenthSide;
    private double[] arrayOfPheromones;
    private double[] probabilityArray;
    private double[] etaForTheCurrentPoint;
    private int[] finishArrayOfPathWays;
    private int pathLength;

    private int pseudoRand(double count) {
        return ThreadLocalRandom.current().nextInt(0, ((int) count) + 1);
    }

    public AntAlgoritm(int lenthSide, int startPoint, int endPoint, int[][] matrixOfShortestArcs) {
        this.lenthSide = lenthSide;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.matrixOfShortestArcs = matrixOfShortestArcs;
        ofVisit = new int[matrixOfShortestArcs.length];
    }

    public void start() {
        fillingArrayOfProbability(startPoint);
    }

    public void initializationArrayOfProbability() {

        for (int counter = 0; counter < matrixOfShortestArcs.length; counter++) {
            if ((etaForTheCurrentPoint[counter] != -1) && (ofVisit[counter] != 1)) {
                probabilityArray[counter] = (etaForTheCurrentPoint[counter] * arrayOfPheromones[counter]) / sumEta;
                helpToFillProbilityArray(counter);
            }
        }
    }

    public void refreshArrayOfPheromones() {
        for (int counter = 0; counter < finishArrayOfPathWays.length; counter++) {
            if (finishArrayOfPathWays[counter] > 0) {
                if (arrayOfPheromones[finishArrayOfPathWays[counter]] > lenthSide / 7)
                    arrayOfPheromones[finishArrayOfPathWays[counter]] -= lenthSide / 7;
            }
        }
    }

    public int fillingArrayOfProbability(int numberOfVertex) {
        pathLength = 0;
        for (int counter = 0; counter < matrixOfShortestArcs.length; counter++) {
            ofVisit[counter] = 0;
        }
        ofVisit[startPoint] = 1;
        finishArrayOfPathWays = new int[matrixOfShortestArcs.length];
        for (int counter = 0; counter < finishArrayOfPathWays.length; counter++) {
            finishArrayOfPathWays[counter] = -1;
        }
        int counter = 0;
        do {
            probabilityArray = new double[matrixOfShortestArcs.length];
            fillingArrayOfEta(numberOfVertex);
            initializationArrayOfProbability();
            finishArrayOfPathWays[counter] = numberOfVertex;
            numberOfVertex = changeNextVertex();
            if (counter > 0) {
            }
            if (numberOfVertex == -1) {
                refreshArrayOfPheromones();
                fillingArrayOfProbability(startPoint);
                break;
            }
            counter++;
        } while (numberOfVertex != endPoint);
        System.out.print("");
        return 0;
    }

    private void helpToFillProbilityArray(int numberOfVertex) {
        for (int counter = numberOfVertex - 1; counter >= 0; counter--) {
            if ((probabilityArray[counter] != 0) && (ofVisit[counter] != 1)) {
                probabilityArray[numberOfVertex] += probabilityArray[counter];
                break;
            }
        }
    }

    public void fillingArrayOfEta(int numberOfVertex) {
        sumEta = 0;
        arrayOfPheromones = new double[matrixOfShortestArcs.length];
        etaForTheCurrentPoint = new double[matrixOfShortestArcs.length];
        for (int counter = 0; counter < etaForTheCurrentPoint.length; counter++) {
            etaForTheCurrentPoint[counter] = -1;
            arrayOfPheromones[counter] = lenthSide;
        }
        for (int counter = 0; counter < etaForTheCurrentPoint.length; counter++) {
            if ((matrixOfShortestArcs[numberOfVertex][counter] != Integer.MAX_VALUE) && (counter != numberOfVertex)) {
                etaForTheCurrentPoint[counter] = (1 / (double) matrixOfShortestArcs[numberOfVertex][counter]);
                sumEta += etaForTheCurrentPoint[counter] * arrayOfPheromones[counter];
            }
        }
    }

    public int changeNextVertex() {
        double maxElement = -1;
        for (int n = 0; n < probabilityArray.length; n++) {
            if (probabilityArray[n] > maxElement)
                maxElement = probabilityArray[n];
        }
        int randomNumber = pseudoRand(maxElement * 100);
        return checkRandomNumberInProbabilitiArray(randomNumber);
    }

    public int checkRandomNumberInProbabilitiArray(int randomNumber) {
        for (int counter = probabilityArray.length - 1; counter >= 0; counter--) {
            if ((probabilityArray[counter] > 0) && (ofVisit[counter] != 1)) {
                for (int secondCounter = counter; secondCounter >= 0; secondCounter--) {
                    if ((secondCounter == 0) && (ofVisit[counter] != 1)) {
                        if ((0 <= randomNumber) && (probabilityArray[counter] * 100 >= randomNumber)) {
                            ofVisit[counter] = 1;
                            return counter;
                        }
                    }
                    if ((probabilityArray[secondCounter] > 0) && (ofVisit[counter] != 1) && (counter != secondCounter)) {
                        if ((probabilityArray[secondCounter] * 100 <= randomNumber) && (probabilityArray[counter] * 100 >= randomNumber)) {
                            ofVisit[counter] = 1;
                            return counter;
                        } else
                            break;
                    }
                }
            }
        }
        return -1;
    }

    public String answer2D(PreparationForDijkstra2D preparationForDijkstra2D) {
        for (int counter = 0; counter < finishArrayOfPathWays.length; counter++) {
            if (finishArrayOfPathWays[counter] == -1) {
                finishArrayOfPathWays[counter] = endPoint;
                break;
            }
        }
        String result = null;
        result = "(";
        int counter = 0;
        do {
            result += preparationForDijkstra2D.findPoint(finishArrayOfPathWays[counter]) + ") (";
            counter++;
        } while (finishArrayOfPathWays[counter] != endPoint);
        findPathLenth();
        result += preparationForDijkstra2D.findPoint(finishArrayOfPathWays[counter]) + "): " + pathLength;
//        System.out.print(result);
        return result;
    }

    public String answer3D(PreparationForDijkstra3D preparationForDijkstra3D) {
        for (int counter = 0; counter < finishArrayOfPathWays.length; counter++) {
            if (finishArrayOfPathWays[counter] == -1) {
                finishArrayOfPathWays[counter] = endPoint;
                break;
            }
        }
        String result = null;
        result = "(";
        int counter = 0;
        do {
            result += preparationForDijkstra3D.toPoint(finishArrayOfPathWays[counter]) + ") (";
            counter++;
        } while (finishArrayOfPathWays[counter] != endPoint);
        findPathLenth();
        result += preparationForDijkstra3D.toPoint(finishArrayOfPathWays[counter]) + "): " + pathLength;
//        System.out.print(result);
        return result;
    }

    public void findPathLenth() {
        for (int counter = 0; counter < finishArrayOfPathWays.length; counter++) {
            if (finishArrayOfPathWays[counter] != endPoint) {
                if (counter != 0) {
                    pathLength += matrixOfShortestArcs[finishArrayOfPathWays[counter - 1]][finishArrayOfPathWays[counter]];
                }
            } else {
                pathLength += matrixOfShortestArcs[finishArrayOfPathWays[counter - 1]][finishArrayOfPathWays[counter]];
                break;
            }
        }
    }

    public static void main(String[] args) {
//        PreparationForDijkstra2D preparation = new PreparationForDijkstra2D();
//                AntAlgoritm antAlgoritm2D=new AntAlgoritm(preparationForDijkstra2D.getLengthSide(),
//         preparationForDijkstra2D.getStartPoint(),preparationForDijkstra2D.getEndPoint(),
//         preparationForDijkstra2D.getMatrixOfShortestArcs());
//        antAlgoritm2D.start();
//        AntAlgoritm antAlgoritm3D=new AntAlgoritm(preparationForDijkstra3D.getNumberOfBalls(),preparationForDijkstra3D.getStartingPoin(),preparationForDijkstra3D.getEndPoint(),preparationForDijkstra3D.getMatrixOfShortestArcs());;
//        antAlgoritm3D.start();
//        antAlgoritm3D.answer3D(preparationForDijkstra3D);
//        System.out.println("\n Path id 3D - Ant algoritm");
//        my.answer2D(preparationForDijkstra2D);
    }
}

