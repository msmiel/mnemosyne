import java.util.*;
import java.io.*;
import java.lang.*;
 
public class Search
{
	//
	// Boards are represented with 5-row by 4-column arrays.  Each cell stores
	// the dimensions of the piece in that position, such as 22 for a 2 by 2 piece.
	// The 1 by 2 piece is represented with 120 to distinguish the top half of the piece
	// from the lower half represented with 12.
	//lists
	List <Board> listOfMoves  = new LinkedList();
	List <Board> exploredNodes  = new LinkedList();
	List <Board> exploredNodes2  = new LinkedList();
	List <Board> move40List  = new LinkedList();
	List  <Board> solutionList  = new LinkedList();
	boolean isHeuristicSearch;
	int SizeExp1=0;
	int SizeExp2=0;
	int SizeMl=0;
	FileWriter fstream;
	BufferedWriter out;
	//
	public  void  BidirectionalSearch() 
	{
		int [][] startLocations = new int [][] {{120,22,22,120},{12,22,22,12},{120,21,21,120},{12,11,11,12},{11,0,0,11} };
		// Initialize a Board structure with the starting configuration.
		Board initialConf = new Board();
		initialConf.moveNumber = 0;
		initialConf.heurMeasure = 0;
		initialConf.parent = null;
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
				initialConf.board[i][j] = startLocations[i][j];
		
		listOfMoves.add(initialConf);    
		int listSize = listOfMoves.size();    
		System.out.println("Initial value for listSize variable: "+ listSize); 
		   
		    Board currentConf=new Board();    
		    Board tempNode=new Board();    
		    double totalMeasure = 0;    
		    double minHeuristic = 100;    
		    int indexOfMin = 0;    
		    boolean printRequired;    
		    boolean stopFlag = false;    
		    boolean isFirstEmpty = true;    
		    int em1Row=0;
		    int em1Col=0;
		    int em2Row=0;
		    int	em2Col=0;
		    int firstEmIndex=0;
		    int p=0;
		    boolean solutionFound=false;
	    isHeuristicSearch = false;
		 
		// Bidirectional Search Step 1:
		// Breadth-first search until moveNumber 40.  All nodes with moveNumber 40 are put on move40List.
		//for (Board Lst: listOfMoves)
		long start = System.currentTimeMillis();
		System.out.println("Start time is :"+start+"\n"); 
			while ( !listOfMoves.isEmpty())
			{
				currentConf=listOfMoves.get(0);
				listSize = listOfMoves.size();
				
				// Assign the first node in listOfMoves to the currentConf.
				exploredNodes.add(currentConf);
				listOfMoves.remove(currentConf);
				
				// If the moveNumber is 40, the node is placed on move40List without generating further moves.
				if ( currentConf.moveNumber ==40 )
				{
						move40List.add(currentConf);
						continue;
				}
				
				// Otherwise, generate new moves:
				// For-loops find the empty spaces in the board.
				for (int i = 0; i < 5; i++)
				{
					for (int j = 0; j < 4; j++)
					{
						if ( currentConf.board[i][j] == 0 )
						{
						// If this is the first empty space, the row and column are assigned to em1Row and em1Col.
										if (isFirstEmpty)
										{
										em1Row = i;
										em1Col = j;
										isFirstEmpty = false;
										}
										// Else if this is the second empty space, the row and column are assigned to em2Row and em2Col.
										else
										{
										em2Row = i;
										em2Col = j;
										}
						}
					}
				}
				
				isFirstEmpty = true;
				// If the empty spaces are adjacent horizontally, the function check2HorizSpaces() is called.
				if ( em1Row == em2Row && (em1Col + 1) < 4 && (em1Col + 1) == em2Col )
				{
					check2HorizSpaces(currentConf, em1Row, em1Col, em2Row, em2Col);
				}
				
				// Else if they are adjacent vertically, check2VertSpaces() is called.
				else if ( em1Col == em2Col && (em1Row + 1) < 5 && (em1Row + 1) == em2Row )
				{
					check2VertSpaces(currentConf, em1Row, em1Col, em2Row, em2Col);
				}
				// Else if the empty spaces are not adjacent, the check1EmptySpace() function is called for each.
				else
				{
					check1EmptySpace(currentConf, em1Row, em1Col);
					check1EmptySpace(currentConf, em2Row, em2Col);
				}
			
			
		}   // End of first while-loop and the breadth-first search.
			
		for(Board lst: move40List)
				try
				{
					fstream = new FileWriter("output.txt",true);
					out= new BufferedWriter(fstream);
					out.write("heuristic messure: "+lst.moveNumber);
					out.newLine();
						for (int m=0; m<6;m++){
						out.write("corrent node move info : "+lst.moveInfo[m]);
						out.newLine();
						}
					out.write("corrent node move number: "+lst.moveNumber);
					out.newLine();
					out.write("elements of move 40 list "+ move40List.size());
					out.newLine();
						for (int i = 0; i < 5; i++)
							for (int j = 0; j < 4; j++){
							out.write(lst.board[i][j]+" ");
							if(j==3)
							out.newLine();
							}
					
					out.write("----------------------");
					out.newLine();
					out.close();
				}
				catch(IOException e)
				{	
					System.out.println("There was a problem:" + e);			
				}
				
				
		 ///what is this?
					//Board TempNode1= new Board();
					//TempNode1 = move40List.get(0);
					//buildSolutionList(TempNode1);
					
		// Next step will be a heuristic search from the solution node back to a node in move40List.
		isHeuristicSearch = true;
		
		boolean reachedMove40 = true;
		int list40Size;
		list40Size = move40List.size();
		
		// Initialize a Board structure with the solution configuration.
		int [][] solutionLocations = new int [][] {{120,120,120,120},{12,12,12,12},{11,11,21,21},{0,22,22,11},{0,22,22,11} };
		Board solutionConf = new Board();
		solutionConf.moveNumber = 0;
		solutionConf.heurMeasure = 0;
		solutionConf.parent = null;
		for (int i = 0; i < 5; i++)
		for (int j = 0; j < 4; j++)
		solutionConf.board[i][j] = solutionLocations[i][j];
		listOfMoves.add(solutionConf);
		listSize = listOfMoves.size();
		
		// Bidirectional Search Step 2:
		// Heuristic search from the solution state to a node in move40List.
		while ( !listOfMoves.isEmpty() )
		{
			listSize = listOfMoves.size();
		
		
		// For-loop finds the node with best heuristic measure in listOfMoves.
			for (Board Lst: listOfMoves) 
			{
					totalMeasure = Lst.heurMeasure + Lst.moveNumber;
					if (totalMeasure < minHeuristic)
					{
						minHeuristic = totalMeasure;
						currentConf = Lst;
					} 
			}
			// Node with best heuristic measure is assigned to the currentConf, pushed onto the exploredNodes2 vector,
			// and popped from the listOfMoves vector.
			//currentConf = TempNode1;
			exploredNodes2.add(currentConf);
			minHeuristic = 1000;
			listOfMoves.remove(currentConf);
			// Test if currentConf has reached the same configuration as a node in move40List.
			reachedMove40 = true;
			
			for (Board Lst1: move40List)
			{
				tempNode = Lst1;
				for (int i = 0; i < 5; i++)
				for (int j = 0; j < 4; j++)
				{
					if ( Lst1.board[i][j] != currentConf.board[i][j] )
					{
					reachedMove40 = false;
					}
				}
			
				if (reachedMove40)
				{
					solutionFound = true;
					buildSolutionList(currentConf, Lst1);
					tempNode = move40List.get(move40List.size()-1);
				}
				reachedMove40 = true;
			}// end of for loop
			// If a path has been found from the solution to a node in move40List, then break out of the while-loop.
			if (solutionFound)
			{
				System.out.println("the size of Explored Node List is  : "+exploredNodes.size()+"\n");
				System.out.println("the size of  Explored 2 Node list is :"+exploredNodes2.size()+"\n");
				System.out.println(" the size of List Of Moves is: "+(listOfMoves.size()+exploredNodes.size()+exploredNodes2.size()+SizeExp1+SizeExp2+SizeMl)+"\n");
				System.out.println(" the size of List OfExplored is: "+(exploredNodes.size()+exploredNodes2.size()+SizeExp1+SizeExp2)+"\n");
				System.out.println("dublication for list of Explored nodes: "+SizeExp1+"\n" );
				System.out.println("Dublication for list of Explored 2: "+ SizeExp2+"\n");
				System.out.println(" Dublication for List Of Moves: "+SizeMl+"\n");
				long time = System.currentTimeMillis() - start;
				System.out.println(" Time to run is :"+ time/1000+" seconds. \n");
				break;
			}
		// Otherwise, generate new moves:
		// For-loops find the empty spaces in the board.
			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					if ( currentConf.board[i][j] == 0 )
					{
					// If this is the first empty space, the row and column are assigned to em1Row and em1Col.
						if (isFirstEmpty)
						{
							em1Row = i;
							em1Col = j;
							isFirstEmpty = false;
						}
						// Else if this is the second empty space, the row and column are assigned to em2Row and em2Col.
						else
						{
							em2Row = i;
							em2Col = j;
						} 
					 } 
				} 
			}
			isFirstEmpty = true;
			
