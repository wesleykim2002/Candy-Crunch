/**
 * CandyCruncher4.java
 * Version 4.0
 * @Wesley Kim
 * @10/23/2018
 * creates a code which takes a candy crunch file and plays it candy crush style
 */

import java.io.File;
import java.util.Scanner;

public class CandyCruncher4{
  public static void main(String[]args) throws Exception{
    //creating all the necessary variables
    Boolean win = false;
    int swapVer, score, solution, scoreFinal, move;
    int highestX1, highestY1, highest, highestX2, highestY2;
    score = 0;
    scoreFinal = 0;
    solution = 0;
    highest = 0;
    highestX1 = 0;
    highestY1 = 0;
    highestX2 = 0;
    highestY2 = 0;
    move = 0;
    String letter1, letter2;
    
    //input file name
    System.out.println("Enter the name of the file you would like to use (include the file type eg. '.txt'): ");
    Scanner myScanner = new Scanner (System.in);
    String fileName = myScanner.nextLine();
    
    //loads board from file
    String [][]board = boardLoad(fileName);
    
    //creates the visited array
    int [][]visited = new int [board.length][board[0].length]; 
    visited = visitedReset(visited);
    
    
    //draws board
    System.out.println("GAME BOARD:");
    System.out.println("");
    drawBoard(board);
    System.out.println("");
    System.out.println("------------------------------");
    
    //----------game code----------//
    while (win == false){
      int x,y;
      highest = 0;
      swapVer = 0;
      move++;
      System.out.println("Move Number: "+move);
      System.out.println("");
      
      //-----finding all possible valid swaps-----//
      System.out.println("Possible moves: ");
      
      //finding the number of horizontal solutions
      for (int i=0; i<board.length; i++){
        for (int j=0; j<board[0].length-1; j++){
          visited = visitedReset(visited);
          swapVer = 0;
          x = i;
          y = j;
          
          //swaps are executed and verified
          board = swapLocation(board,x,y,x,y+1);
          letter1 = board[x][y];
          letter2 = board[x][y+1];
          swapVer = swapVerify(board,letter2,x,y+1,visited);
          
          //calculates the score that can be received from the swap
          if (!letter1.equals(letter2)){
            visited = visitedReset(visited);
            int temp = swapVerify(board,letter1,x,y,visited);
            if ((temp >= 3)&&(swapVer>=3)) {
              swapVer += temp;
            }else if ((temp>=3)&&(swapVer<3)){
              swapVer = temp;
            }
          }
          
          //output
          if (swapVer>=3){
            solution++;
            System.out.println("We could swap ("+x+","+y+") and ("+x+","+(y+1)+") to receive "+swapVer+" points");
            if (swapVer>highest){
              highest = swapVer;
              highestX1 = x;
              highestY1 = y;
              highestX2 = x;
              highestY2 = y+1;
            }
          }
          //undoes the swap to check for more possibilities
          board = swapLocation(board,x,y+1,x,y);
        }
      }
      
      //finding the number of vertical solutions
      for (int i=0; i<board.length-1; i++){
        for (int j=0; j<board[0].length; j++){
          visited = visitedReset(visited);
          swapVer = 0;
          x = i;
          y = j;
          
          //swaps are executed and verified
          board = swapLocation(board,x,y,x+1,y);
          letter1 = board[x][y];
          letter2 = board[x+1][y];
          swapVer = swapVerify(board,letter2,x+1,y,visited);
          
          //calculates the score that can be received from the swap
          if (!letter1.equals(letter2)){
            visited = visitedReset(visited);
            int temp = swapVerify(board,letter1,x,y,visited);
            if ((temp >= 3)&&(swapVer>=3)) {
              swapVer += temp;
            }else if ((temp>=3)&&(swapVer<3)){
              swapVer = temp;
            }
          }
          
          //output
          if (swapVer>=3){
            solution++;
            System.out.println("We could swap ("+x+","+y+") and ("+(x+1)+","+y+") to receive "+swapVer+" points");
            if (swapVer>highest){
              highest = swapVer;
              highestX1 = x;
              highestY1 = y;
              highestX2 = x+1;
              highestY2 = y;
            }
          }
          //undoes the swap to check for more possibilities
          board = swapLocation(board,x+1,y,x,y);
        }
      }
      System.out.println("");
      
      //visited array must be reset every time to check the validity of swaps in the next loop
      visited = visitedReset(visited);
      
      if (highest != 0){//the code is highest != 0 and not highest>=3 becuase highest will only be given a number greater than or equal to 3
        //finds the highest scoring swap and deletes the connected letters
        board = swapLocation(board,highestX1,highestY1,highestX2,highestY2);
        letter1 = board[highestX1][highestY1];
        letter2 = board[highestX2][highestY2];
        swapVer = swapVerify(board,letter2,highestX2,highestY2,visited);
        if (swapVer>=3){
          board = destroy(board,visited);
        }
        //destroys connected letters
        if (!letter1.equals(letter2)){
          visited = visitedReset(visited);
          int temp = swapVerify(board,letter1,highestX1,highestY1,visited);
          if ((temp >= 3)&&(swapVer>=3)) {
            swapVer += temp;
            board = destroy(board,visited);
          }else if ((temp>=3)&&(swapVer<3)){
            swapVer = temp;
            board = destroy(board,visited);
          }
        }
        //output
        System.out.println("Best move: ");
        System.out.println("We could swap ("+highestX1+","+highestY1+") and ("+highestX2+","+highestY2+") to receive "+highest+" points");
        System.out.println("");
      }else{
        //there is no solution of the highest possible score is not valid
        solution = 0;
      }
      
      //the letters fall and the board is drawn
      board = gravity(board);
      drawBoard(board);

      //output for this loop
      score = highest;
      System.out.println("Score for this round: "+score);
      System.out.println("Number of solutions: "+solution);
      scoreFinal+=score;
      score = 0;
      
      //if there are no possible valid swaps, the code will stop
      if (solution==0){
        win = true;
      }
      solution = 0;
      
      System.out.println("------------------------------");
      
    }
    //final output
    System.out.println("Game Over");
    System.out.println("Number of valid moves: "+(move-1));
    System.out.println("Final Score: "+scoreFinal);
    
    myScanner.close();
  }
  
