package comp1110.ass2.tiles;

import java.util.HashMap;

/**
 * A class containing information about tile "cccc".
 * @author LanXue
 * @date 2020-03-24 22:05
 */
public class Tcccc implements TileInterface {

    public int totalNumber;

    public Tcccc() {
        totalNumber = 2;
    }

    @Override
    public HashMap<String, Integer> nextTile(int exitIndex, int currentX, int currentY) {
        int nextX = 0;
        int nextY = 0;
        int nextExit = 0;
        if (exitIndex == 0) {
            nextX = currentX - 1;
            nextY = currentY;
            nextExit = 2;
        } else if (exitIndex == 5) {
            nextX = currentX - 1;
            nextY = currentY;
            nextExit = 3;
        } else if (exitIndex == 1) {
            nextX = currentX + 1;
            nextY = currentY;
            nextExit = 7;
        } else if (exitIndex == 4) {
            nextX = currentX + 1;
            nextY = currentY;
            nextExit = 6;
        } else if (exitIndex == 3) {
            nextX = currentX;
            nextY = currentY + 1;
            nextExit = 1;
        } else if (exitIndex == 6) {
            nextX = currentX;
            nextY = currentY + 1;
            nextExit = 0;
        } else if (exitIndex == 2) {
            nextX = currentX;
            nextY = currentY - 1;
            nextExit = 4;
        } else if (exitIndex == 7) {
            nextX = currentX;
            nextY = currentY - 1;
            nextExit = 5;
        }
        HashMap<String, Integer> nextTile = new HashMap<>();
        nextTile.put("y", nextY);
        nextTile.put("x", nextX);
        nextTile.put("exitIndex", nextExit);
        return nextTile;
    }
}
