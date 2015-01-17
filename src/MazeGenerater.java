import hsa.Console;
import java.awt.*;

public class MazeGenerater {
     
     static Console c = new Console(30,96,12);
     
     public static void main(String[] args) throws InterruptedException {
          
          Maze maze; 
          char movement;
          boolean atExit = false;
          
          maze = new Maze(c.getWidth(),c.getHeight(),10,c,false);
          
          maze.makeMaze();
          maze.drawMaze();
          
          while (atExit == false) {
               movement = c.getChar();
               switch (movement){
                 case 'w':
                   atExit = maze.setPlayerPos(0,-1);
                   break;
                 case 'a':
                   atExit = maze.setPlayerPos(-1,0);
                   break;
                 case 's':
                   atExit = maze.setPlayerPos(0,1);
                   break;
                 case 'd':
                   atExit = maze.setPlayerPos(1,0);
                   break;
                 case 'z':
                   maze.debugMethod();
                   break;
               }
          }
          
          c.println("CONTRATS!");
          
     }
     
}
