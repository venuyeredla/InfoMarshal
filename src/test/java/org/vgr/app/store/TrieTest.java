package org.vgr.app.store;

import java.util.Scanner;

import org.junit.Test;

public class TrieTest {
	@Test
	public void testInsert() {
		Trie trie=new Trie();
		trie.insert("venugopal");
		trie.insert("venu");
		trie.insert("ananya");
		trie.insert("job");
		//trie.delete("job");
		System.out.print("Enter your query term ?");
		Scanner scanner=new Scanner(System.in);
		String input=scanner.next();
		scanner.close();
		System.out.println("Does '"+input+"' exists? :: "+trie.search(input));
	}
}
	