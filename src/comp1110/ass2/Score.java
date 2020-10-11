package comp1110.ass2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * This class will help compute the points of one tract.
 * @author LanXue
 * @date 2020-03-25 15:38
 */
public class Score {

    /**
     * Computes the total points of the one track that starts from the given station
     * and ends at any station.
     * @param stationPosition the given station index
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @return the total points on that station
     */
    public static int getPoints(int stationPosition, String placementSequence) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int totalPoints = 0;
        // Gets the information of the tile that connects with the given station
        HashMap<String, Integer> connectedTile = Station.getConnectedTile(stationPosition);
        int exitIndex = connectedTile.get("exitIndex");
        int currentX = connectedTile.get("x");
        int currentY = connectedTile.get("y");
        // Follows a track and add the point each time when it goes to the next tile
        // until it arrives at a station
        while (! Station.isArriveStation(currentX, currentY, exitIndex)) {
            // Gets the position that connects with the given station
            String currPosition = (currentY + "") + (currentX + "");
            int currPotionIndex = placementSequence.indexOf(currPosition);
            // Check whether the given placement contains the tile connected with the station
            if (currPotionIndex == -1) {
                totalPoints = 0;
                break;
            }
            // Gets the tile in the position that connects with the given station
            String currTile = placementSequence.substring(currPotionIndex - 4, currPotionIndex);
            // Gets the information of the next connected tile with Reflection
            Class CurrTile = Class.forName("comp1110.ass2.tiles." + "T" + currTile);
            Method next = CurrTile.getMethod("nextTile", int.class, int.class, int.class);
            Object nextTile = next.invoke(CurrTile.getDeclaredConstructor().newInstance(), exitIndex, currentX, currentY);
            currentX = (int)((HashMap)nextTile).get("x");
            currentY = (int)((HashMap)nextTile).get("y");
            exitIndex = (int)((HashMap)nextTile).get("exitIndex");
            totalPoints++;
        }
        // Check if the point needs to be double due to the centre station
        if (Station.isArriveCentreStation(currentX, currentY, exitIndex)) {
            totalPoints = 2 * totalPoints;
        }
        return totalPoints;
    }
}
