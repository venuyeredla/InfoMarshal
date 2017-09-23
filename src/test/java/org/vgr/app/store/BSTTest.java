package org.vgr.app.store;

import org.junit.Test;

public class BSTTest {
	@Test
	public void testBST(){
		BST bst=new BST();
	//	RandomUtil randomUtil=new RandomUtil();
	//	int[] keys= randomUtil.randomNumbers(20, 100);
		int[] keys= {36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
		int[] searchKeys=new int[10];
		int counter=0;
		for(int i=0;i<keys.length;i++) {
			int key=keys[i];
			bst.insert(key);
			System.out.print(key+",");
			if(i%3==0 && counter<10) searchKeys[counter++]=key;
		}
		System.out.print("\nPre Order :");
		bst.traverse(Traversal.PRE);
		System.out.print("\nPost Order :");
		bst.traverse(Traversal.POST);
		System.out.print("\nIn Order : ");
		bst.traverse(Traversal.IN);
		System.out.println();
		
		
		//Calling BST delete
		int del=61;
		bst.delete(del);
		System.out.print("\nPre Order : ");
		bst.traverse(Traversal.PRE);
		System.out.println();
		System.out.print(del+" exists? :"+ bst.search(del));

/*		for(int j=searchKeys.length-1;j>=0;j--) {
			System.out.print(searchKeys[j]+":"+ bst.search(searchKeys[j])+" , ");
		}
*/	}
}
