    # New Code for Deliverable D2E

## < u6744849 > < Anbang Li >

For Deliverable D2E, I contributed the following new statements of original code:

- Codes in Task 6.
- Codes in Task 9.

Implementation of task 6 has 3 different parts. 
- First, create a hashmap that stores an empty board with tile positions.
 ```
//Create an empty hashmap
       Map<String,String> map = new LinkedHashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!((i==3 && (j==3 || j==4)) || (i==4 && (j==3||j==4)))) {
                    map.put((i + "") + j, null);
                }
            }
        }
```
- Then, use a loop to put each tile in the sequence into the hashmap. Meanwhile check if there is already a value in
  the key-value pair, return false if there is.
 ```
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
```

- The third part is to make sure that no tile is placed near central station when no tile is near it.
```
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
```
- The last part is to check if the tile near the stations loop back to the station or the station adjacent, given that there
  are still empty positions on board. 
```
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
```
- One unit test code file, Task6AdditionalTest.java.
- During the process of finishing task6, the original test is found to be incomplete. Therefore I added some additional test
 in order to complete the test.
- The test is incomplete in a way that, it does not report on the tile at the corner of the board which connects two adjacent stations
together while there are empty spaces on the board. In the Task6AdditionalTest the last part of task 6 is remedied.
  