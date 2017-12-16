package org.vgr.store.io;

import org.junit.Ignore;
import org.junit.Test;

public class FileUtilTest {

	@Test
	@Ignore
	public void testMemoreInfo() {
		FileUtil.getMemorInfo();
	}
	
	
	@Test
	public void getPath() {
	  String path=FileUtil.getPath("venu.db");
		System.out.println("Path is : "+path);
	}
}
