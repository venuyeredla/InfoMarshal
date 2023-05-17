package org.vgr.algos.advanced;

import java.util.ArrayList;

import org.junit.Test;
import org.vgr.algos.greedy.ActivitySelection;
import org.vgr.algos.greedy.JobSequence;
import org.vgr.algos.greedy.MinMaxProduct;

public class Greedy {



	@Test
	public void testActivitySelection()
    {
		ActivitySelection aSelection=new ActivitySelection();
        int s[] = { 1, 3, 0, 5, 8, 5 };
        int f[] = { 2, 4, 6, 7, 9, 9 };
        int n = s.length;
 
        // Function call
        aSelection.printMaxActivities(s, f, n);
    }
	
	

	@Test
	public void jobSequence()
    {
		  ArrayList<JobSequence> arr = new ArrayList<JobSequence>();
	        arr.add(new JobSequence('a', 2, 100));
	        arr.add(new JobSequence('b', 1, 19));
	        arr.add(new JobSequence('c', 2, 27));
	        arr.add(new JobSequence('d', 1, 25));
	        arr.add(new JobSequence('e', 3, 15));
	 
	        System.out.println(
	            "Following is maximum profit sequence of jobs");
	 
	        JobSequence job = new JobSequence();
	 
	        // Function call
	        job.printJobScheduling(arr, 3);

    }
	
	@Test
	 public void minProdut()
	    {
		 MinMaxProduct minMaxProduct=new MinMaxProduct();
	        int a[] = { -1, -1, -2, 4, 3 };
	        int n = 5;
	 
	        System.out.println(minMaxProduct.minProductSubset(a, n));
	        
	        
	         int a2[] = {-1, -1, -2, 4, 3};
	         n = a2.length;
	        System.out.println(minMaxProduct.maxProductSubset(a2, n));

	    }
	
	
	
}
