package algorithm;

public class Algorithm3D {

    private SphereAlgorithmField field;

    private int lengthSide;
    private int startX;
    private int startY;
    private int startZ;

    private boolean isExist(int x, int y, int z) {
        int row = lengthSide - 1 - y;
        if ((x >= 0) && (x < lengthSide) &&
                (row >= 0) && (row < lengthSide - x) &&
                (z >= 0) && (z < row + 1)) {
            return true;
        }
        return false;
    }

    public Algorithm3D(SphereAlgorithmField field) {
        this.field = field;
        this.lengthSide = field.getLengthOfSide();
    }

    int calculateNumberWays(int x, int y, int z) {

        AlgorithmCubicle target = field.getCell(x, y, z);

        AlgorithmCubicle xy_1z_1 = field.getCell(x, y - 1, z + 1);
        AlgorithmCubicle xyz_1 = field.getCell(x, y, z + 1);
        AlgorithmCubicle x_1y_1z_1 = field.getCell(x - 1, y - 1, z + 1);
        AlgorithmCubicle xy_1z = field.getCell(x, y - 1, z);
        AlgorithmCubicle x_1y_1z = field.getCell(x - 1, y - 1, z);
        AlgorithmCubicle x_1yz = field.getCell(x - 1, y, z);

        int result = 0;
        if (xy_1z_1 != null && xy_1z_1.getNumberOfWays() > -1) {
            result += xy_1z_1.getNumberOfWays();
        }

        if (xyz_1 != null && xyz_1.getNumberOfWays() > -1) {
            result += xyz_1.getNumberOfWays();
        }

        if (x_1y_1z_1 != null && x_1y_1z_1.getNumberOfWays() > -1) {
            result += x_1y_1z_1.getNumberOfWays();
        }

        if (xy_1z != null && xy_1z.getNumberOfWays() > -1) {
            result += xy_1z.getNumberOfWays();
        }

        if (x_1y_1z != null && x_1y_1z.getNumberOfWays() > -1) {
            result += x_1y_1z.getNumberOfWays();
        }

        if (x_1yz != null && x_1yz.getNumberOfWays() > -1) {
            result += x_1yz.getNumberOfWays();
        }

        if (result > 9_9999_999 ||
                (xy_1z_1 != null && xy_1z_1.getNumberOfWays() == 0 && isExist(x, y - 1, z + 1))
                || (xyz_1 != null && xyz_1.getNumberOfWays() == 0 && isExist(x, y, z + 1))
                || (x_1y_1z_1 != null && x_1y_1z_1.getNumberOfWays() == 0 && isExist(x - 1, y - 1, z + 1))
                || (xy_1z != null && xy_1z.getNumberOfWays() == 0 && isExist(x, y - 1, z))
                || (x_1y_1z != null && x_1y_1z.getNumberOfWays() == 0 && isExist(x - 1, y - 1, z))
                || (x_1yz != null && x_1yz.getNumberOfWays() == 0 && isExist(x - 1, y, z))) {
            target.setNumberOfWays(0);
        } else {
            target.setNumberOfWays(result);
        }
        return result;
    }

    public int calculateNumberWays(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        field.getCell(startX, startY, startZ).setNumberOfWays(1);

        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;

        for (int level = startZ; level >= endZ ; level--) {
            int currentRowBeginningX = startX;
            int currentRowBeginningY = startY;

            while(true){
                if (!isExist(currentRowBeginningX,currentRowBeginningY,level)){
                    currentRowBeginningY = currentRowBeginningX;
                }
                int currentY = currentRowBeginningY;
                while(isExist(currentRowBeginningX, currentY, level)){
                    if(!((currentRowBeginningX == startX) && (currentY == startY) &&(level == startZ)))
                        calculateNumberWays(currentRowBeginningX, currentY, level);
                    currentY++;
                }
                currentRowBeginningX++;
                if(currentRowBeginningX > lengthSide - level - 1){
                    break;
                }
//                currentRowBeginningY++;
            }
        }

        return field.getCell(endX, endY, endZ).getNumberOfWays();
    }

    public static void main(String[] args) {
        SphereAlgorithmField field = new SphereAlgorithmField(3);
        System.out.println(new Algorithm3D(field).calculateNumberWays(
                0, 1, 1,
                2, 2, 0));
        System.out.println();
    }

}
