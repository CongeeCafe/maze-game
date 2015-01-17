// Docs not finished


import hsa.Console;
import java.awt.*;

public class Maze {
     
     private int[][] maze; // Holds the maze data
     
     private int blockWidth; // Height in px of one maze block item
     private int blockHeight; // Width in px of one maxe block item
     
     private int playerPosX; // X position of player in current maze
     private int playerPosY; // Y position of player in current maze
     
     private int exitPosX; // X position of the maze exit
     private int exitPosY; // Y position of the maze exit
     
     private int steps; // The number of steps that have been taken by the player
     
     private int extraPoints = 0; // Extra points collected by collecting dots
     
     private boolean showDrawAnim; // Whether or not to animate the generation of the maze
     
     private Console c; // Console to print the maze on
     
     public Maze(int width, int height, int blockSize, Console c, boolean showDrawAnim){
          
          // Set console
          this.c = c;
          
          // Set block sizes
          this.blockWidth = blockSize;
          this.blockHeight = blockSize;
          
          // Set default player starting position
          this.playerPosX = 2;
          this.playerPosY = 1;
          
          // Set animation setting
          this.showDrawAnim = showDrawAnim;
          
          // Initialize maze array
          maze = new int[width/this.blockWidth][height/this.blockHeight];
          
          // Defaults all elements of array to 1 (wall block code)
          for (int y=0; y<maze[0].length; y++){
               for (int x=0; x<maze.length; x++){
                    maze[x][y] = 1;
               }
          }
          
          
          // Sets number of steps taken to 0
          this.steps = 0;
          
          
          
     } // End of method
     
     // Draws the current player item on screen and removes 
     public void drawNewPlayer(int oldPlayerPosX, int oldPlayerPosY){
          c.setColor(Color.WHITE);
          c.fillRect(this.blockWidth*(oldPlayerPosX),this.blockHeight*(oldPlayerPosY),this.blockWidth,this.blockHeight);
          
          c.setColor(Color.RED);
          c.fillRect(this.blockWidth*(this.playerPosX),this.blockHeight*(this.playerPosY),this.blockWidth,this.blockHeight);
     }
     
     public boolean setPlayerPos(int directionX, int directionY){
          
          int newPlayerPosX = playerPosX + directionX;
          int newPlayerPosY = playerPosY + directionY;
          
          int oldPlayerPosX = playerPosX;
          int oldPlayerPosY = playerPosY;
          
          if (this.maze[newPlayerPosX][newPlayerPosY] == 1){
               return false;
          } else if (this.maze[newPlayerPosX][newPlayerPosY] == 9){
               extraPoints ++;
          }
          
          this.playerPosX = newPlayerPosX;
          this.playerPosY = newPlayerPosY;
          
          this.maze[oldPlayerPosX][oldPlayerPosY] = 0;
          this.maze[playerPosX][playerPosY] = 2;
          
          drawNewPlayer(oldPlayerPosX,oldPlayerPosY);
          
          this.steps ++;
          
          if (playerPosX == exitPosX && playerPosY == exitPosY){
               return true;
          } else {
               return false;
          }
          
     }
     
     // Returns the score in the current maze
     public int getScore(){
          int baseScore = 100000;
          return (int)(baseScore * Math.pow(0.99,steps)) + extraPoints*500;
     }
     
