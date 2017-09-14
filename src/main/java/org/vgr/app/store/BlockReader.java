package org.vgr.app.store;

import org.vgr.app.domain.UserInfo;

public class BlockReader {
	
	public UserInfo readUserInfo(DataReader reader, int offset) {
		UserInfo info=new UserInfo();
		reader.seek(offset);
		info.setName(reader.readString());
	    int len=reader.readVInt();
	    int[] arr=new int[len];
		for(int i=0;i<len;i++){
			arr[i]=reader.readInt();
		}
		info.setValues(arr);
		return info;
	}

}
