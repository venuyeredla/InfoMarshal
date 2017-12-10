package org.vgr.store.rdbms;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.FileUtil;

public class BTreeIdxTest {
	private static final Logger LOG = LoggerFactory.getLogger(BTreeIdxTest.class);
	private static BTreeIndex bTree=null;
	static String index=FileUtil.getPath("btree.idx");
	@BeforeClass
	public static void init() {
		//FileStore fileStore=new FileStore(index);
		Store store=new MapStore();
		bTree=new BTreeIndex(store,0);
	}
	
	@Test
	public void testInsert() {
		   HashSet<Integer> keySet=RandomUtil.randomNumsSet(10, 20);
		   int i=1;
		  /* int arr[]= {13,47,9,31,6,9,11,13,30,31,16,29,47,14,16,18,23,29,10,39,42,47};
		   for(int k=0;k<arr.length;k++) {
			   bTree.insert(arr[k],i++);
		   }*/
		   
	/*	   for (Integer integer : keySet) {
			   System.out.print("("+integer+", "+(i++)+")");
			   bTree.insert(arr[k],i++);
		    }*/
		   StringBuffer stringBuffer=new StringBuffer();
	       for (Integer integer : keySet) {
	    	      int val=i++;
	    	      stringBuffer.append("("+integer+","+val+")");
	    	      bTree.insert(integer,val);
			}
            bTree.traverse();
		    System.out.println("\nTotal keys inserted : "+keySet.size());
		    System.out.println(stringBuffer);
		    
		  }
	@Test
	@Ignore
	public void testTravrse() {
		      bTree.traverse();
		  }
	
	@Test
	@Ignore
	public void testSerach() {
		HashSet<Integer> keySet=RandomUtil.randomNumsSet(20, 10000);
		String searchKeys= keySet.stream().map(num->""+num).collect(Collectors.joining(","));
		System.out.println("Keys : "+searchKeys);
		for (Integer integer : keySet) {
			  IndexNode result=bTree.search(bTree.root, integer);
			  if(result!=null) {
				  System.out.print("Key : "+ integer +" Page : "+result.getId() +" -> Keys : "+result.getKeys()+"\n");
			  }
			  
		   }
	}
	
	@AfterClass
	public static void close() {
	//	bTree.close();
	}
	
	
	
}
