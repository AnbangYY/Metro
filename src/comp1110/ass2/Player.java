package comp1110.ass2;

import java.util.*;

/**
 * A class will generate players who possess different stations and scores,
 * and help player to generate good move.
 * @author the group
 */
public class Player {

    public Player() {
    }

    /**
     * Calculates the given player's score based on the current board.
     * @param board the current board state
     * @param playerIndex the given player
     * @return the given player's score
     */
    public int getPlayerScore(Board board, int playerIndex, int numberOfPlayers) {
        int[] scoreList = Metro.getScore(board.getBoard(), numberOfPlayers);
        return scoreList[playerIndex];
    }

    /**
     * Gives the winner based on the current board situation.
     * @param board the current board situation
     * @return the winner
     */
    public String getWinner(Board board, int numberOfPlayers) {
        String winner = "";
        int maxScore = 0;
        for (int i = 1; i <= numberOfPlayers; i++) {
            int score = getPlayerScore(board, i - 1, numberOfPlayers);
            if (maxScore == 0) {
                maxScore = score;
                winner = "You";
            } else if (score > maxScore){
                maxScore = score;
                winner = "Player" + i;
            }
        }
        return winner;
    }

    /** Blocks the opponent's move with lowest mark.
     * If opponent cannot be blocked in one move then branch to heuristic of late game strategy.
     * Otherwise, Gets the lowest mark in early game, and harvest high mark in later game.
     * works better with more bot players in the game
     * @param board the current board state
     * @param piece the piece needed to be placed
     * @param robotIndex the index of robot
     * @param numberOfPlayers the total number of players
     * @return goodMove a string representing the next move
     */
     public String generateGoodMove(Board board, String piece, int robotIndex, int numberOfPlayers) {
         String goodMove;
         ArrayList<String> restPositions = board.getRestPosition();
         int humanPlayerCurrentScore = getPlayerScore(board, 0, numberOfPlayers);
         int robotScore ;
         int humanPlayerExpectedScore;
         int positionsNo = restPositions.size();
         HashMap<Integer, ArrayList<String>> robotScoreTable = new LinkedHashMap<>();
         HashMap<Integer, ArrayList<String>> playerScoreTable = new LinkedHashMap<>();
         for (String position : restPositions) {
             if (Metro.isPlacementSequenceValid(board.getBoard() + piece + position)) {
                 goodMove = piece + position;
                 Board pSequence = new Board(board.getBoard() + goodMove);
                 robotScore = getPlayerScore(pSequence, robotIndex + 1, numberOfPlayers);
                 humanPlayerExpectedScore = getPlayerScore(pSequence, 0, numberOfPlayers);
                 // put different robotScores with the relative moves in the robotScoreTable
                 generateScoreTable(goodMove, robotScore, robotScoreTable);
                 // If can change player's score, put different robotScores with the relative moves in the playerScoreTable
                 if (humanPlayerExpectedScore != humanPlayerCurrentScore) {
                     generateScoreTable(goodMove, humanPlayerExpectedScore, playerScoreTable);
                 }
             }
         }
         Random generator = new Random();
         Set<Integer> robotScores = robotScoreTable.keySet();
         Set<Integer> playerScores = playerScoreTable.keySet();
         Object[] robotObj = robotScores.toArray();
         Object[] playerObj = playerScores.toArray();
         Arrays.sort(robotObj);
         Arrays.sort(playerObj);
         // If the human player's score has been changed, try to finish the track with minimum score
         if (playerObj.length != 0) {
             int minScore = (int) playerObj[0];
             ArrayList<String> goodMoves = playerScoreTable.get(minScore);
             int randomIndex = generator.nextInt(goodMoves.size());
             goodMove = goodMoves.get(randomIndex);
         } else {
             if (positionsNo > 30) {
                 int minScore = (int) robotObj[0];
                 ArrayList<String> goodMoves = robotScoreTable.get(minScore);
                 int randomIndex = generator.nextInt(goodMoves.size());
                 goodMove = goodMoves.get(randomIndex);
             } else {
                 int maxScore = (int) robotObj[robotObj.length - 1];
                 ArrayList<String> goodMoves = robotScoreTable.get(maxScore);
                 int randomIndex = generator.nextInt(goodMoves.size());
                 goodMove = goodMoves.get(randomIndex);
             }
         }
         return goodMove;
     }

    /**
     * A help method for generateGoodMove to update the map.
     * key is the score that can be earned, value if the move that will win the score.
     * @param goodMove the expected placement
     * @param score the expected score earned by the goodMove
     * @param scoreTable the current map needed to be updated
     */
    private void generateScoreTable(String goodMove, int score, HashMap<Integer, ArrayList<String>> scoreTable) {
        if (scoreTable.get(score) == null) {
            ArrayList<String> moves = new ArrayList<>();
            moves.add(goodMove);
            scoreTable.put(score, moves);
        } else {
            ArrayList<String> moves = scoreTable.get(score);
            moves.add(goodMove);
            scoreTable.put(score, moves);
        }
    }
}
