package comp1110.ass2.gui;

import comp1110.ass2.Metro;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * A very simple viewer for piece placements in the Metro game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    /* board layout */
    private static final int SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group tiles = new Group();
    private final Group stations = new Group();
    private final Group board = new Group();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
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
     * Place a tile on the board based on it's given position.
     * @author Anbang Li
     * @param p the tile with the position
     */
    public void viewImage(String p) {
        char[] q = p.toCharArray();
        char[] t = Arrays.copyOfRange(q, 0, 4);
        String s = new String(t);
        Image image = new Image(Viewer.class.getResource(URI_BASE + s + ".png").toString());
        ImageView imageView = new ImageView(image);
        int x = 0;
        int y = 0;
        imageView.setFitHeight(SQUARE_SIZE);
        imageView.setFitWidth(SQUARE_SIZE);
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
        tiles.getChildren().add(imageView);
    }

    /**
     * Put all stations' images on the board.
     * @author Anbang Li
     */
    private void stations(){
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
     * Place a background on the board.
     * @author Anbang Li
     */
    void grids() {
        Image i = new Image(Viewer.class.getResource("assets/grids.png").toString());
        ImageView background = new ImageView(i);
        board.getChildren().add(background);
        background.setFitWidth(700);
        background.setFitHeight(700);
        background.setLayoutX(162);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        grids();
        stations();
        makeControls();

        root.getChildren().addAll(board, stations, controls, tiles);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
