package algorithm;

public class AlgorithmCubicle {

    private int x;
    private int y;
    private int z;
    private int numberOfWays;

    public AlgorithmCubicle(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AlgorithmCubicle(int x, int y, int z, int numberOfWays) {
        this(x,y,z);
        this.numberOfWays = numberOfWays;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getNumberOfWays() {
        return numberOfWays;
    }

    public void setNumberOfWays(int numberOfWays) {
        this.numberOfWays = numberOfWays;
    }
}
