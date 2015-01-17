import hsa.Console;
import java.awt.*;

public class MainMenu 
{
     static Console c = new Console(30,96,12);
     
     public static void main(String[] args) throws InterruptedException
     {
          Font arialP = new Font ("Arial",Font.BOLD,14);
          Font arialH = new Font ("Arial",Font.BOLD,44);
          
          c.setFont(arialP);
          
          int choice;
          int totalScore = 0;
          
          do{
               //Opening screen
               c.setColor(new Color(78,70,66));
               c.fillRect(0,0,c.getWidth(),c.getHeight());
               
               c.setColor(Color.WHITE);
               
               c.setFont(arialH);
               c.drawString("The Maze Game",170,220);
               
               c.setFont(arialP);
               c.drawString("[1] Start Game",278,260);
               c.drawString("[2] Instructions",275,280);
               c.drawString("[3] Exit Game",282,300);
               
               c.drawString("Choice:",282,353);
               
               c.setTextBackgroundColor(new Color(78,70,66));
               c.setTextColor(Color.WHITE);
               
               Thread.sleep(60); // Prevents bug where console clears itself (hopefully)
               
               c.setCursor(21,50);
               choice = readValidInt(1,2,3);
               c.clear();
               
               if(choice==3)
               {
                    System.exit(0);
               }
               else if(choice==2)
               {
                    instructions();   
               }
               else if(choice==1)
               {
                    for (int i=0; i<5; i++){
                         totalScore += game(i+1);
                    }
                    
                    c.setColor(Color.WHITE);
                    c.setFont(arialH);
                    
                    c.drawString("THE END",240,230);
                    c.fillRect(238,255,200,60);
                    
                    c.setColor(Color.GRAY);
                    c.setFont(arialP);
                    
                    c.drawString("Total Score: " + totalScore,275,280);
                    c.drawString("Press any key to continue",250,300);
                    
                    c.getChar();
                    c.clear();
                    
               }
               
          }while (choice != 3);
     }
     
     public static void instructions(){
          //instructions for the game
          c.println("Your goal is to traverse the maze and find the exit in the least amount of steps possible");
          c.println("The score for each maze completed is based on the number of steps taken to find the exit."); 
          c.println("Your end score will be based on the sum of the scores that you have accumulated throughout the game.");
          c.println("press any key to return");
          c.getChar();
          c.clear();
     }
     
     public static int readValidInt(int validNum1, int validNum2, int validNum3){
          
          String tmpInput;
          int userInt = -1;
          
          tmpInput = c.readLine();
          
          if (tmpInput.matches("[0-9]") && (userInt!=validNum1 && userInt!=validNum2 && userInt!=validNum3)){
               userInt = Integer.parseInt(tmpInput);
          }
          
          return userInt;
     }
     
     public static int game(int round) throws InterruptedException {
          
          Maze maze; 
          char movement;
          boolean atExit = false;
          
          maze = new Maze(c.getWidth(),c.getHeight(),18-round*2,c,false);
          
          c.setColor(Color.WHITE);
          c.fillRect(238,205,200,60);
          
          c.setColor(Color.GRAY);
          c.drawString("Round " + round,312,230);
          c.drawString("Press any key to continue",250,250);
          
          c.getChar();
          c.clear();
          
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
          
          c.setColor(Color.BLACK);
          c.fillRect(235,175,210,80);
          
          c.setColor(Color.GRAY);
          c.fillRect(235,180,210,70);
          
          c.setColor(Color.WHITE);
          c.drawString("You made it!",295,200);
          c.drawString("Score: " + maze.getScore(),295,220);
          
          c.drawString("Press [e] key to continue",255,240);
          
          while (c.getChar() != 'e'){
          }
          
          c.clear();
          
          return maze.getScore();
          
     }
}  
