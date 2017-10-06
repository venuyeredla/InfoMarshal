package org.vgr.store.ds;

import org.junit.BeforeClass;
import org.junit.Test;

public class BTreeTest {

	private static BTree bTree=null;
	
	@BeforeClass
	public static void init() {
		bTree=new BTree();
	}
	
	@Test
	public void testInsert() {
		//	RandomUtil randomUtil=new RandomUtil();
		//	int[] keys= randomUtil.randomNumbers(20, 100);
			int[] keys= {36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
			int[] searchKeys=new int[10];
			int counter=0;
				for(int i=0;i<keys.length;i++) {
					int key=keys[i];
					bTree.insert(key,0);
					if(i%3==0 && counter<10) searchKeys[counter++]=key;
				}
				System.out.println();
				bTree.traverse(bTree.root);
			}
}
