package org.vgr.app.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.vgr.app.domain.UserInfo;
import org.vgr.app.util.RandomUtil;
import org.junit.Ignore;
import org.junit.Test;

public class BlockReadWriteTest {
	
	private static String storaBase="C:\\Work\\opensource\\tempdata\\";
	String fileName=storaBase+"usr.store";
	
	@Test
	@Ignore
	public void testWrite() {
		
		OutputStream out;
		File file=new File(fileName);
		
			try {
				if(!file.exists())
					file.createNewFile();
				out = new FileOutputStream(file);
				DataWriter dataWriter=new DataWriter(out);
				BlockWriter blockWriter=new BlockWriter();
				UserInfo info;
				RandomUtil randomUtil=new RandomUtil();
				for(int i=0;i<20;i++){
					info=new UserInfo();
					info.setName("Name"+i);
					info.setValues(randomUtil.randomNumbers(10, 100));
					 System.out.println(info);
				    int pointer=	blockWriter.writeUserInfo(dataWriter, info);
				    System.out.println("Map : "+i +"-->"+pointer);
				}
				dataWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	public void testRead() {
		InputStream input;
		try {
			input = new FileInputStream(fileName);
			DataReader dataReader=new DataReader(input);
			BlockReader blockReader=new BlockReader();
		    UserInfo info=blockReader.readUserInfo(dataReader, 758);
		    System.out.println(info);
			dataReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
