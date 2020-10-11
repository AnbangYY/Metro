package comp1110.ass2;

import comp1110.ass2.tiles.TileInterface;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * A class that provides a basic method to ensure that the game runs
 * according to the rules.
 * @author the group
 */
public class Metro {
    /**
     * Task 2
     * Determine whether a piece placement is well-formed. For a piece
     * placement to be well-formed, it must:
     * - contain exactly six characters;
     * - have as its first, second, third and fourth characters letters between
     * 'a' and 'd' inclusive (tracks); and
     * - have as its fifth and sixth characters digits between 0 and 7 inclusive
     * (column and row respectively).
     *
     * @param piecePlacement A String representing the piece to be placed
     * @return True if this string is well-formed
     */
    public static boolean isPiecePlacementWellFormed(String piecePlacement) {
        String type = piecePlacement.substring(0,4);
        String position = piecePlacement.substring(4);
        // Checks the length of the tile
        if (piecePlacement.length() != 6) {
            return false;
        }
        // Checks the first four character
        for (int i = 0; i < type.length(); i++) {
            String character = String.valueOf(type.charAt(i));
            if (!"a".equals(character) && !"b".equals(character) && !"c".equals(character) && !"d".equals(character)) {
                return false;
            }
        }
        // Checks the last two digits
        String limitedDigits = "01234567";
        if (!limitedDigits.contains(position.substring(0, 1)) || !limitedDigits.contains(position.substring(1))) {
            return false;
        }
        return true;
    }

