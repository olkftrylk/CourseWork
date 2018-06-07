package algorithm;

public class AlgorithmCell extends Cell {

    private long numberOfWays;

    public AlgorithmCell(int x, int y) {
        super(x, y);
    }


    public long getNumberOfWays() {
        return numberOfWays;
    }

    public void setNumberOfWays(long numberOfWays) {
        this.numberOfWays = numberOfWays;
    }
}