     public void drawMaze(){
          
          c.setColor(new Color(145,135,116));
          
          c.fillRect(0,0,this.c.getWidth(),this.c.getHeight());
          
          for (int h=0; h<maze[0].length; h++){
               for (int w=0; w<maze.length; w++){
                    
                    if (maze[w][h] == 0){
                         c.setColor(Color.WHITE);
                         c.fillRect(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                         
                    } else if (maze[w][h] == 9){
                         c.setColor(Color.WHITE);
                         c.fillRect(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                         
                         c.setColor(Color.ORANGE);
                         c.fillOval(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                         
                    } else if (maze[w][h] == 3){
                         c.setColor(new Color(134,226,97));
                         c.fillRect(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                         
                    } else if (maze[w][h] == 2){
                         c.setColor(Color.RED);
                         c.fillRect(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                    }
                    
               }
          }
          
     } // End of drawMaze method
     
     public void makeMaze() throws InterruptedException {
          
          // Draws maze background if option to animate maze generation is true
          if (showDrawAnim == true){
               c.setColor(new Color(145,135,116));
               for (int h=0; h<maze[0].length; h++){
                    for (int w=0; w<maze.length; w++){
                         c.fillRect(this.blockWidth*(w),this.blockHeight*(h),this.blockWidth,this.blockHeight);
                    }
               }
               c.setColor(Color.WHITE);
          }
          
          mazeGen(maze.length-((maze.length+1)%2)-3,maze[0].length-((maze[0].length+1)%2)-3,2);
          
          
          // Sets default maze exit position
          this.exitPosX = maze.length-((maze.length+1)%2)-2;
          this.exitPosY = maze[0].length-((maze[0].length+1)%2)-3;
          
          maze[exitPosX][exitPosY] = 3;
          maze[this.playerPosX][this.playerPosY] = 2;
          
          setPlayerPos(0,0);
     }
     
     private void mazeGen (int x, int y, int go) throws InterruptedException {
          
          // Stores the attempted directions to expand the maze branch
          boolean up = false;
          boolean down = false;
          boolean left = false;
          boolean right = false;
          
          int direction = -1; // Randomlly generated direction to expand the maze in
          int extraChanceRandom = -1; // Places extra points on the ground if random number is 9;
          
          // Stops the expansion of the maze branch if it is not in bounds
          if (x<2 || y<2 || x>maze.length-3 || y>maze[0].length-3){
               return;
          }
          
          // Checks if any maze path is in bounds and adjacent to the new path - stopping the branch if there is,
          // or making the positions as a path if there isn't
          if (x <= maze.length-3 && x >= 2 && y <= maze[0].length-3 && y >= 2){
               
               if (go == 0){ // If the heading direction is up
                    
                    for (int i=-1; i<2; i++){
                         for (int j=-1; j<2; j++){
                              if (maze[x+j][y+i] == 0){
                                   return;
                              }
                         }
                    }
                    
                    maze[x][y] = 0;
                    maze[x][y+1] = 0;
                    
                    if (showDrawAnim == true){
                         c.fillRect(this.blockWidth*(x),this.blockHeight*(y+1),this.blockWidth,this.blockHeight);
                    }
                    
               } else if (go == 1){ // If the heading direction is down
                    
                    for (int i=1; i>-2; i--){
                         for (int j=-1; j<2; j++){
                              if (maze[x+j][y+i] == 0){
                                   return;
                              }
                         }
                    }
                    
                    maze[x][y] = 0;
                    maze[x][y-1] = 0;
                    
                    if (showDrawAnim == true){
                         c.fillRect(this.blockWidth*(x),this.blockHeight*(y-1),this.blockWidth,this.blockHeight);
                    }
                    
               } else if (go == 2){ // If the heading direction is left
                    
                    for (int i=-1; i<2; i++){
                         for (int j=-1; j<2; j++){
                              if (maze[x+i][y+j] == 0){
                                   return;
                              }
                         }
                    }
                    
                    maze[x][y] = 0;
                    maze[x+1][y] = 0;
                    
                    if (showDrawAnim == true){
                         c.fillRect(this.blockWidth*(x+1),this.blockHeight*(y),this.blockWidth,this.blockHeight);
                    }
                    
               } else if (go == 3){ // If the heading direction is right
                    
                    for (int i=1; i>-2; i--){
                         for (int j=-1; j<2; j++){
                              if (maze[x+i][y+j] == 0){
                                   return;
                              }
                         }
                    }
                    
                    maze[x][y] = 0;
                    maze[x-1][y] = 0;
                    
                    if (showDrawAnim == true){
                         c.fillRect(this.blockWidth*(x-1),this.blockHeight*(y),this.blockWidth,this.blockHeight);
                    }
               }
          }
          
          extraChanceRandom = 0 + (int)(Math.random() * ((22 - 0) + 1));
          
          if (extraChanceRandom == 9){
               maze[x][y] = 9;
          }
          
          if (showDrawAnim == true){
               Thread.sleep(15);
               c.fillRect(this.blockWidth*(x),this.blockHeight*(y),this.blockWidth,this.blockHeight);
               Thread.sleep(15);
          }
          
          // Try expanding maze from position until all directions are tried
          do {
               
               // Generate random direction (0-3)
               direction = 0 + (int)(Math.random() * ((4 - 0) + 1));
               
               switch (direction){
                    case 0:
                         up = true;
                         mazeGen(x, y-2, direction);
                         break;
                    case 1:
                         down = true;
                         mazeGen(x, y+2, direction);
                         break;
                    case 2:
                         left = true;
                         mazeGen(x-2, y, direction);
                         break;
                    case 3:
                         right = true;
                         mazeGen(x+2, y, direction);
                         break;
               }
               
          } while (up == false || down == false || left == false || right == false);
          
          return;
          
     } // End of mazeGen method
     
     // Remove later //
     public void debugMethod(){
          this.playerPosX = maze.length-((maze.length+1)%2)-2;
          this.playerPosY = maze[0].length-((maze[0].length+1)%2)-3;
     }
     // Remove later //
     
}
