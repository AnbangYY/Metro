package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Metro;
import comp1110.ass2.Player;
import comp1110.ass2.Station;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class that plays the game.
 * @author the group
 * @date 2020-05-09 20:29
 */
public class Game extends Application {

    /** board layout */
    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;

    /** where to find media assets */
    private static final String URI_BASE = "assets/";

    /** node groups */
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group tiles = new Group();
    private final Group availableTiles = new Group();
    private final Group players = new Group();
    private final Group stations = new Group();
    private final StackPane robots = new StackPane();
    private final Group stationColors = new Group();

    /** inner classes and nodes */
    private Label scoreLabel;
    private TextField textField;
    private GridBackground background;
    private DeckTileButton deck;
    private HandTileButton hand;
    private WithdrawButton withdraw;

    /** Properties in the game process */
    private ImageView backCover;
    private Board currentBoard;
    private Robot robot;
    private Stations station;
    private String handTile = "";
    private String deckTile = "";
    private int mouseX = 0;
    private int mouseY = 0;
    private boolean takeHandTile = false;
    private boolean takeDeckTile = false;
    private int score;
    private int numberOfPlayers;
    private String winner;
    private String robotHandTile;
    private Label[] scoreLabelList;
    private final static Color[] colors = {Color.YELLOWGREEN, Color.LIGHTCORAL, Color.MEDIUMTURQUOISE, Color.GOLD, Color.MEDIUMPURPLE, Color.ROSYBROWN};

