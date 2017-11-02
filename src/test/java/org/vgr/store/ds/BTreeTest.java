package org.vgr.store.ds;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.vgr.app.util.RandomUtil;

public class BTreeTest {

	private static BTree bTree=null;
	@BeforeClass
	public static void init() {
		bTree=new BTree();
	}
	@Test
	public void testInsert() {
		 	RandomUtil randomUtil=new RandomUtil();
			int[] keys= randomUtil.randomNumbers(1000, 10000);
			HashSet<Integer> keySet=new HashSet<>(); 
		   // int[] keys= {36,45,78,32,13,94,50,74,47,91,21,84,41,54,70,19,61,73,5,11,22,65,3,48,37,53}; //22
//			System.out.println("Total keys: "+keys.length);
			int ssize=20;
			int[] searchKeys=new int[ssize];
			int c=0;
			for(int i=0;i<keys.length;i++) {
				 keySet.add(keys[i]);
				 if(c<ssize&&i%50==2) {
					 searchKeys[c]=keys[i];
					 c++;
				 }
				//bTree.insert(keys[i],0);
			}
			
		    for (Integer integer : keySet) {
				      bTree.insert(integer,0);
			   }
		    System.out.println("Total keys: "+keySet.size());
		    bTree.traverse(bTree.rootPage);
            for (int i = 0; i < searchKeys.length; i++) {
            	int key=searchKeys[i];
            	if(i%4==0) System.out.println();
            	Page result=bTree.search(bTree.rootPage, key);
				if(result!=null) {
					System.out.print("Key:"+key +" Page:"+result.pageNumber+"\t");
				}else {
					System.out.print("Does not found key");
				}
			   }
			}
}
