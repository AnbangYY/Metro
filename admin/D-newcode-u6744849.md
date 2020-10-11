# New Code for Deliverable D2D

## < u6744849 > < Li Anbang >

For Deliverable D2D, I contributed the following new statements of original code:
- Added two methods which helps to find all positions of a placement sequence
- use these two methods to solve part of task 6 - avoid overlapping

```

  From Board.class:
      //this method returns the array of x-postions of a placement sequence
 12    public static int[] getPositionX(String placementString){
 13       int[] a = new int[placementString.length()/6];
 14        for(int i=0; i<placementString.length()/6; i++){
 15            int b = placementString.charAt(4+i*6);
 16           a[i]= b-48;
 17      }
 18        return a;
 19   }
      //this method returns the array of y-positions of a placement sequence
 21   public static int[] getPositionY(String placementString){
 22       int[] b = new int[placementString.length()/6];
 23       for(int i=0; i<placementString.length()/6; i++){
 24           int c = placementString.charAt(4+i*7);
 25           b[i] = c-48;
 26         }
 27       return b;
 28   }

 From Metro.class:
 141  public static boolean isPlacementSequenceValid(String placementSequence) {
 142      for(int i=0; i<placementSequence.length()/6; i++){
 143          for(int j=0; j<placementSequence.length()/6; j++) {
         // this part uses two for loops to check if there are two same x coordinates in the sequence first, then check if the 
         corresponding y-coordinate has same. If there is a pair of same x & y coordinates, then the two tiles are overlapped.
 144              if (Board.getPositionX(placementSequence).toString().charAt(i)==Board.getPositionX(placementSequence).toString().charAt(j)){
 145                 if(Board.getPositionY(placementSequence).toString().charAt(i)==Board.getPositionY(placementSequence).toString().charAt(j)){
 146                      return false;
 147                  }
        //this part checks if the tiles are overlapped with the central stations.
 148                  else if(Board.getPositionX(placementSequence).toString().charAt(i)== '3' && Board.getPositionY(placementSequence).toString().charAt(i)=='3' ){
 149                      return false;
 150                  }
 151                 else if(Board.getPositionX(placementSequence).toString().charAt(i)== '3' && Board.getPositionY(placementSequence).toString().charAt(i)=='4' ) {
 152                      return false;
 153                  }
 154                  else if(Board.getPositionX(placementSequence).toString().charAt(i)== '4' && Board.getPositionY(placementSequence).toString().charAt(i)=='3' ) {
 155                      return false;
 156                  }
 157                  else if(Board.getPositionX(placementSequence).toString().charAt(i)== '4' && Board.getPositionY(placementSequence).toString().charAt(i)=='4' ) {
 158                      return false;
 159                  }
 160                  else {
 161                      return true;
 162                  }
 163              }
 164          }
 165      }
 166        return false;
 167    }