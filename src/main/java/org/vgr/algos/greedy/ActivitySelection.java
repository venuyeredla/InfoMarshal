package org.vgr.algos.greedy;

public class ActivitySelection {
	// Prints a maximum set of activities that can be done
    // by a single person, one at a time.
    public void printMaxActivities(int s[], int f[],int n)
    {
        int i, j;
        System.out.println(
            "Following activities are selected");
 
        // The first activity always gets selected
        i = 0;
        System.out.print(i + " ");
 
        // Consider rest of the activities
        for (j = 1; j < n; j++) {
            // If this activity has start time greater than
            // or equal to the finish time of previously
            // selected activity, then select it
            if (s[j] >= f[i]) {
                System.out.print(j + " ");
                i = j;
            }
        }
    }
}
