# New Code for Deliverable D2D

## < u7078355 > < Lan Xue >

For Deliverable D2D, I contributed the following new statements of original code:

- from Station class getConnectedTile Method （line116-line143)
- This method can gets the expected position and exit index of the tile that connects with the given station.
  Later, this method will be called as a help function to determine whether the tiles in the given sequence 
  has arrived at any station when calculating the points.

  ```
  public static HashMap<String, Integer> getConnectedTile(int stationPosition) {
      // Gets the exit index and the position of the tile connected to the given station
      int exitIndex = 0;
      int x = 0;
      int y = 0;
      if (Arrays.asList(UPPER).contains(stationPosition)) {
          exitIndex = 0;
          y = 0;
          x = 8 - stationPosition;
      } else if (Arrays.asList(LEFT).contains(stationPosition)) {
          exitIndex = 6;
          x = 0;
          y = stationPosition - 9;
      } else if (Arrays.asList(BELOW).contains(stationPosition)) {
          exitIndex = 4;
          y = 7;
          x = stationPosition - 17;
      } else if (Arrays.asList(RIGHT).contains(stationPosition)) {
          exitIndex = 2;
          x = 7;
          y = 32 - stationPosition;
      }
      HashMap<String, Integer> connectedTile = new HashMap<>();
      connectedTile.put("y", y);
      connectedTile.put("x", x);
      connectedTile.put("exitIndex", exitIndex);
      return connectedTile;
  }
  ```

(List at least 10, and include line numbers.)