  //--------------------methods--------------------//
  
  //inputs the board text file into a 2d array
  public static String[][] boardLoad(String name) throws Exception{
    int row,column;
    File myFile = new File(name);
    Scanner input = new Scanner(myFile);
    row = input.nextInt();
    column = input.nextInt();    
    String[][]board = new String[row][column];
    for (int i=0; i<row; i++){
      String gameLine = input.next();
      for (int j=0; j<gameLine.length(); j++){
        board[i][j] = gameLine.substring(j,j+1);
      }
    }
    input.close();
    return board;
  }
  
  //outputs the board
  public static void drawBoard(String[][] game) throws Exception{
    for (int i=0; i<game.length; i++){
      for (int j=0; j<game[0].length; j++){
        System.out.print(game[i][j]);
      }
      System.out.println("");
    }
    System.out.println("");
  }
  
  //swaps two letters
  public static String[][] swapLocation(String[][]game,int x1,int y1, int x2, int y2){
    String storage;
    storage = game[x1][y1];
    game[x1][y1] = game[x2][y2];
    game [x2][y2] = storage;
    return game;
  }
  
  //verifies swap and returns how many letters are connected
  public static int swapVerify(String[][]game, String letter, int x, int y, int[][]visited){
    if ((x<0)||(x>game.length-1)||(y<0)||(y>game[0].length-1)){
      return 0;
    }else if ((visited[x][y]==1)||(visited[x][y]==2)){
      return 0;
    }else if (!game[x][y].equals(letter)){
      visited[x][y] = 1;
      return 0;
    }else if (letter.equals("-")){
      return 0;
    }else{
      int score = 1;
      visited[x][y] = 2;
      score += swapVerify(game, letter, x+1, y, visited);
      score += swapVerify(game, letter, x-1, y, visited);
      score += swapVerify(game, letter, x, y+1, visited);
      score += swapVerify(game, letter, x, y-1, visited);
      return score;
    }
  }
  
  //resets visited array
  public static int[][] visitedReset(int [][]visited){
    for (int i=0; i<visited.length; i++){
      for (int j=0; j<visited[0].length; j++){
        visited[i][j]=0;
      }
    }
    return visited;
  }
  
  //converts connected letters into "-"'s
  public static String[][] destroy(String[][]board, int[][]visited){
    for (int i=0; i<board.length; i++){
      for (int j=0; j<board[0].length; j++){
        if (visited[i][j] == 2){
          board[i][j] = "-";
          visited[i][j] = 0;
        }else if (visited[i][j] == 1){
          visited[i][j] = 0;
        }
      }
    }                       
    return board;
  }
  
  //lets letters fall
  public static String[][] gravity(String[][]board){
    int change;
    String item = "";
    do{
      change = 0;
      for (int i=0; i<board.length-1; i++){
        for (int j=0; j<board[0].length; j++){
          if (!board[i][j].equals("-")&&board[i+1][j].equals("-")){
            item = board[i][j];
            board[i][j] = board[i+1][j];
            board[i+1][j] = item;
            change++;
          }
        }
      }
    }while ((change!=0));
    return board;
  }
}