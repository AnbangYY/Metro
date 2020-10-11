package comp1110.ass2.tiles;

import java.util.HashMap;

/**
 * A class containing information about tile "adad".
 * @author LanXue
 * @date 2020-03-24 22:05
 */
public class Tadad implements TileInterface {

    public int totalNumber;

    public Tadad() {
        totalNumber = 2;
    }

    @Override
    public HashMap<String, Integer> nextTile(int exitIndex, int currentX, int currentY) {
        int nextX = 0;
        int nextY = 0;
        int nextExit = 0;
        if (exitIndex == 6 || exitIndex == 7) {
            nextX = currentX - 1;
            nextY = currentY;
            nextExit = exitIndex - 4;
        } else if (exitIndex == 2 || exitIndex == 3) {
            nextX = currentX + 1;
            nextY = currentY;
            nextExit = exitIndex + 4;
        } else if (exitIndex == 0 || exitIndex == 1) {
            nextX = currentX;
            nextY = currentY + 1;
            nextExit = exitIndex;
        } else {
            nextX = currentX;
            nextY = currentY - 1;
            nextExit = exitIndex;
        }
        HashMap<String, Integer> nextTile = new HashMap<>();
        nextTile.put("y", nextY);
        nextTile.put("x", nextX);
        nextTile.put("exitIndex", nextExit);
        return nextTile;
    }
}
