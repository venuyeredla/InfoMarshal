package org.vgr.app.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class DataIOTest {
	private static String storaBase="C:\\Work\\opensource\\tempdata\\";
	
	@Test
	public void testMapReadWrite() {
		String fileName=storaBase+"file.store";
		OutputStream out;
		InputStream input;
		try {
			File file=new File(fileName);
			if(!file.exists())
				file.createNewFile();
			out = new FileOutputStream(file);
			DataWriter dataWriter=new DataWriter(out);
			Map<String,String> map=new HashMap<String,String>();
			map.put("name", "Venugopal");
			map.put("wife", "ananya");
			map.put("Job", "qualcomm");
			map.put("brother1", "venkat");
			map.put("brother2", "Raghu");
			dataWriter.writeMap(map);
			dataWriter.close();
            //Reading data
			input = new FileInputStream(storaBase+"file.store");
			DataReader dataReader=new DataReader(input);
			dataReader.readMap().forEach((key,value)->{
				System.out.println(key +" -- "+value);
			});
			dataReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@Ignore
	public void testPointer() {
		String fileName=storaBase+"pointer.store";
		try {
			File file=new File(fileName);
			if (!file.exists())
				file.createNewFile();
			DataReader dataReader=new DataReader(new FileInputStream(file));
			DataWriter dataWriter=new DataWriter(new FileOutputStream(file));
			dataWriter.writeString("venugopal Reddy is hero");
			int position=dataWriter.getFilePointer();
			System.out.println("Pointer to 2nd : "+position);
			dataWriter.writeString("Ananya Reddy");
			dataWriter.close();
			dataReader.seek(position);
			System.out.println("2nd name : "+dataReader.readString());
			dataReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
