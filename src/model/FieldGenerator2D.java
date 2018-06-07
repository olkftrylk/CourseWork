package model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FieldGenerator2D {

    public List<String> walls = new ArrayList<>();
    public List<String> rooms = new ArrayList<>();

    private int numberOfRooms;
    private int numberOfWalls;
    private int lengthSide;
    private int minWeight;
    private int maxWeight;
//    private int lengthOfSide;

    public FieldGenerator2D(int n, int minWeight, int maxWeight) {
        this.lengthSide = n;
        numberOfWalls = 9 * n * n - 15 * n + 6;
        numberOfRooms = 3 * n * n - 3 * n + 1;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    public List<String> generateBuilding() {
        walls = new ArrayList<>();

        int currentRoomX;
        int currentRoomY;
        for (int i = 0; i < lengthSide; i++) {
            currentRoomX = i;
            currentRoomY = 0;
            while (isExist(currentRoomX + 1, currentRoomY + 1)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + (currentRoomX + 1) + ", " + (currentRoomY + 1) + "): "
                        + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1));
                currentRoomX++;
                currentRoomY++;
            }
        }

        for (int i = 1; i < lengthSide; i++) {
            currentRoomX = 0;
            currentRoomY = i;
            while (isExist(currentRoomX + 1, currentRoomY + 1)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + (currentRoomX + 1) + ", " + (currentRoomY + 1) + "): "
                        + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1));
                currentRoomX++;
                currentRoomY++;
            }
        }


        for (int i = 0; i < lengthSide; i++) {
            currentRoomX = 0;
            currentRoomY = i;
            while (isExist(currentRoomX + 1, currentRoomY)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + (currentRoomX + 1) + ", " + (currentRoomY) + "): "
                        + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1));
                currentRoomX++;
            }
        }


        for (int i = 1; i < lengthSide; i++) {
            currentRoomX = i;
            currentRoomY = i + lengthSide - 1;
            while (isExist(currentRoomX + 1, currentRoomY)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + (currentRoomX + 1) + ", " + (currentRoomY) + "): "
                        + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1));
                currentRoomX++;
            }
        }

        for (int i = 0; i < lengthSide; i++) {
            currentRoomX = i;
            currentRoomY = 0;
            while (isExist(currentRoomX, currentRoomY + 1)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + currentRoomX + ", " + (currentRoomY + 1) + "): "
                                + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1));
                currentRoomY++;
            }
        }


        for (int i = 1; i < lengthSide; i++) {
            currentRoomX = i + lengthSide - 1;
            currentRoomY = i;
            while (isExist(currentRoomX, currentRoomY + 1)) {
                walls.add("(" + currentRoomX + ", " + currentRoomY + ") (" + currentRoomX + ", " + (currentRoomY + 1) + "): "
                        + ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1) );
                currentRoomY++;
            }
        }


        rooms = new ArrayList<>();
        return walls;
    }


    private boolean isNotVisited(int x, int y) {
        for (String wall : walls) {
            if (x == Integer.parseInt(wall.substring(1, wall.indexOf(",")))
                    && (y == Integer.parseInt(wall.substring(wall.indexOf(" ") + 1, wall.indexOf(")"))))) {
                return false;
            }
        }
        return true;
    }

    public boolean isExist(int x, int y) {
        return (Math.abs(x - y) <= lengthSide - 1) &&
                (x < 2 * lengthSide - 1) &&
                (y < 2 * lengthSide - 1) &&
                (x >= 0) &&
                (y >= 0);
    }

    private void generateNextLevelWalls(int x, int y) {
        if (isNotAddedToRooms(x, y)) {
            rooms.add("(" + x + ", " + y + ")");
        }
        if (isExist(x + 1, y)) {
            int firstNeighbourWallWeight = ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1);
            walls.add("(" + x + ", " + y + ") (" + (x + 1) + ", " + y + "): " + firstNeighbourWallWeight);
            if (isNotVisited(x + 1, y)) {
                generateNextLevelWalls(x + 1, y);
            }
        }
        if (isExist(x, y + 1)) {
            int secondNeighbourWallWeight = ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1);
            walls.add("(" + x + ", " + y + ") (" + x + ", " + (y + 1) + "): " + secondNeighbourWallWeight);
            if (isNotVisited(x, y + 1)) {
                generateNextLevelWalls(x, y + 1);
            }
        }

        if (isExist(x + 1, y + 1)) {
            int thirdNeighbourWallWeight = ThreadLocalRandom.current().nextInt(minWeight, maxWeight + 1);
            walls.add("(" + x + ", " + y + ") (" + (x + 1) + ", " + (y + 1) + "): " + thirdNeighbourWallWeight);
            if (isNotVisited(x + 1, y + 1)) {
                generateNextLevelWalls(x + 1, y + 1);
            }
        }
    }

    private boolean isNotAddedToRooms(int x, int y) {
        for (String room : rooms) {
            if (x == Integer.parseInt(room.substring(1, room.indexOf(",")))
                    && (y == Integer.parseInt(room.substring(room.indexOf(" ") + 1, room.indexOf(")"))))) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        FieldGenerator2D fieldGenerator = new FieldGenerator2D(100, 1, 10);
        int counter = 0;
        for (String s : fieldGenerator.generateBuilding()) {
            System.out.println(s);
            counter++;
        }
        System.out.println(counter);
        for (String room : fieldGenerator.getRooms()) {
            System.out.println(room);
        }
    }

    public List<String> getRooms() {
        return rooms;
    }

    public List<String> getWalls() {
        return walls;
    }

    public int getLengthSide() {
        return lengthSide;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }
}
