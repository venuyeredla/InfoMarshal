package org.vgr.algos.backtracking;

import org.junit.Test;
import org.vgr.algos.backtrack.RatMaze;
import org.vgr.algos.backtrack.Sudoku;

public class BackTracking {
	
	@Test
	public void testRatMaze() {
		{
			int maze[][] = { { 1, 0, 0, 0 },
							{ 1, 1, 0, 1 },
							{ 0, 1, 0, 0 },
							{ 1, 1, 1, 1 } };

		     int N = maze.length;
		     RatMaze rat = new RatMaze(N);
			 rat.solveMaze(maze);
		}
	}
	
	
	
	  
	
	@Test
	public void testSudoku() {
		 // Driver Code
		Sudoku sudoku=new Sudoku();
	        int grid[][] = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
	                         { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
	                         { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
	                         { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
	                         { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
	                         { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
	                         { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
	                         { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
	                         { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
	 
	        if (sudoku.solveSudoku(grid, 0, 0))
	        	Sudoku.print(grid);
	        else
	            System.out.println("No Solution exists");
	}

}
