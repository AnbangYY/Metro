package comp1110.ass2;

import java.util.Arrays;
import java.util.HashMap;

/**
 * This class will have several methods to help give the different lists of stations
 * depends on the number of players. Besides, it will also offer methods to check
 * whether the tile connects to the station or the centre station.
 * @author LanXue
 * @date 2020-03-25 15:20
 */
public class Station {

    public static final Integer[] UPPER = {1, 2, 3, 4, 5, 6, 7, 8};
    public static final Integer[] BELOW = {17, 18, 19, 20, 21, 22, 23, 24};
    public static final Integer[] LEFT = {9, 10, 11, 12, 13, 14, 15, 16};
    public static final Integer[] RIGHT = {25, 26, 27, 28, 29, 30, 31, 32};
    public int numberOfPlayers;

    public Station() {
    }

    public Station(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Generates different station map based on the number of the players,
     * the key is the player index, value is the stations that belong to him.
     * @return the map
     */
    public HashMap<Integer, int[]> getPlayersStations() {
        HashMap<Integer, int[]> stationMap = new HashMap<>();
        switch (numberOfPlayers) {
            case 1:
                stationMap.put(1, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9 , 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32});
                break;
            case 2:
                stationMap.put(1, new int[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31});
                stationMap.put(2, new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32});
                break;
            case 3:
                stationMap.put(1, new int[]{1, 4, 6, 11, 15, 20, 23, 25, 28, 31});
                stationMap.put(2, new int[]{2, 7, 9, 12, 14, 19, 22, 27, 29, 32});
                stationMap.put(3, new int[]{ 3, 5, 8, 10, 13, 18, 21, 24, 26, 30});
                break;
            case 4:
                stationMap.put(1, new int[]{4, 7, 11, 16, 20, 23, 27, 32});
                stationMap.put(2, new int[]{3, 8, 12, 15, 19, 24, 28, 31});
                stationMap.put(3, new int[]{1, 6, 10, 13, 18, 21, 25, 30});
                stationMap.put(4, new int[]{2, 5,  9, 14, 17, 22, 26, 29});
                break;
            case 5:
                stationMap.put(1, new int[]{1, 5, 10, 14, 22, 28});
                stationMap.put(2, new int[]{6, 12, 18, 23, 27, 32});
                stationMap.put(3, new int[]{3, 7, 15, 19, 25, 29});
                stationMap.put(4, new int[]{2, 9, 13, 21, 26, 30});
                stationMap.put(5, new int[]{4, 8, 11, 20, 24, 31});
                break;
            case 6:
                stationMap.put(1, new int[]{1, 5, 10, 19, 27});
                stationMap.put(2, new int[]{2, 11, 18, 25, 29});
                stationMap.put(3, new int[]{4, 8, 14, 21, 26});
                stationMap.put(4, new int[]{6, 15, 20, 24, 31});
                stationMap.put(5, new int[]{3, 9, 13, 23, 30});
                stationMap.put(6, new int[]{7, 12, 22, 28, 32});
                break;
            default:
                break;
        }
        return stationMap;
    }

    /**
     * Checks whether the track on the current position connects the station.
     * @param currentX the coordinate of x of the current position
     * @param currentY the coordinate of y of the current position
     * @param exitIndex the exit index that the track starts
     * @return true if the track arrives at station, false otherwise
     */
    public static boolean isArriveStation(int currentX, int currentY, int exitIndex) {
        if (currentY == -1 && exitIndex == 4) {
            return true;
        } else if (currentX == -1 && exitIndex == 2) {
            return true;
        } else if (currentY == 8 && exitIndex == 0) {
            return true;
        } else if (currentX == 8 && exitIndex == 6) {
            return  true;
        } else if (isArriveCentreStation(currentX, currentY,exitIndex)) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the track on the current position connects the centre station.
     * @param currentX the coordinate of x of the current position
     * @param currentY the coordinate of y of the current position
     * @param exitIndex the exit index that the track starts
     * @return true if the track arrives at centre station, false otherwise
     */
    public static boolean isArriveCentreStation(int currentX, int currentY, int exitIndex) {
        if (currentX == 3 && currentY == 3 &&
                (exitIndex == 0 || exitIndex == 1 || exitIndex == 6 || exitIndex == 7)) {
            return true;
        } else if (currentX == 4 && currentY == 3 &&
                (exitIndex == 0 || exitIndex == 1 || exitIndex == 2 || exitIndex == 3)) {
            return true;
        } else if (currentX == 3 && currentY == 4 &&
                (exitIndex == 4 || exitIndex == 5 || exitIndex == 6 || exitIndex == 7)) {
            return true;
        } else if (currentX == 4 && currentY == 4 &&
                (exitIndex == 2 || exitIndex == 3 || exitIndex == 4 || exitIndex == 5)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the expected position and exit index of the tile that connects with the given station.
     * @param stationPosition the given station
     * @return the information of the tile
     */
    public static HashMap<String, Integer> getConnectedTile(int stationPosition) {
        // Gets the exit index and the position of the tile connected to the given station
        int exitIndex = 0;
        int x = 0;
        int y = 0;
        if (Arrays.asList(UPPER).contains(stationPosition)) {
            exitIndex = 0;
            y = 0;
            x = 8 - stationPosition;
        } else if (Arrays.asList(LEFT).contains(stationPosition)) {
            exitIndex = 6;
            x = 0;
            y = stationPosition - 9;
        } else if (Arrays.asList(BELOW).contains(stationPosition)) {
            exitIndex = 4;
            y = 7;
            x = stationPosition - 17;
        } else if (Arrays.asList(RIGHT).contains(stationPosition)) {
            exitIndex = 2;
            x = 7;
            y = 32 - stationPosition;
        }
        HashMap<String, Integer> connectedTile = new HashMap<>();
        connectedTile.put("y", y);
        connectedTile.put("x", x);
        connectedTile.put("exitIndex", exitIndex);
        return connectedTile;
    }
}
