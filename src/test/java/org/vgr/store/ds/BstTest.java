package org.vgr.store.ds;

import java.util.HashSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.util.RandomUtil;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.FileUtil;
public class BstTest {
	private static final Logger LOG=LoggerFactory.getLogger(BstTest.class);
	String indexFile=FileUtil.getPath("bst.idx");
	
	public void testInsert(){
		//int[] keys= {4104, 2088, 1048};//{36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
		RandomUtil randomUtil=new RandomUtil();
		int[] keys= randomUtil.randomNumbers(500, 10000);
		HashSet<Integer> keySet=new HashSet<>();
		for(int i=0;i<keys.length;i++) {
			keySet.add(keys[i]);
		   }
		Bst bst =new Bst();
		LOG.info("No of keys :"+keySet.size());
		keySet.forEach(key->bst.insert(key, key));
		bst.traverse(Traversal.PRE);
		DataWriter	writer=new DataWriter(indexFile);
		bst.writeToStorage(writer);
		writer.close();
		LOG.info("Index saved to :"+indexFile);
	 }
	@Test
	public void testSerach() {
	 DataReader reader=new DataReader(indexFile);
	 Bst bst=Bst.readFromStorage(reader);
	 bst.traverse(Traversal.PRE);
     RandomUtil randomUtil=new RandomUtil();
	 int[] searchKeys= randomUtil.randomNumbers(20, 10000);
	 for(int i=0;i<searchKeys.length;i++) {
	 	int key=searchKeys[i];
	    long value= bst.search(key);
	    System.out.println();
	    if(value==-1) {
	    	System.out.print("Key:"+key+ " No");
	    }else {
	    	System.out.print("Key :"+key+" --> "+value );
	    }
		}
	/*	
	    bst.delete(del);
	    System.out.print("\n After deleting");
		bst.traverse(Traversal.PRE);*/
	}
}
