package comp1110.ass2.tiles;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * An interface to provide information about tiles.
 * @author xuelan
 */
public interface TileInterface {

    /**
     * Generates a map for tiles, key is the tile's content and value is the total number of that tile.
     * @return the tile map
     */
    static HashMap<String, Integer> setTilesMap() {
        HashMap<String, Integer> tilesMap = new HashMap<>();
        tilesMap.put("aacb", 4);
        tilesMap.put("cbaa", 4);
        tilesMap.put("acba", 4);
        tilesMap.put("baac", 4);
        tilesMap.put("aaaa", 4);
        tilesMap.put("cbcb", 3);
        tilesMap.put("bcbc", 3);
        tilesMap.put("cccc", 2);
        tilesMap.put("bbbb", 2);
        tilesMap.put("dacc", 2);
        tilesMap.put("cdac", 2);
        tilesMap.put("ccda", 2);
        tilesMap.put("accd", 2);
        tilesMap.put("dbba", 2);
        tilesMap.put("adbb", 2);
        tilesMap.put("badb", 2);
        tilesMap.put("bbad", 2);
        tilesMap.put("ddbc", 2);
        tilesMap.put("cddb", 2);
        tilesMap.put("bcdd", 2);
        tilesMap.put("dbcd", 2);
        tilesMap.put("adad", 2);
        tilesMap.put("dada", 2);
        tilesMap.put("dddd", 2);
        return tilesMap;
    }

    /**
     * Gets the information of the next connected tile including the position and the
     * exit index that the track will start in the next connected tile.
     * @param exitIndex the exit index that the track starts
     * @param currentX the coordinate of x of the tile's current location
     * @param currentY the coordinate of y of the tile's current location
     * @return the map containing position as well as the exit index of the next connected tile
     */
    HashMap<String, Integer> nextTile(int exitIndex, int currentX, int currentY);

    /**
     * Check whether the number of each tile exceeds its total number.
     * @param totalTiles A String representing the all tiles on the board and hands
     * @return True if there is a tile's number exceeds its total number
     */
    static boolean exceedMaxNumber(String totalTiles) {
        ArrayList<String> tiles = new ArrayList<>();
        // Splits each tile placement from the given string
        for (int i = 0; i < totalTiles.length(); i+=4) {
            String tile;
            if (i+4 <= totalTiles.length()) {
                tile = totalTiles.substring(i, i+4);
            } else {
                tile = totalTiles.substring(i);
            }
            tiles.add(tile);
        }
        // Generates a map for tiles with the number as the value
        HashMap<String, Integer> tilesCountMap = new HashMap<>();
        for (String tile : tiles) {
            if (tilesCountMap.containsKey(tile)) {
                tilesCountMap.put(tile, tilesCountMap.get(tile)+1);
            } else {
                tilesCountMap.put(tile, 1);
            }
        }
        // Check the if the number of the tile exceeds the maximum number
        HashMap<String, Integer> tilesMap = TileInterface.setTilesMap();
        for (String key : tilesCountMap.keySet()) {
            int count = tilesCountMap.get(key);
            if (count > tilesMap.get(key)) {
                return true;
            }
        }
        return false;
    }

}
