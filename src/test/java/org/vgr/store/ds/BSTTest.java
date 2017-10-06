package org.vgr.store.ds;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.RamStorage;

public class BSTTest {
	
	private static BST bst;
	private static RamStorage ramStorage;
	private static DataWriter dataWriter;
	private static DataReader dataReader;
	
	@BeforeClass
	public static  void init() {
		bst=new BST();
		ramStorage=new RamStorage();
		dataWriter=new DataWriter(ramStorage);
		dataReader=new DataReader(ramStorage);
	}
	@AfterClass
	public static  void end() {
		dataWriter.close();
		dataReader.close();
	}
	
	@Test
	public void testInsert(){
	//	RandomUtil randomUtil=new RandomUtil();
	//	int[] keys= randomUtil.randomNumbers(20, 100);
		int[] keys= {36,45,78,32,13,61,94,50,74,47,91,21,84,94,41,54,70,19,61,73};
		int[] searchKeys=new int[10];
		int counter=0;
			for(int i=0;i<keys.length;i++) {
				int key=keys[i];
				bst.insert(key,0);
				if(i%3==0 && counter<10) searchKeys[counter++]=key;
			}
			System.out.println();
			bst.traverse(Traversal.PRE);
		}
	
	@Test
	@Ignore
	public void testSerach() {
		int del=61;
	    bst.delete(del);
	    System.out.print("\n After deleting");
		bst.traverse(Traversal.PRE);
		long val=bst.search(del);
		boolean result=val==-1?false:true;
		System.out.print("\n"+del+" Exists? :"+result +" --And data is : "+val );
	}
	
	@Test
	public void testWriteRead() {
		bst.writeToStorage(dataWriter);
		BST bst=BST.readFromStorage(dataReader);
		System.out.println("\nRead from Storage");
		bst.traverse(Traversal.PRE);
	}
	
}
