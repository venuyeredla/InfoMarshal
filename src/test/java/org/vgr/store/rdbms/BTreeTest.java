package org.vgr.store.rdbms;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.store.ds.Page;
import org.vgr.util.RandomUtil;

public class BTreeTest {
	private static BTree bTree=null;
	static String index="";
	@Test
	@Ignore
	public void testInsert() {
			HashSet<Integer> keySet=RandomUtil.randomNumsSet(2000, 10000);
			bTree=new BTree(index);
	/*	    for (Integer integer : keySet) {
				   bTree.insert(integer,0);
			     }
		      bTree.close();*/
			  bTree.insert(2080,0);
			  bTree.close();
		      System.out.println("Total keys inserted : "+keySet.size());
		   //   bTree.traverse(bTree.rootPage);
		   
		  }
	@Test
	@Ignore
	public void testTravrse() {
			  bTree=new BTree(index);
		      bTree.traverse(bTree.root);
		  }
	@Test
	public void testSerach() {
		bTree=new BTree(index);
		HashSet<Integer> keySet=RandomUtil.randomNumsSet(20, 10000);
		String searchKeys= keySet.stream().map(num->""+num).collect(Collectors.joining(","));
		System.out.println("Keys : "+searchKeys);
		for (Integer integer : keySet) {
			  Page result=bTree.search(bTree.root, integer);
			  if(result!=null) {
				  System.out.print("Key : "+ integer +" Page : "+result.getId() +" -> Keys : "+result.getKeys()+"\n");
			  }
			  
		   }
		
	}
	@Test
	@Ignore
	public void testReadPage() {
		 bTree=new BTree(index);
		Page page= bTree.readPage(45);
		System.out.println("Page : "+page.getId() +" -> Keys : "+page.getKeys());
	}
	
}
