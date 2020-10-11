package comp1110.ass2;
import java.util.ArrayList;

/**
 * A class that can represent the current state of the board and provide some
 * methods to get information of the board.
 * @author the group
 */
public class Board {

    public String placementSequence;

    public Board() {
    }

    public Board(String placementSequence) {
        this.placementSequence = placementSequence;
    }

    /**
     * Get the current placement sequence on the board.
     * @return he current placement sequence
     */
    public String getBoard() {
        return placementSequence;
    }

    /**
     * Set the board with the current placement sequence.
     * @param placementSequence the given placement sequence
     */
    public void setBoard(String placementSequence) {
        this.placementSequence = placementSequence;
    }

    /**
     * Get a string sequence for all tiles showed in the board.
     * @return the string containing all tiles
     */
    public String getAllTiles() {
        StringBuilder allTiles = new StringBuilder();
        for (int i = 0; i < placementSequence.length(); i+=6) {
            String tile = placementSequence.substring(i, i+4);
            allTiles.append(tile);
        }
        return allTiles.toString();
    }

    /**
     * Creates a ArrayList to store all available positions based on the current board.
     * @return a ArrayList containing all available positions
     */
    public ArrayList<String> getRestPosition() {
        ArrayList<String> restPositions = new ArrayList<>();
        boolean[][] used = new boolean[8][8];
        used[3][3] = true;
        used[3][4] = true;
        used[4][3] = true;
        used[4][4] = true;
        for (int i = 0; i < placementSequence.length(); i+=6) {
            String position = placementSequence.substring(i+4,i+6);
            int y = Integer.parseInt(String.valueOf(position.charAt(0)));
            int x = Integer.parseInt(String.valueOf(position.charAt(1)));
            used[y][x] = true;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!used[i][j]) {
                    restPositions.add(i + "" + j);
                }
            }
        }
        return restPositions;
    }

    /**
     * This method is to change the coordinates of the mouse into the corresponding tile's position.
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @return the array contains the position of the corresponding tile
     */
    public int[] getMousePosition(double x, double y) {
        int[] positions = new int[2];
        positions[0] = (int) (x / 70) - 1;
        positions[1] = (int) (y / 70) - 1;
        return positions;
    }

}
