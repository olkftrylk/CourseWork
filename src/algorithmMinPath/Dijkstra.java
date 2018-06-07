package algorithmMinPath;

import model.FieldGenerator3D;

public class Dijkstra {

    private int[][] matrixOfShortestArcs;
    private int lengthSide;
    private int startPoint;
    private int endPoint;
    private int[] auxiliaryArray;

    public int[] getAuxiliaryArray() {
        return auxiliaryArray;
    }

    private int[] result;
    private int numberOfTheTop;
    private String arrayOfAnswersFor2D;
    private String arrayOfAnswersFor3D;
    public String  fillStringOfAnswer2D (PreparationForDijkstra2D preparationForDijkstra2D,int[] arrayOfAuxiliary){
        int ifFindStartPoint=0;
        for (int n = matrixOfShortestArcs.length-1; n >=0; n--) {
            if (ifFindStartPoint==1){
                arrayOfAnswersFor2D+='('+preparationForDijkstra2D.findPoint(result[n])+")";
            }
            if (result[n]==startPoint) {
                ifFindStartPoint = 1;
                arrayOfAnswersFor2D='('+preparationForDijkstra2D.findPoint(startPoint)+")";
            }
        }
        arrayOfAnswersFor2D+=": "+arrayOfAuxiliary[endPoint];
        return arrayOfAnswersFor2D;
    }
    public Dijkstra(int startPoint, int endPoint, int numberOfTheTop, int n, int[][] matrixOfShortestArcs) {
        this.matrixOfShortestArcs=matrixOfShortestArcs;
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.numberOfTheTop=numberOfTheTop;
        lengthSide = n;
    }
    public void findPath(int[] arrayOfAuxiliary){
        int[] arrayOfVisit=new int[matrixOfShortestArcs.length];
         result=new int[matrixOfShortestArcs.length];
        for (int counter = 0; counter < matrixOfShortestArcs.length; counter++) {
            result[counter]=-1;
        }
        int start_Point=endPoint;
        result[0]=start_Point;
        arrayOfVisit[endPoint]=1;
        int helpCounter=1;

        do {
            int answer=-1;

            for (int counter = 0; counter < matrixOfShortestArcs.length; counter++) {
                int minAuxiary=Integer.MAX_VALUE;
                if ((matrixOfShortestArcs[start_Point][counter]!=Integer.MAX_VALUE)&&(arrayOfVisit[counter]!=1)){
                    if (minAuxiary>arrayOfAuxiliary[counter]){
                        minAuxiary=arrayOfAuxiliary[counter];
                        if (minAuxiary+matrixOfShortestArcs[start_Point][counter]==arrayOfAuxiliary[start_Point]){
                            result[helpCounter]=counter;
                            start_Point=counter;
                            arrayOfVisit[counter]=1;
                            helpCounter++;
                            answer=1;
                            break;
                        }
                    }
                }
            }
        }while (start_Point!=startPoint);
    }

    public void fillStringOfAnswer3D (PreparationForDijkstra3D preparationForDijkstra3D, int[] arrayOfAuxiliary){
        int ifFindStartPoint=0;
        for (int n = matrixOfShortestArcs.length-1; n >=0; n--) {
            if (ifFindStartPoint==1){
                arrayOfAnswersFor3D+='('+preparationForDijkstra3D.toPoint(result[n])+")";
            }
            if (result[n]==startPoint) {
                ifFindStartPoint = 1;
                arrayOfAnswersFor3D='('+preparationForDijkstra3D.toPoint(startPoint)+")";
            }
        }
        arrayOfAnswersFor3D+=": "+arrayOfAuxiliary[endPoint];
        return;
    }
    public int shortestPathAlgorithm(int maxElement) {
        auxiliaryArray = new int[maxElement];
        int[] ofVisit = new int[maxElement + 1];
        for (int counter = 0; counter < auxiliaryArray.length; counter++) {
            auxiliaryArray[counter] = Integer.MAX_VALUE;
        }
        int minElementalArrayOfLabels = (int)(3*Math.pow(lengthSide,2) - 3*lengthSide +1);
        int indexOfminElementaArrayOfLabels=-1;
        for (int counter = 0; counter < auxiliaryArray.length; counter++) {
            auxiliaryArray = matrixOfShortestArcs[startPoint];
            ofVisit[startPoint] = 1;
            if ((auxiliaryArray[counter] < minElementalArrayOfLabels) && (auxiliaryArray[counter] != 0)) {
                minElementalArrayOfLabels=auxiliaryArray[counter];
                indexOfminElementaArrayOfLabels=counter;

            }
        }

        if (auxiliaryArray[endPoint] == Integer.MAX_VALUE) {
            int counterFor=0;
            int checkFinish;
            do {
                checkFinish=-1;
                for (int counter = 0; counter < auxiliaryArray.length; counter++) {
                    if (indexOfminElementaArrayOfLabels==-1){
                        break;
                    }
                    ofVisit[indexOfminElementaArrayOfLabels] = 1;
                    if ((auxiliaryArray[counter] > auxiliaryArray[indexOfminElementaArrayOfLabels] + matrixOfShortestArcs[indexOfminElementaArrayOfLabels][counter]) && (matrixOfShortestArcs[indexOfminElementaArrayOfLabels][counter] != Integer.MAX_VALUE) && (ofVisit[counter] != 1)&&(counter!=indexOfminElementaArrayOfLabels)) {
                        auxiliaryArray[counter] = auxiliaryArray[indexOfminElementaArrayOfLabels] + matrixOfShortestArcs[indexOfminElementaArrayOfLabels][counter];
                    }
                }
                minElementalArrayOfLabels = Integer.MAX_VALUE;
                for (int counter = 0; counter < auxiliaryArray.length; counter++) {
                    if ((auxiliaryArray[counter] < minElementalArrayOfLabels) && (auxiliaryArray[counter] != 0) && (ofVisit[counter] != 1)) {
                        minElementalArrayOfLabels=auxiliaryArray[counter];
                        indexOfminElementaArrayOfLabels=counter;
                        checkFinish=1;
                    }
                }
                counterFor++;
                } while ((counterFor != auxiliaryArray.length)&&((indexOfminElementaArrayOfLabels!=-1)||(checkFinish!=-1)));

        }
        findPath(auxiliaryArray);
        return 0;
    }
    public String answerForDijkstra2D(){
        return arrayOfAnswersFor2D;
    }
    public String answerForDijkstra3D(){
        return arrayOfAnswersFor3D;
    }