		 // If the empty spaces are adjacent horizontally, the function check2HorizSpaces() is called.
			if ( em1Row == em2Row && (em1Col + 1) < 4 && (em1Col + 1) == em2Col )
			{
				check2HorizSpaces(currentConf, em1Row, em1Col, em2Row, em2Col);
			}
			// Else if they are adjacent vertically, check2VertSpaces() is called.
			else if ( em1Col == em2Col && (em1Row + 1) < 5 && (em1Row + 1) == em2Row )
			{
				check2VertSpaces(currentConf, em1Row, em1Col, em2Row, em2Col);
			}
			// Else if the empty spaces are not adjacent, the check1EmptySpace() function is called for each.
			else
			{
				check1EmptySpace(currentConf, em1Row, em1Col);
				check1EmptySpace(currentConf, em2Row, em2Col);
			}
		}   // End of second while-loop and the heuristic search from the solution to a node in move40List.
	//}  //for loop 
	}//End of bidirectional search
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// The check2HorizSpaces() function generates possible moves when the two empty spaces are
	// adjacent horizontally.
	// It has 5 parameters:
	//             currentConf:       Current board configuration.
	//             em1Row:             Row of the first empty space.
	//             em1Col:             Column of the first empty space.
	//             em2Row:             Row of the second empty space.
	//             em2Col:             Column of the second empty space.
	//////////////////////////////////////////////////////////////////////////////////////////////
	public void check2HorizSpaces(Board currentConf, int em1Row, int em1Col, int em2Row, int em2Col)
	{
		Board newConf;
		boolean isDuplicate;
		 // If the 2 by 2 piece is below the two empty spaces, a new move is generated with the 2 by 2 piece moving up one row.
		if ((em1Row+1) < 4 && currentConf.board[em1Row+1][em1Col] == 22 && (em2Row+1) < 4 && currentConf.board[em2Row+1][em2Col] == 22)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 4; j++)
					newConf.board[i][j] = currentConf.board[i][j];
			
			 // Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 22;
			newConf.board[em2Row][em2Col] = 22;
			
			newConf.board[em1Row+2][em1Col] = 0;
			newConf.board[em2Row+2][em2Col] = 0;
			
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
					// moveInfo[0] and moveInfo[1] store the dimensions of the piece: 2 by 2.
					newConf.moveInfo[0] = 2;
					 newConf.moveInfo[1] = 2;
					 // moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
					newConf.moveInfo[2] = em1Row + 1;
					 newConf.moveInfo[3] = em1Col;
					// moveInfo[2] and moveInfo[3] store the piece's row and column after the move.
					newConf.moveInfo[4] = em1Row;
					newConf.moveInfo[5] = em1Col;
					if (isHeuristicSearch)
					calculateHeuristic(newConf);
					else
					newConf.heurMeasure = 0;
					listOfMoves.add(newConf);
			}
		}
		
		// If the 2 by 2 piece is above the two empty spaces, a new move is generated with the 2 by 2 piece moving down one row.
		if ((em1Row-1) > 0 && currentConf.board[em1Row-1][em1Col] == 22 && (em2Row-1) > 0 && currentConf.board[em2Row-1][em2Col] == 22)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			newConf.board[i][j] = currentConf.board[i][j];
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 22;
			newConf.board[em2Row][em2Col] = 22;
			newConf.board[em1Row-2][em1Col] = 0;
			newConf.board[em2Row-2][em2Col] = 0;
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store the dimensions of the piece: 2 by 2.
				newConf.moveInfo[0] = 2;
				
				newConf.moveInfo[1] = 2;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row - 2;
				newConf.moveInfo[3] = em1Col;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row - 1;
				newConf.moveInfo[5] = em1Col;
				if (isHeuristicSearch)
				calculateHeuristic(newConf);
				else
				newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			}
		}
		// If the 2 by 1 piece is below the two empty spaces, a new move is generated with the 2 by 1 piece moving up one row.
		if ((em1Row+1) < 5 && currentConf.board[em1Row+1][em1Col] == 21 && (em2Row+1) < 5 && currentConf.board[em2Row+1][em2Col] == 21)
		{
			newConf = new Board();
			// System.out.println("inside check 2 horizon Spaces function 3 if");
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 4; j++)
					newConf.board[i][j] = currentConf.board[i][j];
			
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 21;
			newConf.board[em2Row][em2Col] = 21;
			newConf.board[em1Row+1][em1Col] = 0;
			newConf.board[em2Row+1][em2Col] = 0;
			
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store the dimensions of the piece: 2 by 1.
				newConf.moveInfo[0] = 2;
				newConf.moveInfo[1] = 1;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row + 1;
				newConf.moveInfo[3] = em1Col;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col;
				
				
				if (isHeuristicSearch)
					calculateHeuristic(newConf);
				else
					newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			}
		}
		// If the 2 by 1 piece is above the two empty spaces, a new move is generated with the 2 by 1 piece moving down one row.
		if ((em1Row-1)>=0 && currentConf.board[em1Row-1][em1Col] == 21 && (em2Row-1)>=0 && currentConf.board[em2Row-1][em2Col] == 21)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			newConf.board[i][j] = currentConf.board[i][j];
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 21;
			newConf.board[em2Row][em2Col] = 21;
			newConf.board[em1Row-1][em1Col] = 0;
			newConf.board[em2Row-1][em2Col] = 0;
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store the dimensions of the piece moved: 2 by 1.
				newConf.moveInfo[0] = 2;
				newConf.moveInfo[1] = 1;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row - 1;
				newConf.moveInfo[3] = em1Col;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col;
				if (isHeuristicSearch)
				calculateHeuristic(newConf);
				else
				newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
				
			}
		}
		
		// Calls to check1EmptySpace() to generate possible moves for each empty space separately.
		check1EmptySpace(currentConf, em1Row, em1Col);
		check1EmptySpace(currentConf, em1Row, em2Col);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// The check2VertSpaces() function generates possible moves when the two empty spaces are
	// adjacent vertically.
	// It has 5 parameters:
	//             currentConf:        Current board configuration.
	//             em1Row:             Row of the first empty space.
	//             em1Col:             Column of the first empty space.
	//             em2Row:             Row of the second empty space.
	//             em2Col:             Column of the second empty space.
	//////////////////////////////////////////////////////////////////////////////////////////////
	public void check2VertSpaces(Board currentConf, int em1Row, int em1Col, int em2Row, int em2Col)
	{
		Board newConf;
		boolean isDuplicate;
		// If the 2 by 2 piece can move left to the two empty spaces, a new move is generated with the 2 by 2 moving one column left.
		if ((em1Col+1) < 3 && currentConf.board[em1Row][em1Col+1] == 22 && (em2Col+1) < 3 && currentConf.board[em2Row][em2Col+1] == 22)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 4; j++)
				newConf.board[i][j] = currentConf.board[i][j];
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 22;
			newConf.board[em2Row][em2Col] = 22;
			newConf.board[em1Row][em1Col+2] = 0;
			newConf.board[em2Row][em2Col+2] = 0;
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store the dimensions of the piece moved: 2 by 2.
				newConf.moveInfo[0] = 2;
				newConf.moveInfo[1] = 2;
				
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row;
				newConf.moveInfo[3] = em1Col + 1;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col;
				if (isHeuristicSearch)
					calculateHeuristic(newConf);
				else
					newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			}
		}
		
		// If the 2 by 2 piece can move right to the two empty spaces, a new move is generated with the 2 by 2 moving one column right.
		if ((em1Col-1) > 0 && currentConf.board[em1Row][em1Col-1] == 22 && (em2Col-1) > 0 && currentConf.board[em2Row][em2Col-1] == 22)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			newConf.board[i][j] = currentConf.board[i][j];
			
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 22;
			newConf.board[em2Row][em2Col] = 22;
			newConf.board[em1Row][em1Col-2] = 0;
			newConf.board[em2Row][em2Col-2] = 0;
			
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store 2 and 2 as the dimensions of the piece moved.
				newConf.moveInfo[0] = 2;
				newConf.moveInfo[1] = 2;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row;
				newConf.moveInfo[3] = em1Col - 2;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col - 1;
				
				
				if (isHeuristicSearch)
					calculateHeuristic(newConf);
				else
					newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			} 
		}
		
		// If the 1 by 2 piece can move left to the two empty spaces, a new move is generated with the 1 by 2 moving one column left.
		if ((em1Col+1)<4 && currentConf.board[em1Row][em1Col+1] == 120 && (em2Col+1)<4 && currentConf.board[em2Row][em2Col+1] == 12)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			newConf.board[i][j] = currentConf.board[i][j];
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 120;
			newConf.board[em2Row][em2Col] = 12;
			newConf.board[em1Row][em1Col+1] = 0;
			newConf.board[em2Row][em2Col+1] = 0;
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store 1 and 2 as the dimensions of the piece moved.
				newConf.moveInfo[0] = 1;
				newConf.moveInfo[1] = 2;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row;
				newConf.moveInfo[3] = em1Col + 1;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col;
				if (isHeuristicSearch)
				calculateHeuristic(newConf);
				else
				newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			}  
		}
		
		// If the 1 by 2 piece can move right to the two empty spaces, a new move is generated with the 1 by 2 moving one column right.
		if ((em1Col-1)>=0 && currentConf.board[em1Row][em1Col-1] == 120 && (em2Col-1)>=0 && currentConf.board[em2Row][em2Col-1] == 12)
		{
			newConf = new Board();
			newConf.moveNumber = currentConf.moveNumber + 1;
			newConf.parent = currentConf;
			for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			newConf.board[i][j] = currentConf.board[i][j];
			// Board is adjusted to reflect the new move.
			newConf.board[em1Row][em1Col] = 120;
			newConf.board[em2Row][em2Col] = 12;
			newConf.board[em1Row][em1Col-1] = 0;
			newConf.board[em2Row][em2Col-1] = 0;
			// Call to checkDuplicateConf() function.
			isDuplicate = checkDuplicateConf(newConf);
			if (!isDuplicate)
			{
				// moveInfo[0] and moveInfo[1] store 1 and 2 as the dimensions of the piece moved.
				newConf.moveInfo[0] = 1;
				newConf.moveInfo[1] = 2;
				// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
				newConf.moveInfo[2] = em1Row;
				newConf.moveInfo[3] = em1Col - 1;
				// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
				newConf.moveInfo[4] = em1Row;
				newConf.moveInfo[5] = em1Col;
				if (isHeuristicSearch)
				calculateHeuristic(newConf);
				else
				newConf.heurMeasure = 0;
				listOfMoves.add(newConf);
			} 
		}
		
		// Calls to check1EmptySpace() to generate possible moves for each empty space separately.
		check1EmptySpace(currentConf, em1Row, em1Col);
		check1EmptySpace(currentConf, em2Row, em2Col);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// The check1EmptySpace() function generates possible moves for an empty space.
	// It has 3 parameters:
	//             currentConf:      Current board configuration.
	//             emRow:              Row of the empty space.
	//             emCol:              Column of the empty space.
	//////////////////////////////////////////////////////////////////////////////////////////////
	public void check1EmptySpace(Board currentConf, int emRow, int emCol)
	{
	Board newConf;
	int pieceRow=0;
	int pieceCol=0;
	int pieceType=0;
	boolean isDuplicate;
	// If there is a valid space one row below the empty space.
	if ( (emRow+1) < 5 )
	{
	newConf=new Board();
	// Identify piece below empty space.
	pieceRow = emRow + 1;
	pieceCol = emCol;
	pieceType = currentConf.board[pieceRow][pieceCol];
	// If the piece below the empty space is a 1 by 1 piece, generate a new move with the 1 by 1 moving up.
	if ( pieceType == 11 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 1 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	
	
	listOfMoves.add(newConf);
	}
	}
	
	// If the piece below the empty space is a 1 by 2 piece, generate a new move with the 1 by 2 moving up.
	if ( (emRow+2) < 5 && pieceType == 120 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol] = 12;
	newConf.board[pieceRow+1][pieceCol] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 2 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 2;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	} } }
	// If there is a valid space one row above the empty space.
	if ( (emRow-1) >= 0 )
	{
	newConf=new Board();
	// Identify piece above empty space.
	pieceRow = emRow - 1;
	pieceCol = emCol;
	
	pieceType = currentConf.board[pieceRow][pieceCol];
	// If the piece above the empty space is a 1 by 1 piece, generate a new move with the 1 by 1 moving down.
	if ( pieceType == 11 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 1 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	}  }
	// If the piece above the empty space is a 1 by 2 piece, generate a new move with the 1 by 2 moving down.
	if ( (emRow-2) >= 0 && pieceType == 12 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	
	newConf.board[pieceRow][pieceCol] = 120;
	newConf.board[pieceRow-1][pieceCol] = 0;
	// Call to checkDuplicateConf() board.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 2 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 2;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow - 1;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = pieceRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	} } }
	// If there is a valid space to the right of the empty space.
	if ( (emCol+1) < 4 )
	{
	newConf=new Board();
	// Identify piece to the right of empty space.
	pieceRow = emRow;
	pieceCol = emCol + 1;
	pieceType = currentConf.board[pieceRow][pieceCol];
	// If the piece to the right of the empty space is a 1 by 1 piece, generate a new move with the 1 by 1 moving left.
	if ( pieceType == 11 )
	{
	//  newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 1 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	} }
	// If the piece to the right of the empty space is a 2 by 1 piece, generate a new move with the 2 by 1 moving left.
	if ( (emCol+2) < 4 && pieceType == 21 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol+1] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 2 and 1 as the dimensions of the moved piece.
	newConf.moveInfo[0] = 2;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	
	
	listOfMoves.add(newConf);
	} } }
	
	
	// If there is a valid space one column to the left of the empty space.
	if ( (emCol-1) >= 0 )
	{
	newConf=new Board();
	// Identify piece to the left of empty space.
	pieceRow = emRow;
	pieceCol = emCol - 1;
	pieceType = currentConf.board[pieceRow][pieceCol];
	// If the piece to the left of the empty space is a 1 by 1 piece, generate a new move with the 1 by 1 moving right.
	if ( pieceType == 11 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 1 and 1 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 1;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = emRow;
	newConf.moveInfo[5] = emCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	}
	}
	
	
	// If the piece to the left of the empty space is a 2 by 1 piece, generate a new move with the 2 by 1 moving right.
	if ( (emCol-2) >= 0 && pieceType == 21 )
	{
	newConf = new Board();
	newConf.moveNumber = currentConf.moveNumber + 1;
	newConf.parent = currentConf;
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	newConf.board[i][j] = currentConf.board[i][j];
	// Board is adjusted to reflect the new move.
	newConf.board[emRow][emCol] = pieceType;
	newConf.board[pieceRow][pieceCol-1] = 0;
	// Call to checkDuplicateConf() function.
	isDuplicate = checkDuplicateConf(newConf);
	if (!isDuplicate)
	{
	// moveInfo[0] and moveInfo[1] store 2 and 1 as the dimensions of the piece moved.
	newConf.moveInfo[0] = 2;
	newConf.moveInfo[1] = 1;
	// moveInfo[2] and moveInfo[3] store the piece's row and column before the move.
	newConf.moveInfo[2] = pieceRow;
	newConf.moveInfo[3] = pieceCol - 1;
	// moveInfo[4] and moveInfo[5] store the piece's row and column after the move.
	newConf.moveInfo[4] = pieceRow;
	newConf.moveInfo[5] = pieceCol;
	if (isHeuristicSearch)
	calculateHeuristic(newConf);
	else
	newConf.heurMeasure = 0;
	listOfMoves.add(newConf);
	} }  } }
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// The checkDuplicateConf() function returns true if newMove is a duplicate of any
	// previous configuration, and if newMove has taken more moves to reach this configuration.
	//
	// If newMove is a duplicate with fewer moves, then newMove is added to listOfMoves,
	// and the deletePath() function is called to delete the less efficient path.
	// It has one parameter:
	//        newMove:      New configuration.
	//////////////////////////////////////////////////////////////////////////////////////////
	public boolean checkDuplicateConf(Board newMove)
	{
	int size_ex1_before_dub=0;
	int size_exp2_before_dub =0;
	int size_lstmove_before_dub=0;
	boolean flag = true;
	Board tempNode=new Board();
	// Check if newMove is already contained in listOfMoves.
	for (Iterator<Board> iter = listOfMoves.iterator(); iter.hasNext(); )
	{
	tempNode=iter.next();
	for (int i = 0; i < 5; i++)
	{
	for (int j = 0; j < 4; j++)
	{
	if (newMove.board[i][j] != tempNode.board[i][j])
	flag = false;
	}
	}
	
	if (flag == true)
	{
	// If newMove reached this configuration in fewer moves, call deletePath() on the duplicate in listOfMoves.
	if ( newMove.moveNumber < tempNode.moveNumber )
	{
	size_lstmove_before_dub=listOfMoves.size();
	iter.remove();
	deletePath(tempNode);
	SizeMl=SizeMl+(size_lstmove_before_dub - listOfMoves.size());
	return false;
	}
	else
	{
	return true;
	}
	}
	flag = true;
	}
	
	// If this is the breadth-first search, then check the exploredNodes vector.
	if (!isHeuristicSearch)
	{
	
	// Check if newMove is already contained in exploredNodes.
	for (Iterator<Board> iter = exploredNodes.iterator(); iter.hasNext(); )
	{
	tempNode=iter.next();
	for (int i = 0; i < 5; i++)
	{
	for (int j = 0; j < 4; j++)
	{
	if (newMove.board[i][j] != tempNode.board[i][j])
	flag = false;
	}
	}
	
	if (flag == true)
	{
	// If newMove reached this configuration in fewer moves, call deletePath() on the duplicate in exploredNodes.
	if (newMove.moveNumber < tempNode.moveNumber)
	{
	size_ex1_before_dub=exploredNodes.size();
	iter.remove();
	deletePath(tempNode);
	SizeExp1=SizeExp1+(size_ex1_before_dub-exploredNodes.size());
	return false;
	}
	else
	{
	return true;
	}
	}
	flag = true;
	} }
	// Else if this is the heuristic search, check the exploredNodes2 vector.
	else
	{
	// Check if newMove is already contained in exploredNodes2.
	tempNode= new Board();
	for (Iterator<Board> iter = exploredNodes2.iterator(); iter.hasNext(); )
	{
	
	tempNode=iter.next();
	for (int i = 0; i < 5; i++)
	{
	for (int j = 0; j < 4; j++)
	{
	if (newMove.board[i][j] != tempNode.board[i][j])
	flag = false;
	}
	}
	if (flag == true)
	{
	// If newMove reached this configuration in fewer moves, call deletePath() on the duplicate in exploredNodes2.
	if (newMove.moveNumber < tempNode.moveNumber)
	{
	size_exp2_before_dub=exploredNodes2.size();
	iter.remove();
	deletePath(tempNode);
	SizeExp2=SizeExp2+(size_exp2_before_dub - exploredNodes2.size());
	return false;
	}
	else
	{
	return true;
	} }
	
	flag = true;
	}  }
	
	
	return false;
	
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	// The deletePath() function deletes less efficient paths when a duplicate configuration
	// is found by the checkDuplicateConf() function.
	// It has one parameter:
	//        deleteNode:  Node to be deleted.
	///////////////////////////////////////////////////////////////////////////////////////////
	public void deletePath(Board deleteNode)
	{
	Board TempNode22;
	// Check listOfMoves for nodes which are descendants of deleteNode.
	if ( !listOfMoves.isEmpty())
	{
	TempNode22= new Board();
	for (Iterator<Board> iter = listOfMoves.iterator(); iter.hasNext(); )
	{
	TempNode22=iter.next();
	if ( TempNode22.parent != null && TempNode22.parent == deleteNode )
	{
	iter.remove();
	deletePath(TempNode22);
	}
	}
	}
	
	// If this is the breadth-first search, check exploredNodes for nodes which are descendants of deleteNode.
	if ( !isHeuristicSearch && !exploredNodes.isEmpty())
	{
	for(Board Lst:exploredNodes)
	{
	// TempNode22=iter.next();
	if ( Lst.parent != null && Lst.parent == deleteNode )
	{
	exploredNodes.remove(Lst);
	deletePath(Lst);
	} } }
	
	// If this is the heuristic search, check exploredNodes2 for nodes which are descendants of deleteNode.
	if ( isHeuristicSearch && !exploredNodes2.isEmpty() )
	{
	for (Iterator<Board> iter = listOfMoves.iterator(); iter.hasNext(); )
	{
	TempNode22=iter.next();
	// for (Board Lst:exploredNodes2)
	
	if ( TempNode22.parent != null && TempNode22.parent == deleteNode )
	{
	//if(!exploredNodes2.isEmpty())
	iter.remove();
	deletePath(TempNode22);
	}
	
	}
	}
	
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// The printSolution() function prints information about a configuration and the sequence
	// of moves leading to this configuration.
	// It has two parameters:
	//        printNode:   Configuration to be printed.
	//        printRequired: A boolean flag set to true when printing the entire sequence of
	//                       moves, and set to false when printing only the configuration.
	/////////////////////////////////////////////////////////////////////////////////////////
	public  void printSolution(Board printNode, boolean printRequired) {
	Board tempNode=new Board();
	int solutionSize;
	try {
	fstream = new FileWriter("output.txt");
	out= new BufferedWriter(fstream);
	// Print out the configuration.
	out.write("Final configuration: ");
	out.newLine();
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 4; j++)
	{
	if ( printNode.board[i][j] == 11 ){
	out.write("The 1 by 1 piece is located at row "+i+" and column " +j +" ");
	out.newLine();}
	else if ( printNode.board[i][j] == 21 && (j+1) < 4 && printNode.board[i][j+1] == 21 ){
	out.write("The 2 by 1 piece is located at row "+i+ " and column "+j+" ");
	out.newLine();}
	else if ( printNode.board[i][j] == 120 ){
	out.write("The 1 by 2 piece is located at row "+i+" and column "+j);
	out.newLine();}
	else if ( printNode.board[i][j] == 22 && (i+1) < 5 && printNode.board[i+1][j] == 22
	&& (j+1) < 4 && printNode.board[i][j+1] == 22 ){
	out.write("The 2 by 2 piece is located at row "+i+" and column "+j);
	out.newLine();}
	}
	out.write( "\n\n");
	out.newLine();
	// If the printRequired flag is set to true, print out the entire sequence of moves.
	if (printRequired)
	{
	solutionSize = solutionList.size();
	
	out.write( "At the end of the program, the size of the solutionList is "+ solutionSize+ "\n\n");
	out.newLine();
	out.write("\n\nThe sequence of moves leading to this configuration is:\n\n");
	out.newLine();
	// For-loop prints out the sequence of moves.
	for (int i = 1; i < solutionSize - 1; i++)
	{
	out.write("Move "+solutionList.get(i).moveNumber + ": The ");
	//out.newLine();
	out.write( solutionList.get(i).moveInfo[0] + " by "+solutionList.get(i).moveInfo[1]);
	//out.newLine();
	out.write(" piece was moved from row ");
	// out.newLine();
	
	// If the moveNumber is 40 or less, moveInfo[2] and moveInfo[3] are the starting row and column for the piece,
	// and moveInfo[4] and moveInfo[5] are the new row and column after the move.
	if (solutionList.get(i).moveNumber <= 40)
	{
	out.write(""+solutionList.get(i).moveInfo[2]+"");
	//out.newLine();
	out.write( " and column " +solutionList.get(i).moveInfo[3]);
	//out.newLine();
	out.write( " to row "+ solutionList.get(i).moveInfo[4]);
	//out.newLine();
	out.write( " and column " + solutionList.get(i).moveInfo[5]);
	out.newLine();
	//outfile << endl << endl;
	}
	
	// Else if moveNumber is more than 40, moveInfo[4] and moveInfo[5] are the starting row and column for the piece,
	// and moveInfo[2] and moveInfo[3] are the new row and column after the move.
	else
	{
	out.write("- "+solutionList.get(i).moveInfo[4]);
	//out.newLine();
	out.write( " and column " + solutionList.get(i).moveInfo[5]);
	//out.newLine();
	out.write( " to row " +solutionList.get(i).moveInfo[2]);
	//out.newLine();
	out.write( " and column " + solutionList.get(i).moveInfo[3]);
	out.newLine();
	// outfile << endl << endl;
	}
	}
	out.write("\n\n");
	out.newLine();
	solutionList.clear();
	}
	out.close();
	}catch(IOException e){	System.out.println("There was a problem:" + e);
	}
	}
	

	////////////////////////////////////////////////////////////////////////////////////////////
	// The calculateHeuristic() function assigns a heuristic estimate of the new move's distance
	// to the solution.
	// It has one parameter:
	//        newConf:     new board.
	////////////////////////////////////////////////////////////////////////////////////////////
	public  void calculateHeuristic(Board newConf)
	{
		int squareRow=0;
		int squareCol=0;
		int em1Row=0;
		int  em1Col=0;
		int em2Row=0;
		int em2Col=0;
		//int firstEmIndex=0;
		int pieceRow=0;
		int pieceCol=0;
		boolean isFirstEmpty = true;
		//Board tempNode=new Board();
	
		// For-loops find the row and column of the 2 by 2 square in this configuration.
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			{
				if (newConf.board[i][j] == 22 && (i+1) < 5 && newConf.board[i+1][j] == 22 && (j+1) < 4 && newConf.board[i][j+1] == 22)
				{
					squareRow = i;
					squareCol = j;
					break;
				}
			}
	
		// Heuristic is assigned by taking the 2 by 2 square's current row: Row 0 is ideal to reach the starting position.
		newConf.heurMeasure = squareRow;
		// For-loops find the empty spaces in the new board.
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 4; j++)
			{
				if ( newConf.board[i][j] == 0 )
				{
				// If this is the first empty space, the row and column are assigned to em1Row and em1Col.
					if (isFirstEmpty)
					{
					em1Row = i;
					em1Col = j;
					isFirstEmpty = false;
					}
				
				// Else if this is the second empty space, the row and column are assigned to em2Row and em2Col.
					else
					{
					em2Row = i;
					em2Col = j;
					}
				}
			}
	
		isFirstEmpty = true;
		
		// If the 2 by 2 square can move up one row, improve its heuristic score by subtracting 2.
		if ( (squareRow-1) >= 0 && em1Row == (squareRow-1) && em2Row == (squareRow-1) && em1Col == squareCol
		&& (squareCol+1) < 4 && em2Col == (squareCol+1) )
			newConf.heurMeasure -= 2.0;
		
		// Else if it can move down one row, improve its heuristic score by subtracting 0.5.
		else if ( (squareRow+2) < 5 && em1Row == (squareRow+2) && em2Row == (squareRow+2) && em1Col == squareCol
		&& (squareCol+1) < 4 && em2Col == (squareCol+1) )
			newConf.heurMeasure -= 0.5;
		// Else if it can move right one column, improve its heuristic score by subtracting 1.
		else if ( (squareRow+1) < 5 && em1Row == squareRow && em2Row == (squareRow+1) && (squareCol+2) < 4
		&& em1Col == (squareCol+2) && em2Col == (squareCol+2) )
			newConf.heurMeasure -= 1;
		// Else if it can move left one column, improve its heuristic score by subtracting 1.
		else if ( (squareRow+1) < 5 && em1Row == squareRow && em2Row == (squareRow+1) && (squareCol-1) >= 0
		&& em1Col == (squareCol-1) && em2Col == (squareCol-1) )
			newConf.heurMeasure -= 1;
		// Else if there are pieces above the 2 by 2, check possible moves for these pieces.
		else if ( (squareRow-1) >= 0 )
		{
			pieceRow = squareRow - 1;
			
				for (int i = 0; i < 2; i++)
				{
					if (i == 0)
					pieceCol = squareCol;
					else if (i == 1)
					pieceCol = squareCol + 1;
				
					// If the piece above the 2 by 2 is a 1 by 1 piece, check possible moves for the 1 by 1.
					if ( pieceCol < 4 && newConf.board[pieceRow][pieceCol] == 11 )
					{
					// If the 1 by 1 can move right or left, the heuristic estimate is improved by subtracting 0.5.
						if ( em1Row == pieceRow && (pieceCol+1) < 4 && em1Col == (pieceCol+1) )
							newConf.heurMeasure -= 0.5;
						else if ( em1Row == pieceRow && (pieceCol-1) >= 0 && em1Col == (pieceCol-1) )
							newConf.heurMeasure -= 0.5;
						else if ( em2Row == pieceRow && (pieceCol+1) < 4 && em2Col == (pieceCol+1) )
							newConf.heurMeasure -= 0.5;
						else if ( em2Row == pieceRow && (pieceCol-1) >= 0 && em2Col == (pieceCol-1) )
							newConf.heurMeasure -= 0.5;
						// If the 1 by 1 can move up, the heuristic estimate is improved by subtracting 0.5.
						if ( (pieceRow-1) >= 0 && em1Col == pieceCol && em1Row == (pieceRow-1) )
							newConf.heurMeasure -= 0.5;
						else if ( em2Col == pieceCol && (pieceRow-1) >= 0 && em2Row == (pieceRow-1) )
							newConf.heurMeasure -= 0.5;
					
					}
				
					// If the piece above the 2 by 2 is a 1 by 2 piece, check possible moves for the 1 by 2.
					else if ( pieceCol < 4 && newConf.board[pieceRow][pieceCol] == 120 )
					{
					
						// If the 1 by 2 can move up, the heuristic estimate is improved by subtracting 0.5.
						if ( em1Col == pieceCol && (pieceRow-1) >= 0 && em1Row == (pieceRow-1) )
						newConf.heurMeasure -= 0.5;
						else if ( em2Col == pieceCol && (pieceRow-1) >= 0 && em2Row == (pieceRow-1) )
						newConf.heurMeasure -= 0.5;
						
						// If the 1 by 2 can move left or right, the heuristic estimate is improved by subtracting 0.5.
						if ( em1Row == pieceRow && (pieceRow+1) < 5 && em2Row == (pieceRow+1) && (pieceCol+1) < 4
						&& em1Col == (pieceCol+1) && em2Col == (pieceCol+1) )
						newConf.heurMeasure -= 0.5;
						
						else if ( em1Row == pieceRow && (pieceRow+1) < 5 && em2Row == (pieceRow+1) && (pieceCol-1) >= 0
						&& em1Col == (pieceCol-1) && em2Col == (pieceCol-1) )
							newConf.heurMeasure -= 0.5;
					
					}
				
				
					// If the piece above the 2 by 2 is a 2 by 1 piece, check possible moves for the 2 by 1.
					else if ( pieceCol < 4 && newConf.board[pieceRow][pieceCol] == 21 )
					{
						// If the 2 by 1 can move up, the heuristic estimate is improved by subtracting 1.
						if ( (pieceRow-1) >= 0 && em1Row == (pieceRow-1) && em2Row == (pieceRow-1) && em1Col == pieceCol
						&& (pieceCol+1) < 4 && em2Col == (pieceCol+1) )
								newConf.heurMeasure -= 	1;
						else
						{
							// If the 2 by 1 can move left or right, the heuristic estimate is improved by subtracting 0.5.
							if ( em1Row == pieceRow && (pieceCol+2) < 4 && em1Col == (pieceCol+2) )
								newConf.heurMeasure -= 0.5;
							else if ( em1Row == pieceRow && (pieceCol-1) >= 0 && em1Col == (pieceCol-1) )
								newConf.heurMeasure -= 0.5;
							
							if ( em2Row == pieceRow && (pieceCol+2) < 4 && em2Col == (pieceCol+2) )
								newConf.heurMeasure -= 0.5;
							else if ( em2Row == pieceRow && (pieceCol-1) >= 0 && em2Col == (pieceCol-1) )
								newConf.heurMeasure -= 0.5;
						}
				
					}
			}        // For-loop's closing brace
		} // 	else if ( (squareRow-1) >= 0 )
	
		// Multiply the heurMeasure by 100 to make it an integer value.
		newConf.heurMeasure *= 100.0;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// The buildSolutionList() function combines the two paths found by the bidirectional search
	// into a single path from the start state to a solution.
	// It has two parameters:
	//        finalNode:   Last node explored by the heuristic search.
	//        move40:      Node in move40List which is identical to finalNode.
	////////////////////////////////////////////////////////////////////////////////////////////
	public void buildSolutionList(Board finalNode, Board move40) {
		Board tempNode=new Board();
		int solutionSize;
		int count;
		List <Board>  tempList= new LinkedList();
		
		// Build the path from move40 back to the start state.
		tempList.add(move40);
		tempNode = move40.parent;
	
		while (tempNode != null)
		{
		tempList.add(tempNode);
		
		tempNode = tempNode.parent;
		}
	
		// Transfer this path from tempList to solutionList.
		for (int i = tempList.size() - 1; i >= 0; i--)
		{
		solutionList.add(tempList.get(i));
		}
	
		// Assign 41 as the moveNumber for finalNode, and build the path from finalNode to the solution state.
		finalNode.moveNumber = 41;
		solutionList.add(finalNode);
		tempNode = finalNode.parent;
		count = 2;
		
		while (tempNode != null)
		{
			tempNode.moveNumber = 40 + count;
			solutionList.add(tempNode);
			tempNode = tempNode.parent;
			count++;
		}
		
		// out.write( "The size of the solutionList is " + solutionList.size()+ ".\n\n");
		solutionSize = solutionList.size();
		// Call to printSolution() to print out the solution path.
		tempNode = solutionList.get(solutionSize - 1);
	
		printSolution(tempNode, true);
	}//end of build solution

	public static void main(String args[])
	{
		Search s = new Search();
		s.BidirectionalSearch();
	}
	
}// end of class

