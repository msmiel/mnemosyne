//Appendix D.5.1: A Program for the Red Donkey Puzzle written in Java
//This java program uses bidirectional search to solve the problem.
//Reference: Kose, E. (2012). Comparing AI Search Algorithms and Their Efficiency When Applied to Path Finding Problems.
//Code: Board.java
//Board Representation of Klotski Puzzle
public class Board
 {
      int moveNumber;
      int[][] board = new int[5][4]; 
      int [] moveInfo = new int[6];
      double heurMeasure;
      Board parent;     
}
