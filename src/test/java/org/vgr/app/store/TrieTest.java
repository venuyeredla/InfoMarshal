package org.vgr.app.store;

import org.junit.Test;

public class TrieTest {
	@Test
	public void testInsert() {
		Trie trie=new Trie();
		trie.insert("venugopal");
		trie.insert("venkat");
		trie.insert("ananya");
		trie.insert("sex");
		trie.delete("sex");
		System.out.println("Does 'sex' exists? :: "+trie.search("sex"));
	}
}
