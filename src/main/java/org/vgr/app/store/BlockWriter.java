package org.vgr.app.store;

import org.vgr.app.domain.UserInfo;

public class BlockWriter {
	
	public int writeUserInfo(DataWriter writer,UserInfo info) {
		  int pointer=writer.getFilePointer();
		  writer.writeString(info.getName());
		  int[] arr=info.getValues();
		  int len=arr.length;
		  writer.writeVint(len);
		  for(int i=0;i<len;i++){
			  writer.writeInt(arr[i]);
		    }
			  
		  return pointer;
	 }
}