    public static void main(String[] args) {
        //////////////////////////////////    FOR 2D      //////////////////////////////////////////////////
//        FieldGenerator2D fieldGenerator = new FieldGenerator2D(86, 1, 3);
//        PreparationForDijkstra2D myObject=new PreparationForDijkstra2D(1,3,30,30, fieldGenerator.generateBuilding(),fieldGenerator.getLengthSide());
//        myObject.convertData();
        FieldGenerator3D fieldGeneratorFor3D = new FieldGenerator3D(30, 1, 10);
        PreparationForDijkstra3D preparationForDijkstra3D = new PreparationForDijkstra3D(0,0,1, 0, 12,10,fieldGeneratorFor3D.generateBuilding(),30);
        preparationForDijkstra3D.processOfFindingEdges();
        preparationForDijkstra3D.convertData();
//        AntAlgoritm my=new AntAlgoritm(myObject.getLengthSide(),myObject.getStartPoint(),myObject.getEndPoint(),myObject.getMatrixOfShortestArcs());
//        my.start();
        AntAlgoritm myFor3D=new AntAlgoritm(preparationForDijkstra3D.getNumberOfBalls(),preparationForDijkstra3D.getStartingPoin(),preparationForDijkstra3D.getEndPoint(),preparationForDijkstra3D.getMatrixOfShortestArcs());;
        myFor3D.start();
        myFor3D.answer3D(preparationForDijkstra3D);
//        System.out.println("\nPath id 2D - Ant algoritm");
//        my.(1,myObject,preparationForDijkstra3D);

//        System.out.println("\n Path id 3D - Ant algoritm");
//        my.answer2D(myObject);
//       Dijkstra dijkstraFor2D = new Dijkstra(myObject.getStartPoint(),myObject.getEndPoint(),myObject.getNumberOfTheTop(),myObject.getLengthSide(),myObject.getMatrixOfShortestArcs());
//       dijkstraFor2D.shortestPathAlgorithm(0,dijkstraFor2D.numberOfTheTop);
//       dijkstraFor2D.fillStringOfAnswer2D(myObject,dijkstraFor2D.auxiliaryArray);

////        //////////////////////////////////    FOR 3D      //////////////////////////////////////////////////
       Dijkstra dijkstraFor3D = new Dijkstra(preparationForDijkstra3D.getStartingPoin(),preparationForDijkstra3D.getEndPoint(),preparationForDijkstra3D.getNumberOfTheTop(),preparationForDijkstra3D.getNumberOfBalls(),preparationForDijkstra3D.getMatrixOfShortestArcs());
        dijkstraFor3D.shortestPathAlgorithm(preparationForDijkstra3D.getNumberOfTheTop());
        dijkstraFor3D.fillStringOfAnswer3D(preparationForDijkstra3D,dijkstraFor3D.auxiliaryArray);
////
//        System.out.println("\nPath id 2D - Dijkstra");
//        dijkstraFor2D.answerForDijkstra2D();
           System.out.println("\nPath id 3D - Dijkstra");
             dijkstraFor3D.answerForDijkstra3D();

    }

}

