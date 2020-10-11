    # New Code for Deliverable D2E

## < u7078355 > < Lan Xue >

For Deliverable D2E, I contributed the following new statements of original code:

- From Viewer class makePlayerControls method (line304 - line321)
- This part created a new button which can help withdraw the tiles placed just now.
  1. The withdrawn tile will be showed in the button as an image.
  2. You cannot place any other tile before putting back the withdrawn tile.

  ```
  // Create a button to withdraw the tile which was put just now
  withdraw = new Button("withdraw");
  withdraw.setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
  withdraw.setCursor(Cursor.HAND);
  withdraw.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
          if (textField.getText().length() % 6 == 0) {
              textField.setText(textField.getText().substring(0, textField.getText().length() - 2));
              makePlacement(textField.getText().substring(0, textField.getText().length() - 4));
              String tile = textField.getText().substring(textField.getText().length() - 4);
              Image image = new Image(this.getClass().getResource(URI_BASE + tile + ".jpg").toString());
              ImageView imageView = new ImageView(image);
              withdraw.setGraphic(imageView);
              takeDeckTile = true;
          }
      }
  });
  ```
- From Viewer class popEndWindow method (line389 - line405).
- This method pop a new window when the game is over, which mean the board has been filled with 60 tiles.

  ```
  public void popEndWindow() {
      textField.textProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
              score = Metro.getScore(textField.getText(), 1)[0];
              scoreLabel.setText(score+"");
              if (t1.length() == 360) {
                  EndWindow endWindow = new EndWindow();
                  try {
                      endWindow.start(new Stage());
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }
      });
  }
  ```

- Two Unit test code: 
- 1. ExceedMaxNumberTest: to check whether the exceedMaxNumber method in TileInterface class 
     can make the right decision: true for exceed, otherwise false.
- 2. GetPointsTest: to check whether the getPoints method in Score class can calculate the right
     points of the given single station based on the sequence on the board.

(List at least 20, including at least one new unit test, and include line numbers.)
