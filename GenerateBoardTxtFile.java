//Program to Generate a Candy Crusher Board
// Mangat

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

class GenerateBoardTxtFile {
  public static void main(String[] args) throws Exception { 
    
    /* Vars */
    int row, col; 
    int numberOfLetters;
    
    Random randGen = new Random();
    
    /* User Input */
    Scanner keyInput = new Scanner(System.in);  
    
    System.out.println("Enter Row Size: ");
    row = keyInput.nextInt();
    System.out.println("Enter Col Size: ");
    col = keyInput.nextInt();
    System.out.println("How many characters (max 10): ");
    numberOfLetters = keyInput.nextInt();
    
    keyInput.close();
    
    /* File Output */
    String[][] board = new String[row][col];
    File txtFile = new File("new-board.txt");
    PrintWriter output = new PrintWriter(txtFile);
    
    output.println(row);
    output.println(col);
    
    for (int i = 0; i<board.length;i++) {
      for (int j = 0; j<board[0].length;j++) {
        output.print(""+(char)(((Math.abs(randGen.nextInt())) % numberOfLetters) + 65) );     
      }
      output.println("");
    }  
    output.close(); 
    
    
    //end
  }
}