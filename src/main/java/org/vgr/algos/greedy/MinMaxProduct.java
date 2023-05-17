package org.vgr.algos.greedy;


/**
 * Given array a, we have to find the minimum product possible with the subset of elements present in the array. 
 * The minimum product can be a single element also.
 * 
 * 
 * Given an array a, we have to find the maximum product possible with the subset of elements present in the array. 
 * The maximum product can be a single element also.
 * 
 * @author venugopal
 *
 */
public class MinMaxProduct {
	
	public  int minProductSubset(int a[], int n)
    {
        if (n == 1)
            return a[0];
 
        // Find count of negative numbers,
        // count of zeros, maximum valued
        // negative number, minimum valued
        // positive number and product of
        // non-zero numbers
        int negmax = Integer.MIN_VALUE;
        int posmin = Integer.MAX_VALUE;
        int count_neg = 0, count_zero = 0;
        int product = 1;
 
        for (int i = 0; i < n; i++) {
 
            // if number is zero,count it
            // but dont multiply
            if (a[i] == 0) {
                count_zero++;
                continue;
            }
 
            // count the negative numbers
            // and find the max negative number
            if (a[i] < 0) {
                count_neg++;
                negmax = Math.max(negmax, a[i]);
            }
 
            // find the minimum positive number
            if (a[i] > 0 && a[i] < posmin)
                posmin = a[i];
 
            product *= a[i];
        }
 
        // if there are all zeroes
        // or zero is present but no
        // negative number is present
        if (count_zero == n
            || (count_neg == 0 && count_zero > 0))
            return 0;
 
        // If there are all positive
        if (count_neg == 0)
            return posmin;
 
        // If there are even number except
        // zero of negative numbers
        if (count_neg % 2 == 0 && count_neg != 0) {
 
            // Otherwise result is product of
            // all non-zeros divided by maximum
            // valued negative.
            product = product / negmax;
        }
 
        return product;
    }
	
	
	public  int maxProductSubset(int a[], int n) {
        if (n == 1) {
            return a[0];
        }
 
        // Find count of negative numbers, count
        // of zeros, negative number
        // with least absolute value
        // and product of non-zero numbers
        int max_neg = Integer.MIN_VALUE;
        int count_neg = 0, count_zero = 0;
        int prod = 1;
        for (int i = 0; i < n; i++) {
 
            // If number is 0, we don't
            // multiply it with product.
            if (a[i] == 0) {
                count_zero++;
                continue;
            }
 
            // Count negatives and keep
            // track of negative number
            // with least absolute value.
            if (a[i] < 0) {
                count_neg++;
                max_neg = Math.max(max_neg, a[i]);
            }
 
            prod = prod * a[i];
        }
 
        // If there are all zeros
        if (count_zero == n) {
            return 0;
        }
 
        // If there are odd number of
        // negative numbers
        if (count_neg % 2 == 1) {
 
            // Exceptional case: There is only
            // negative and all other are zeros
            if (count_neg == 1
                    && count_zero > 0
                    && count_zero + count_neg == n) {
                return 0;
            }
 
            // Otherwise result is product of
            // all non-zeros divided by
            //negative number with
            // least absolute value.
            prod = prod / max_neg;
        }
 
        return prod;
    }

}