    public Game() {
        currentBoard = new Board("");
        robot = new Robot();
        station = new Stations();
        textField = new TextField();
        backCover = new ImageView(new Image(this.getClass().getResource(URI_BASE + "tile_back_cover.jpg").toString()));
        background = new GridBackground(new Image(this.getClass().getResource("assets/grids.png").toString()));
        background.setOpacity(0.7);
        backCover.setFitWidth(SQUARE_SIZE);
        backCover.setFitHeight(SQUARE_SIZE);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Metro Game");
        LoadingScene scene = new LoadingScene(root,VIEWER_WIDTH, VIEWER_HEIGHT);
        scene.setLoadingPage();
        String cssFile = getClass().getResource("ContentStyle.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draw a placement in the window, removing any previously drawn one.
     * @author Anbang Li
     * @param placement A valid placement string
     */
    private void makePlacement(String placement) {
        tiles.getChildren().clear();
        if(Metro.isPlacementSequenceWellFormed(placement)){
            String Tplace = "";
            for (int i = 0; i < placement.length() / 6; i++){
                for (int j = 0; j < 6; j ++){
                    Tplace = Tplace + placement.charAt(i * 6 + j);
                }
                viewImage(Tplace);
                Tplace = "";
            }
        }
    }

    /**
     * Show the available positions for the next placement with oranges square.
     * @param tile the tile that will be placed
     * @param board the current board
     * @author the group
     */
    private void showAvailablePositions(String tile, Board board){
        int n = board.getRestPosition().size();
        String available = "";
        for(int i=0; i<n; i++){
            if(Metro.isPlacementSequenceValid(board.getBoard() + tile  + board.getRestPosition().get(i))){
                String t = "kkkk";
                available = available + (t) +board.getRestPosition().get(i);
            }
        }
        availableTiles.getChildren().clear();
        String Tplace = "";
        for (int i = 0; i < available.length() / 6; i++){
            for (int j = 0; j < 6; j ++){
                Tplace = Tplace + available.charAt(i * 6 + j);
            }
            viewAvailablePositions(Tplace);
            Tplace = "";
        }
    }

    /**
     * Get a ImageView with the right position based on the given placement string.
     * @param p the given placement string
     * @return the ImageView
     * @author Anbang Li
     */
    private ImageView getImageView(String p) {
        char[] q = p.toCharArray();
        char[] t = Arrays.copyOfRange(q, 0, 4);
        String s = new String(t);
        Image image = new Image(this.getClass().getResource(URI_BASE + s + ".png").toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(SQUARE_SIZE);
        imageView.setFitWidth(SQUARE_SIZE);
        int x = 0;
        int y = 0;
        for (int i = 0; i < 8; i++) {
            char a = (char) (i + '0');
            if (p.charAt(4) == a) {
                y = 70 * i + 70;
            }
        }
        for (int j = 0; j < 8; j++) {
            char b = (char) (j + '0');
            if (p.charAt(5) == b) {
                x = 70 * j + 232;
            }
        }
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    /**
     * Place a tile on the board based on it's given position.
     * @author Anbang Li
     * @param p the tile with the position
     */
    private void viewImage(String p) {
        ImageView imageView = getImageView(p);
        tiles.getChildren().add(imageView);
    }

    /**
     * Place an orange square on the board based on it's given position.
     * @author the group
     * @param p the tile with the position
     */
    private void viewAvailablePositions(String p) {
        ImageView imageView = getImageView(p);
        availableTiles.getChildren().add(imageView);
    }

    /**
     * Create a invisible text field to record the sequence on the board and a button to restart the game.
     * @author Anbang Li
     */
    private void gameControl() {
        HBox hb = new HBox();
        textField = new TextField();
        textField.setVisible(false);
        Button restart = new Button("Restart");
        restart.setId("controlButton");
        restart.setOnAction(e -> {
            textField.clear();
            makePlacement(textField.getText());
            players.getChildren().clear();
            robots.getChildren().clear();
            availableTiles.getChildren().clear();
            makePlayerControls();
            robot = new Robot();
            robot.makeRobots();
        });
        Button quit = new Button("Quit");
        quit.setId("controlButton");
        quit.setOnAction(actionEvent -> System.exit(0));
        hb.getChildren().addAll(textField, restart, quit);
        hb.setSpacing(40);
        hb.setLayoutX(250);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    /**
     * Create some node for player to control, including tile in hand, tile drew from the deck,
     * withdraw the tile and show the score.
     * @author Lan Xue
     */
    private void makePlayerControls() {
        // The button to control whether to use the tile in hand
        Label label1 = new Label("Tile In Hand");
        label1.setId("playerLabel");
        hand = new HandTileButton();
        // The button to control whether to draw a tile from the deck
        Label label2 = new Label("Tile From Deck");
        label2.setId("playerLabel");
        deck = new DeckTileButton();
        // Create a button to withdraw the tile which was put just now
        withdraw = new WithdrawButton("withdraw");
        withdraw.setId("withdrawButton");
        // The label to show the real-time score
        Label label3 = new Label("Your Score");
        label3.setId("playerLabel");
        scoreLabel = new Label("0");
        scoreLabel.setStyle("-fx-font-size: 40;" + "-fx-text-fill: YELLOWGREEN");
        VBox vb = new VBox();
        vb.getChildren().addAll(label1, hand, label2, deck, withdraw, label3, scoreLabel);
        vb.setStyle("-fx-spacing: 20;" + "-fx-translate-x: 30;" + "-fx-translate-y: 70;" + "-fx-alignment: center");
        players.getChildren().add(vb);
    }

    /**
     * Pop a new window when the game is over.
     * @author Anbang Li
     */
    public void popEndWindow() {
        textField.textProperty().addListener((observableValue, s, t1) -> {
            currentBoard.setBoard(t1);
            // Change the score for every one once the tile is placed
            score = Metro.getScore(currentBoard.getBoard(), numberOfPlayers)[0];
            scoreLabel.setText(score+"");
            for (int i = 0; i < scoreLabelList.length; i++) {
                Player currentPlayer = new Player();
                int currentScore = currentPlayer.getPlayerScore(currentBoard, i+1, numberOfPlayers);
                scoreLabelList[i].setText("Player " + (i+2) + ": " + currentScore);
            }
            if (currentBoard.getBoard().length() == 360) {
                Player player = new Player();
                winner = player.getWinner(currentBoard, numberOfPlayers);
                EndWindow endWindow = new EndWindow();
                try {
                    endWindow.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * A class that helps control all stations.
     * @author the group
     */
    private class Stations extends Station {

        /**
         * Put all stations' images on the board.
         * @author Anbang Li
         */
        private void makeStations(){
            int x = 442;
            int y = 280;
            int x1 = x+70;
            int y1 = y+70;
            Image cs = new Image(getClass().getResource("assets/centre_station.jpg").toString());
            ImageView c = new ImageView(cs);
            c.setFitWidth(70);
            c.setFitHeight(70);
            c.setLayoutX(x);
            c.setLayoutY(y);
            c.setRotate(270);
            stations.getChildren().add(c);
            ImageView d = new ImageView(cs);
            d.setFitWidth(70);
            d.setFitHeight(70);
            d.setLayoutX(x1);
            d.setLayoutY(y);
            stations.getChildren().add(d);
            ImageView e = new ImageView(cs);
            e.setFitWidth(70);
            e.setFitHeight(70);
            e.setLayoutX(x);
            e.setLayoutY(y1);
            e.setRotate(180);
            stations.getChildren().add(e);
            ImageView f = new ImageView(cs);
            f.setFitWidth(70);
            f.setFitHeight(70);
            f.setLayoutX(x1);
            f.setLayoutY(y1);
            f.setRotate(90);
            stations.getChildren().add(f);

            for(int i=1; i<9; i++){
                char a = (char)(i+'0');
                Image image = new Image(getClass().getResource(URI_BASE + "station" + a + ".jpg").toString());
                ImageView imageView = new ImageView(image);
                imageView.setLayoutX(722-(i-1)*70);
                imageView.setFitHeight(SQUARE_SIZE);
                imageView.setFitWidth(SQUARE_SIZE);
                imageView.setRotate(180);
                stations.getChildren().add(imageView);
            }

            for(int i=9; i<17; i++){
                String s = String.valueOf(i);
                Image image = new Image(getClass().getResource(URI_BASE + "station" + s + ".jpg").toString());
                ImageView imageView = new ImageView(image);
                imageView.setLayoutY(70+(i-9)*70);
                imageView.setLayoutX(162);
                imageView.setFitHeight(SQUARE_SIZE);
                imageView.setFitWidth(SQUARE_SIZE);
                imageView.setRotate(90);
                stations.getChildren().add(imageView);
            }

            for(int i=17; i<25; i++){
                String s = String.valueOf(i);
                Image image = new Image(getClass().getResource(URI_BASE + "station" + s + ".jpg").toString());
                ImageView imageView = new ImageView(image);
                imageView.setLayoutX(232+(i-17)*70);
                imageView.setLayoutY(630);
                imageView.setFitHeight(SQUARE_SIZE);
                imageView.setFitWidth(SQUARE_SIZE);
                stations.getChildren().add(imageView);
            }

            for(int i=25; i<33; i++){
                String s = String.valueOf(i);
                Image image = new Image(getClass().getResource(URI_BASE + "station" + s + ".jpg").toString());
                ImageView imageView = new ImageView(image);
                imageView.setLayoutY(560-(i-25)*70);
                imageView.setLayoutX(792);
                imageView.setFitHeight(SQUARE_SIZE);
                imageView.setFitWidth(SQUARE_SIZE);
                imageView.setRotate(270);
                stations.getChildren().add(imageView);
            }
        }

        /**
         * Divide all stations into different groups based on the number of players.
         * @author Lan Xue
         */
        private void divideStations() {
            HashMap<Integer, int[]> stationMap = getPlayersStations();
            HBox upper = new HBox();
            HBox below = new HBox();
            VBox left = new VBox();
            VBox right = new VBox();
            for (int i = 8; i > 0 ; i--) {
                Rectangle box = new Rectangle(40, 7);
                makeColor(stationMap, i, box);
                upper.getChildren().add(box);
            }
            for (int i = 17; i < 25; i++) {
                Rectangle box = new Rectangle(40, 7);
                makeColor(stationMap, i, box);
                below.getChildren().add(box);
            }
            for (int i = 9; i < 17; i++) {
                Rectangle box = new Rectangle(7, 40);
                makeColor(stationMap, i, box);
                left.getChildren().add(box);
            }
            for (int i = 32; i > 24; i--) {
                Rectangle box = new Rectangle(7, 40);
                makeColor(stationMap, i, box);
                right.getChildren().add(box);
            }

            upper.setStyle("-fx-translate-x: 248;" + "-fx-translate-y:7;" + "-fx-spacing: 30");
            below.setStyle("-fx-translate-x: 246;" + "-fx-translate-y:686;" + "-fx-spacing: 30");
            left.setStyle("-fx-translate-x: 169;" + "-fx-translate-y:83;" + "-fx-spacing: 30");
            right.setStyle("-fx-translate-x: 848;" + "-fx-translate-y:85;" + "-fx-spacing: 30");
            stationColors.getChildren().addAll(upper, below, left, right);

        }

        /**
         * Color the station according to where it belongs.
         * @param stationMap a HashMap which stores each player's stations
         * @param stationIndex the current station needed to be colored
         * @param box color piece
         * @author Lan Xue
         */
        private void makeColor(HashMap<Integer, int[]> stationMap, int stationIndex, Rectangle box) {
            box.setFill(Color.DIMGREY);
            for (int playerIndex : stationMap.keySet()) {
                int[] stations = stationMap.get(playerIndex);
                ArrayList<Integer> stationList = new ArrayList<>();
                for (int station : stations) {
                    stationList.add(station);
                }
                if (stationList.contains(stationIndex)) {
                    box.setFill(colors[playerIndex - 1]);
                }
            }
        }
    }

    /**
     * A class that creates a board background that the player can interact with
     * (e.g. click the position to place the tile).
     */
    private class GridBackground extends ImageView {
        public GridBackground(Image image) {
            super(image);
            setFitWidth(700);
            setFitHeight(700);
            setLayoutX(162);
            setCursor(Cursor.HAND);
            board.getChildren().add(this);
            this.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();
                    Board board = new Board();
                    mouseX = board.getMousePosition(x, y)[0];
                    mouseY = board.getMousePosition(x, y)[1];
                    StringBuilder sequence = new StringBuilder();
                    sequence.append(textField.getText());
                    sequence.append(mouseY);
                    sequence.append(mouseX);
                    if (currentBoard.getBoard().length() < 360 - numberOfPlayers * 6) {
                        if (Metro.isPlacementSequenceWellFormed(sequence.toString())) {
                            if (Metro.isPlacementSequenceValid(sequence.toString())) {
                                availableTiles.getChildren().clear();
                                textField.setText(sequence.toString());
                                makePlacement(currentBoard.getBoard());
                                deck.setGraphic(null);
                                takeDeckTile = false;
                                withdraw.setGraphic(null);
                                if (takeHandTile) {
                                    hand.setGraphic(null);
                                    handTile = Metro.drawFromDeck(currentBoard.getBoard(), "");
                                    Image image = new Image(this.getClass().getResource(URI_BASE + handTile + ".png").toString());
                                    ImageView imageView = new ImageView(image);
                                    hand.setGraphic(imageView);
                                    takeHandTile = false;
                                }
                                if (deck.getGraphic() == null) {
                                    deck.setGraphic(backCover);
                                }
                                if(numberOfPlayers > 1){
                                    robot.robotMoves(numberOfPlayers-1);
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("WRONG PLACEMENT");
                                alert.setHeaderText("CAN NOT PUT THIS TILE HERE");
                                alert.setContentText("Game rule : \n" +
                                        "· A tile may not be placed so that it connects two stations with a track of length 1.\n" +
                                        "· Each tile must be placed on a square adjacent to another tile or the edge of the board.\n" +
                                        "· A tile may not be placed next to one of the central station tiles unless it is also adjacent to another tile.");
                                alert.showAndWait();
                            }
                        }
                    } else {
                        availableTiles.getChildren().clear();
                        textField.setText(sequence.toString());
                        makePlacement(currentBoard.getBoard());
                        handTile = "";
                        if(numberOfPlayers > 1){
                            robot.robotMoves(numberOfPlayers-1);
                        }
                        takeHandTile = false;
                        takeDeckTile = false;
                    }
                }
            });
        }
    }

    /**
     * A class that creates a loading scene which contains two functions:
     * start game by choosing the number of players and read the introduction.
     * @author Anbang Li
     */
    private class LoadingScene extends Scene {
        ImageView loadingImage = new ImageView(new Image(this.getClass().getResource(URI_BASE + "loading_image.jpg").toString()));
        public LoadingScene(Parent parent, double v, double v1) {
            super(parent, v, v1);
        }

        public void setLoadingPage() {
            Group content = new Group();
            loadingImage.setFitWidth(VIEWER_WIDTH);
            loadingImage.setFitHeight(VIEWER_HEIGHT);
            // Adds two buttons for start and instruction
            HBox buttons = new HBox();
            Button start = new Button("Start Game");
            start.setId("loadingPageButton");
            start.setOnAction(actionEvent -> {
                PlayerNumberWindow playerNumberWindow = new PlayerNumberWindow();
                try {
                    playerNumberWindow.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button intro = new Button("Introduction");
            intro.setId("loadingPageButton");
            intro.setOnAction(actionEvent -> {
                IntroWindow introWindow = new IntroWindow();
                try {
                    introWindow.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttons.getChildren().addAll(start, intro);
            buttons.setId("landingPageButtons");
            content.getChildren().addAll(loadingImage, buttons);
            root.getChildren().add(content);
        }

        /**
         * An inner class that created a window for the player to start the game
         * by choosing the number of players.
         */

        public class PlayerNumberWindow extends Application {
            Stage stage;
            @Override
            public void start(Stage stage) throws Exception {
                this.stage = stage;
                String cssFile = getClass().getResource("ContentStyle.css").toExternalForm();
                HBox buttons = new HBox();
                for (int i = 1; i <= 6; i++) {
                    Button button = new Button(i + " player");
                    button.setId("playerChooseButton");
                    int number = i;
                    button.setOnAction(actionEvent -> {
                        numberOfPlayers = number;
                        station.setNumberOfPlayers(numberOfPlayers);
                        stage.close();
                        root.getChildren().clear();
                        loadingImage.setOpacity(0.4);
                        root.getChildren().addAll(loadingImage, board, controls, availableTiles, background, tiles, stations, stationColors, players, robots);
                        gameControl();
                        makePlayerControls();
                        station.makeStations();
                        station.divideStations();
                        robot.makeRobots();
                        popEndWindow();
                    });
                    buttons.getChildren().add(button);
                }
                buttons.setStyle("-fx-alignment: center;" + "-fx-spacing: 40");
                Scene scene = new Scene(buttons, 800, 200);
                scene.getStylesheets().add(cssFile);
                stage.setScene(scene);
                stage.setTitle("Choose Player Numbers");
                stage.show();
            }
        }
    }

    /**
     * A class that creates a window to show the introduction of the game.
     * @author Lan Xue
     */
    private class IntroWindow extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            VBox content = new VBox();
            // Set text to introduce game rules
            Label title = new Label("Who will build the longest Metro line?");
            title.setId("introTitle");
            VBox text = new VBox();
            text.setId("introText");
            Label rule1 = new Label("Metro is a game for 2-6 players, who take turns placing tiles. Each player starts with an empty hand.\n" +
                    "On their turn, a player draws a tile then place the tile on the board.\n" +
                    "If they do not wish to place the tile, they may pick up another tile from the deck and place immediately.\n");
            Label rule2 = new Label("There are 4 conditions that must be followed when laying down tiles:\n" +
                    "· Each tile must be placed on a square adjacent to another tile or the edge of the board.\n" +
                    "· A tile may not be placed next to one of the central station tiles unless it is also adjacent to another tile.\n" +
                    "· Tiles cannot be rotated.\n" +
                    "· A tile may not be placed so that it connects two stations directly(or loops back to the same station)");
            Label rule3 = new Label("Each time the metro line cross a tile, this line get 1 point.\n" +
                    "The game ends when all tiles have been placed. The one whose stations have the highest scores won");
            text.getChildren().addAll(rule1, rule2, rule3);
            // Create a button to close the window
            Button close = new Button("CLOSE");
            close.setId("introCloseButton");
            close.setOnAction(actionEvent -> stage.close());

            content.getChildren().addAll(title, text, close);
            content.setStyle("-fx-alignment: center;" + "-fx-spacing: 30;");
            content.setBackground(new Background(new BackgroundFill(new Color(0,0,0,0.3), null, null)));
            Scene scene = new Scene(content, 800, 600);
            String cssFile = getClass().getResource("ContentStyle.css").toExternalForm();
            scene.getStylesheets().add(cssFile);
            stage.setScene(scene);
            stage.setTitle("Game Introduction");
            stage.show();
        }
    }

    /**
     * A class that creates a window to show the end of the game, containing two functions: restart and exit.
     * @author Lan Xue
     */
    private class EndWindow extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            BorderPane root = new BorderPane();
            Group content = new Group();
            // Create a label for congratulations
            Label text = new Label( "You've got "+ score + " points!\n" + "Winner is " + winner);
            text.setStyle("-fx-font-size: 24;" + "-fx-text-fill: #063333;" + "-fx-text-alignment: center;" + "-fx-cursor: hand;" + "-fx-translate-x: 20");
            // Create two buttons for game restart and exit
            HBox buttons = new HBox();
            Button restart = new Button("Restart");
            restart.setOnAction(actionEvent -> {
                textField.setText("");
                currentBoard.setBoard("");
                makePlacement(textField.getText());
                players.getChildren().clear();
                robots.getChildren().clear();
                availableTiles.getChildren().clear();
                makePlayerControls();
                robot = new Robot();
                robot.makeRobots();
                stage.close();
            });
            // Create a button to exit the whole game
            Button exit = new Button("Exit");
            exit.setOnAction(actionEvent -> System.exit(0));
            buttons.getChildren().addAll(restart, exit);
            buttons.setStyle("-fx-translate-x: 75;" + "-fx-translate-y: 70;" + "-fx-spacing: 20;" + "-fx-cursor: hand");
            content.getChildren().addAll(text, buttons);
            root.setCenter(content);
            Scene scene = new Scene(root, 300, 200);
            stage.setScene(scene);
            stage.setTitle("GAME OVER");
            stage.show();
        }
    }

    /**
     * A class that creates a button for player to choose the tile in the hand.
     * @author Anbang Li
     */
    private class HandTileButton extends Button {
        public HandTileButton() {
            handTile = "";
            takeHandTile = false;
            this.setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
            this.setCursor(Cursor.HAND);
            if (this.getGraphic() == null) {
                handTile = Metro.drawFromDeck(currentBoard.getBoard(), handTile);
                Image image = new Image(super.getClass().getResource(URI_BASE + handTile + ".png").toString());
                ImageView imageView = new ImageView(image);
                setGraphic(imageView);
            }
            this.setOnAction(actionEvent -> {
                if (!takeDeckTile && !takeHandTile && Metro.isPlacementSequenceValid(currentBoard.getBoard())) {
                    if (currentBoard.getBoard().length() % (numberOfPlayers * 6) == 0) {
                        showAvailablePositions(handTile, currentBoard);
                        textField.setText(currentBoard.getBoard() + handTile);
                        takeHandTile = true;
                    }
                }
            });
        }
    }

    /**
     * A class that creates a button for player to pick a random tile from the deck.
     * @author Anbang Li
     */
    private class DeckTileButton extends Button {
        public DeckTileButton() {
            deckTile = "";
            takeDeckTile = false;
            this.setGraphic(backCover);
            this.setStyle("-fx-font-size: 26;"
                    + "-fx-text-fill: #5787ff");
            this.setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
            this.setCursor(Cursor.HAND);
            this.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText("");
                    if (currentBoard.getBoard().length() < 360 - numberOfPlayers * 6) {
                        // if take the tile in hand and not draw a tile from the deck,
                        // it's allowed to put down the tile and draw a new tile from the deck.
                        // But it't now allowed to take the tile in the hand again after drawing a tile from the deck.

                        if (takeHandTile && !takeDeckTile) {
                            textField.setText(currentBoard.getBoard().substring(0, currentBoard.getBoard().length() - 4));
                            takeHandTile = false;
                        }
                        if ((currentBoard.getBoard().length() % (numberOfPlayers * 6) == 0) && !takeDeckTile) {
                            String tile = Metro.drawFromDeck(currentBoard.getBoard(), handTile);
                            deckTile = tile;
                            showAvailablePositions(deckTile, currentBoard);
                            Image image = new Image(this.getClass().getResource(URI_BASE + deckTile + ".png").toString());
                            ImageView imageView = new ImageView(image);
                            setGraphic(imageView);
                            textField.setText(currentBoard.getBoard() + deckTile);
                            takeDeckTile = true;
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("NO MORE TILES");
                        alert.setHeaderText("NO MORE TILES LEFT IN THE DECK");
                        alert.setContentText("Please put the tile in your hand on the board");
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    /**
     * A class that creates a button for player to withdraw the tile he/she placed just now.
     * @author Lan Xue
     */
    private class WithdrawButton extends Button {
        public WithdrawButton(String s) {
            super(s);
            setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
            setCursor(Cursor.HAND);
            this.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (currentBoard.getBoard().length() % (numberOfPlayers * 6) == 0) {
                        textField.setText(currentBoard.getBoard().substring(0, currentBoard.getBoard().length() - (2 + (numberOfPlayers-1)*6)));
                        makePlacement(currentBoard.getBoard().substring(0, textField.getText().length() - 4));
                        String tile = currentBoard.getBoard().substring(currentBoard.getBoard().length() - 4);
                        Image image = new Image(this.getClass().getResource(URI_BASE + tile + ".png").toString());
                        ImageView imageView = new ImageView(image);
                        setGraphic(imageView);
                        takeDeckTile = true;
                    }
                }
            });
        }
    }

    /**
     * A class to control the nodes of robots as well as the tile placement from robots.
     * @author the group
     */
    private class Robot extends Player {
        private static final int FADE_FRAME_LENGTH = 350;
        private ArrayList<ImageView> robotImages = new ArrayList<>();
        int index = 0;

        /**
         * To create the interface of the robots depends on the number of players.
         * @author Lan Xue
         */
        private void makeRobots() {
            VBox robotGroup = new VBox();
            scoreLabelList = new Label[numberOfPlayers-1];
            for (int i = 1; i < numberOfPlayers; i++) {
                ImageView robotImage = new ImageView(new Image(this.getClass().getResource(URI_BASE + "robot.png").toString()));
                robotImage.setFitWidth(80);
                robotImage.setFitHeight(80);
                robotGroup.getChildren().add(robotImage);
                Label score = new Label("Player " + (i+1) + " : 0");
                score.setTextFill(colors[i]);
                score.setStyle("-fx-font-size: 20");
                robotGroup.getChildren().add(score);
                scoreLabelList[i-1] = score;
                robotImages.add(robotImage);
            }
            robotGroup.setStyle("-fx-spacing: 10;" + "-fx-alignment: center");
            robots.getChildren().addAll(robotGroup);
            robots.setStyle("-fx-pref-height: 700;" +"-fx-translate-x: 900;");
        }

        /**
         * A method to place the current tile on the board with fade effect.
         * @param placement
         * @author the group
         */
        private void placeTile(String placement) {
            String tile = placement.substring(0,4);
            Image image = new Image(this.getClass().getResource(URI_BASE + tile + ".png").toString());
            ImageView imageView = new ImageView(image);
            int x = 0;
            int y = 0;
            imageView.setFitHeight(SQUARE_SIZE);
            imageView.setFitWidth(SQUARE_SIZE);
            for (int i=0; i<8; i++) {
                char a = (char) (i + '0');
                if (placement.charAt(4) == a) {
                    y = 70 * i + 70;
                }
            }
            for (int j=0; j<8; j++) {
                char b = (char)(j+'0');
                if(placement.charAt(5) == b){
                    x = 70*j+232;
                }
            }
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            // Set animation
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.seconds(1.5));
            fade.setCycleCount(1);
            fade.setNode(imageView);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
            tiles.getChildren().add(imageView);
        }

        /**
         * This method makes robots to put the tile on the board in order.
         * @param numberOfRobots the total number of robots
         * @author the group
         */
        private void robotMoves(int numberOfRobots) {
            FadeTransition fade = new FadeTransition();
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(FADE_FRAME_LENGTH * numberOfRobots),
                    actionEvent -> {
                        fade.setNode(robotImages.get(index % numberOfRobots));
                        fade.setDuration(Duration.millis(FADE_FRAME_LENGTH));
                        fade.setFromValue(0.2);
                        fade.setToValue(1);
                        fade.setCycleCount(1);
                        fade.play();

                        robotHandTile = Metro.drawFromDeck(currentBoard.getBoard(), handTile);
                        String placement = generateGoodMove(currentBoard, robotHandTile, index % numberOfRobots, numberOfPlayers);
                        textField.setText(currentBoard.getBoard() + placement);
                        placeTile(placement);
                        index ++;
                    }));
            animation.setCycleCount(numberOfRobots);
            animation.play();
        }
    }

}
