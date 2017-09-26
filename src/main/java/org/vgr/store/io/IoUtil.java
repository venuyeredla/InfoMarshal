package org.vgr.store.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtil {
	
	
	public static DataWriter getDw(String fileName) {
		try {
		   return new DataWriter(new FileOutputStream(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	   return null;
	}
	
public static DataReader getDr(String fileName) {
	try {
		return new DataReader(new FileInputStream(fileName));
	}catch(Exception e) {
		e.printStackTrace();
	}
	return null; 	
	}

public static DataReader getDr(InputStream is) {
	try {
		return new DataReader(is);
	}catch(Exception e) {
		e.printStackTrace();
	}
	return null; 	
	}
    
	public static DataWriter getDw(OutputStream os) {
		try {
		   return new DataWriter(os);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	   return null;
	}
    
    
    
	
}
