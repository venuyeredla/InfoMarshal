package org.vgr.algos.advanced;

import org.junit.Test;
import org.vgr.algos.graphs.DiJkstraShortestPath;
import org.vgr.algos.graphs.GraphTraversal;
import org.vgr.algos.graphs.PrimsMST;

public class GraphsTest {
	
	
	@Test
	public void testGraphTraversal() {
		
		GraphTraversal g = new GraphTraversal(4);
		 
	        g.addEdge(0, 1);
	        g.addEdge(0, 2);
	        g.addEdge(1, 2);
	        g.addEdge(2, 0);
	        g.addEdge(2, 3);
	        g.addEdge(3, 3);
	 
	        System.out.println("Following is Breadth First Traversal "+
	                           "(starting from vertex 2)");
	 
	        g.BFS(2);
	        System.out.println("\n DFS ");
	        
	        g.DFS(2);
	}
	
	@Test
	 public void primMST()
	    {
	        /* Let us create the following graph
	        2 3
	        (0)--(1)--(2)
	        | / \ |
	        6| 8/ \5 |7
	        | /     \ |
	        (3)-------(4)
	            9         */
	        PrimsMST t = new PrimsMST();
	        int graph[][] = new int[][] { { 0, 2, 0, 6, 0 },
	                                      { 2, 0, 3, 8, 5 },
	                                      { 0, 3, 0, 0, 7 },
	                                      { 6, 8, 0, 0, 9 },
	                                      { 0, 5, 7, 9, 0 } };
	 
	        // Print the solution
	        t.primMST(graph);
	    }

	
	// Driver's code
	@Test
    public void DijkistraShortestPath()
    {
        /* Let us create the example graph discussed above
         */
        int graph[][]
            = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                            { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                            { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                            { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                            { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                            { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                            { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
                            { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                            { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
                            DiJkstraShortestPath t = new DiJkstraShortestPath();
 
        // Function call
        t.dijkstra(graph, 0);
    }

}
