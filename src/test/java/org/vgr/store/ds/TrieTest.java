package org.vgr.store.ds;

import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.Application;
import org.vgr.store.search.Trie;

public class TrieTest {
	private static final Logger LOG=LoggerFactory.getLogger(Application.class);
	static String search="/home/venugopal/Documents/Work/io/search/trie.idx";
	private static Trie trie=null;
	//private static DataWriter dataWriter=null;
	@BeforeClass
	public static  void init() {
		trie=new Trie();
		//dataWriter=IoUtil.getDw(search);
	  }
	
	@Test
	public void testInsert() {
		trie.insert("venugopal");
		trie.insert("venu");
		trie.insert("anu");
		trie.insert("venkat");
		trie.insert("venkatesh");
		trie.insert("ananya");
		trie.insert("job");
		trie.delete("job");
	/*	trie.writeToFile(dataWriter);
		dataWriter.close();*/
	}
	
	@Test
	public void testSearch() {
		Scanner scanner=new Scanner(System.in);
		System.out.print("Enter your query term ?");
		String query=scanner.next();
		while(!query.equalsIgnoreCase("-1")) {
			System.out.println("Does '"+query+"' exists? :: "+trie.search(query));
			System.out.print("\nEnter next word :: ");
			query=scanner.next();
		}
		scanner.close();
		LOG.info("Searching exited");
	}
	
}
	