package org.vgr.store.compress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reades the text from file
 * @author vyeredla
 *
 */

public class TextReader {
	public static String getText() {
		BufferedReader bufferedReader=null;
		try {
		bufferedReader=new BufferedReader(new FileReader("./data/model.txt"));
		StringBuilder builder=new StringBuilder();
		String line = bufferedReader.readLine();
		while (line!=null) {
			builder.append(line);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return new String(builder);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	

}
