package algorithm;

public class Algorithm2D {

    private HexagonAlgorithmField field;

    private int lengthSide;
    private int startY;
    private int startX;

    private boolean isExist(int x, int y) {
        return (Math.abs(x - y) <= lengthSide - 1) && (x < 2 * lengthSide - 1) && (y < 2 * lengthSide - 1);
    }

    public Algorithm2D(HexagonAlgorithmField field) {
        this.field = field;
        this.lengthSide = field.getLengthOfSide();
    }

    long calculateNumberWays(int x, int y) {

        AlgorithmCell target = field.getCell(x, y);

        AlgorithmCell topNeighbour =  field.getCell(x, y - 1);
        AlgorithmCell leftTopNeighbour =field.getCell(x - 1, y - 1);
        AlgorithmCell leftBottomNeighbour = field.getCell(x - 1, y);

        long result = 0;
        if (topNeighbour != null) {
            result += topNeighbour.getNumberOfWays();
        }
        if (leftTopNeighbour != null) {
            result += leftTopNeighbour.getNumberOfWays();
        }
        if (leftBottomNeighbour != null) {
            result += leftBottomNeighbour.getNumberOfWays();
        }
//        if(result > 9_9999_999 ||
//                (leftBottomNeighbour!=null && leftBottomNeighbour.getNumberOfWays() == 0 && leftBottomNeighbour.getX() >= startX
//                && leftBottomNeighbour.getY() >= startY ) ||
//                (leftTopNeighbour!=null && leftTopNeighbour.getNumberOfWays() == 0 && leftTopNeighbour.getX() >= startX
//                        && leftTopNeighbour.getY() >= startY) ||
//                (topNeighbour!=null && topNeighbour.getNumberOfWays() == 0  && topNeighbour.getX() >= startX
//                        && topNeighbour.getY() >= startY)){
//            target.setNumberOfWays(0);
//        }else{
            target.setNumberOfWays(result);
//        }
        return result;
    }

    public long calculateNumberWays(int startX, int startY, int endX, int endY) {

        field.getCell(startX, startY).setNumberOfWays(1);

        this.startX = startX;
        this.startY = startY;

        int numberOfLevels = Math.min((endX - startX), (endY - startY));

        for (int i = 0; i <= numberOfLevels; i++) {

            //bottom part
            int currentX = startX + i;
            int currentY = startY + i;

            while (isExist(currentX, currentY)) {
                if ((currentX > startX) || (currentY > startY))
                    calculateNumberWays(currentX, currentY);
                currentY++;
            }

            //top part
            currentX = startX + i;
            currentY = startY + i;

            while (isExist(currentX, currentY)) {
                if ((currentX > startX) || (currentY > startY))
                    calculateNumberWays(currentX, currentY);
                currentX++;
            }

        }
        return field.getCell(endX, endY).getNumberOfWays();

        /*int n = lengthSide;
        for (int i = 0; i < n; i++) {
            for (int depth = 0; depth < n; depth++) {
                if (!((i == 0) && (depth == 0))) {
                    calculateNumberWays(field, depth, i);
                    calculateNumberWays(field, -depth, i + depth);
                }
            }
        }

        for (int i = n; i < 2 * n - 1; i++) {
            for (int depth = 0; depth < 2 * n - 1 - i; depth++) {
                calculateNumberWays(field, depth, i);
                calculateNumberWays(field, -depth, i + depth);
            }
        }
        return ((AlgorithmCell) (field.getCell(0, 2 * n - 2))).getNumberOfWays();*/
    }

    public static void main(String[] args) {
        HexagonAlgorithmField hexagonAlgorithmField = new HexagonAlgorithmField(4);
        System.out.println(new Algorithm2D(hexagonAlgorithmField).calculateNumberWays(1, 1, 6, 6));
        System.out.println();
    }
}