    /**
     * Task 3
     * Determine whether a placement sequence string is well-formed.
     * For a placement sequence to be well-formed, it must satisfy the
     * following criteria:
     * - It must be composed of well-formed tile placements.
     * - For any piece x, there can exist no more instances of x on the board
     * than instances of x in the deck.
     *
     * @param placement A String representing the placement of all tiles on the
     *                  board
     * @return true if this placement sequence is well-formed
     */
    public static boolean isPlacementSequenceWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement sequence is well-formed
        if (placement.length() % 6 == 0) {
            // Splits each tile placement from the given string and test whether it's well-formed
            for (int i = 0; i < placement.length(); i+=6) {
                String tilePlacement = placement.substring(i, i+6);
                if (! isPiecePlacementWellFormed(tilePlacement)) {
                    return false;
                }
            }
            // Checks the number of the current tiles
            // Generates a tiles string to store all tiles showed in the sequence as well as the hands
            Board board = new Board(placement);
            String totalTiles = board.getAllTiles();
            return !TileInterface.exceedMaxNumber(totalTiles);
        } else {
            return false;
        }
    }

    /**
     * Task 5
     * Draw a random tile from the deck.
     *
     * @param placementSequence a String representing the sequence of tiles
     *                          that have already been played
     * @param totalHands        a String representing all tiles (if any) in
     *                          all players' hands
     * @return a random tile from the deck
     */
    public static String drawFromDeck(String placementSequence, String totalHands) {
        // FIXME Task 5: draw a random tile from the deck
        // Generates a tiles string to store all tiles showed in the sequence as well as the hands
        Board board = new Board(placementSequence);
        String allTiles = board.getAllTiles() + totalHands;
        // Generates a list to store the all types of tiles
        HashMap<String, Integer> tilesMap = TileInterface.setTilesMap();
        ArrayList<String> tileList =  new ArrayList<>();
        tileList.addAll(tilesMap.keySet());
        // Chooses tile randomly from the tile list until it satisfies the number limitation
        Random generator = new Random();
        int randomNumber = generator.nextInt(tileList.size());
        String randomTile = tileList.get(randomNumber);
        while (TileInterface.exceedMaxNumber(allTiles + randomTile)) {
            randomNumber = generator.nextInt(tileList.size());
            randomTile = tileList.get(randomNumber);
        }
        return randomTile;
    }

    /**
     * Task 6
     * Determine if a given placement sequence follows the rules of the game.
     * These rules are:
     * - No tile can overlap another tile, or any of the central stations.
     * - A tile cannot be placed next to one of the central squares unless it
     * continues or completes an existing track.
     * - If a tile is on an edge of the board, it cannot contain a track that
     * results in a station looping back to itself, UNLESS that tile could not
     * have been placed elsewhere.
     * - If a tile is on a corner of the board, it cannot contain a track that
     * links the two stations adjacent to that corner, UNLESS that tile could
     * not have been placed elsewhere.
     *
     * @param placementSequence A sequence of placements on the board.
     * @return Whether this placement string is valid.
     */
    public static boolean isPlacementSequenceValid(String placementSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        //Create an empty hashmap
        Map<String,String> map = new LinkedHashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((i==3 && (j==3 || j==4)) || (i==4 && (j==3||j==4)))) {
                    map.put((i + "") + j, null);
                }
            }
        }
        //check if there is overlapped tile
        int count = 1;
        for (int i = 0; i < placementSequence.length()/6; i++) {
            String tile = placementSequence.substring(i*6, i*6+4);
            String position;
            if (i*6+6 < placementSequence.length()) {
                position = placementSequence.substring(i*6 + 4, i*6 + 6);
            } else {
                position = placementSequence.substring(i*6+4);
            }
            if (count != 1 || !tile.equals("dddd")) {
                if (map.get(position) == null && map.containsKey(position)) {
                    map.put(position, tile);
                } else {
                    return false;
                }
                // check if a tile is placed beside central stations while not completing a track
                int y = Integer.parseInt(position.substring(0,1));
                int x = Integer.parseInt(position.substring(1));
                int[] aroundCentralXY = new int[]{2,3,4,5};
                String[] nPositions = new String[]{"22","25","52","55"};
                List<String> sidePositions = new ArrayList<>();
                for(int q = 0; q<8; q++ ){
                    String r = q+""+7;
                    sidePositions.add(r);
                    String l = q+""+0;
                    sidePositions.add(l);
                }
                for(int p = 1; p<7; p++ ){
                    String u = 0+""+p;
                    sidePositions.add(u);
                    String d = 7+""+p;
                    sidePositions.add(d);
                }

                if (!(y == 0 || y == 7 || x == 0 || x == 7)) {
                    if (map.get((y + "") + (x-1)) == null && map.get((y+"") + (x+1)) == null && map.get((y-1) + (x+"")) == null && map.get((y+1) + (x+"")) == null) {
                                return false;
                            }
                        }
                if(Arrays.asList(aroundCentralXY).contains(x) && Arrays.asList(aroundCentralXY).contains(y) && !Arrays.asList(nPositions).contains(position)){
                    if(map.get((x+1)+""+(y))==null || map.get((x)+""+(y+1))==null || map.get((x-1)+""+(y))==null || map.get((x)+""+(y-1))==null){
                return false;
                    }
                }
                // Check if the tiles at edges are valid
                boolean isOnlyEdgesLeft = true;
                for(String key : map.keySet()){
                    if (map.get(key) == null && !sidePositions.contains(key)) {
                        isOnlyEdgesLeft = false;
                    }
                }
                if(!isOnlyEdgesLeft){
                        if(position.charAt(0)=='0'){
                            if(tile.charAt(0)=='d'){
                                return false;
                            }
                        }
                        if(position.charAt(0)=='7'){
                            if(tile.charAt(2)=='d'){
                                return false;
                            }
                        }
                        if(position.charAt(1)=='0'){
                            if(tile.charAt(3)=='d'){
                                return false;
                            }
                        }
                        if(position.charAt(1)=='7'){
                            if(tile.charAt(1)=='d'){
                                return false;
                            }
                        }
                       if (position.equals("00") ) {
                            if (tile.charAt(0) == 'c' || tile.charAt(3) == 'b') {
                                return false;
                            }
                       }
                       if (position.equals("07") ) {
                           if (tile.charAt(0) == 'b' || tile.charAt(1) == 'c') {
                                return false;
                           }
                      }
                       if (position.equals("70") ) {
                           if (tile.charAt(2) == 'b' || tile.charAt(3) == 'c') {
                                return false;
                           }
                      }
                       if (position.equals("77")) {
                           if (tile.charAt(1) == 'b' || tile.charAt(2) == 'c') {
                                return false;
                        }
                    }
                }
            }
                    count++;
        }
        return true;
    }

    /**
     * Task 7
     * Determine the current score for the game.
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param numberOfPlayers   The number of players in the game
     * @return an array containing the scores for all players
     */
    public static int[] getScore(String placementSequence, int numberOfPlayers) {
        // FIXME Task 7: determine the current score for the game
        int totalPoints = 0;
        int[] scores = new int[numberOfPlayers];
        int index = 0;
        Station stations = new Station(numberOfPlayers);
        HashMap<Integer, int[]> stationMap = stations.getPlayersStations();
        for (int player : stationMap.keySet()) {
            int[] stationList = stationMap.get(player);
            for (int station : stationList) {
                int point = 0;
                try {
                    point = Score.getPoints(station, placementSequence);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                totalPoints += point;
            }
            scores[index] = totalPoints;
            totalPoints = 0;
            index++;
        }
        return scores;
    }

    /**
     * Task 9
     * Given a placement sequence string, generate a valid next move.
     *
     * @param placementSequence a String representing the sequence of piece placements made so far in the game
     * @param piece             a four-character String representing the tile to be placed
     * @param numberOfPlayers   The number of players in the game
     * @return A valid placement of the given tile
     */
    public static String generateMove(String placementSequence, String piece, int numberOfPlayers) {
        // FIXME Task 9: generate a valid move
        Board board = new Board(placementSequence);
        ArrayList<String> restPositions = board.getRestPosition();
        Random generator = new Random();
        String placement;
        while (true) {
            int randomIndex = generator.nextInt(restPositions.size());
            String randomPosition = restPositions.get(randomIndex);
            if (isPlacementSequenceValid(placementSequence + piece + randomPosition)) {
                placement = piece + randomPosition;
                break;
            }
        }
        return placement;
    }
}
