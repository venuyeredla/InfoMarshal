package org.vgr.store.ds;

import org.junit.Test;
import org.vgr.store.io.DataWriter;
import org.vgr.store.io.IoUtil;

public class TrieTest {
	String search="/home/venugopal/Documents/Work/io/search/trie.idx";
	
	@Test
	public void testInsert() {
		
		DataWriter dataWriter=IoUtil.getDw(search);
		Trie trie=new Trie();
		trie.insert("venugopal");
		trie.insert("venu");
		trie.insert("anu");
		trie.insert("venkat");
		trie.insert("venkatesh");
		trie.insert("ananya");
		trie.insert("job");
		trie.delete("job");
		trie.writeToFile(dataWriter);
		dataWriter.close();
		/*System.out.print("Enter your query term ?");
		Scanner scanner=new Scanner(System.in);
		String input=scanner.next();
		scanner.close();
		System.out.println("Does '"+input+"' exists? :: "+trie.search(input));*/
	}
}
	