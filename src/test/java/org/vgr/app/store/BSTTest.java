package org.vgr.app.store;

import org.junit.Test;
import org.vgr.app.util.RandomUtil;

public class BSTTest {
	
	@Test
	public void testBST(){
		BST bst=new BST();
		RandomUtil randomUtil=new RandomUtil();
		int[] keys= randomUtil.randomNumbers(20, 100);
		int[] searchKeys=new int[10];
		int counter=0;
		for(int i=0;i<keys.length;i++) {
			int key=keys[i];
			bst.insert(key);
			System.out.print(key+",");
			if(i%3==0 && counter<10) searchKeys[counter++]=key;
		}
		System.out.println();
		for(int j=searchKeys.length-1;j>=0;j--) {
			System.out.print(searchKeys[j]+":"+ bst.search(searchKeys[j])+" , ");
		}
	}

}
