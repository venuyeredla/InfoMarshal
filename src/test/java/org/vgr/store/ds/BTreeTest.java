package org.vgr.store.ds;

import java.util.HashSet;

import org.junit.Ignore;
import org.junit.Test;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.FileUtil;

public class BTreeTest {
	private static BTree bTree=null;
	static String index=FileUtil.getPath("btree.idx");

	@Test
	@Ignore
	public void testWriteMeta() {
		bTree=new BTree(index);
		/*bTree.writeMeta();*/
		bTree.closeWriter();
	}
	@Test
	@Ignore
	public void testRead() {
		bTree=new BTree(index);
		bTree.readMeta();
		bTree.closeReader();
	}
	@Test
	@Ignore
	public void testInsert() {
		    bTree=new BTree(index);
			HashSet<Integer> keySet=RandomUtil.randomNumsSet(1000, 10000);
		    for (Integer integer : keySet) {
				      bTree.insert(integer,0);
			   }
		    System.out.println("Total keys inserted : "+keySet.size());
		    bTree.traverse(bTree.rootPage);
		    bTree.close();
		  }
	
	@Test
	@Ignore
	public void testSerach() {
	 DataReader reader=new DataReader(index);
	 Bst bst=Bst.readFromStorage(reader);
	 bst.traverse(Traversal.PRE);
	 int[] searchKeys= RandomUtil.randomNumbers(20, 10000);
	// String str= Arrays.asList(searchKeys).stream().map(i-> i.toString()).collect(Collectors.joining(","));
	// System.out.println("\nSearc keys : "+str);
	 System.out.println("Found : ");
	 for(int i=0;i<searchKeys.length;i++) {
	 	int key=searchKeys[i];
	    long value= bst.search(key);
	    if(!(value==-1)) {
	    	System.out.print("Key :"+key+" --> "+value +"\t	");
	    }
		}
	/*	
	    bst.delete(del);
	    System.out.print("\n After deleting");
		bst.traverse(Traversal.PRE);*/
	}
	@Test
	public void testReadPage() {
		 bTree=new BTree(index);
		Page page= bTree.readPage(49);
		System.out.println("No of keys in page: "+page.getKeySize());
	}
	
}
