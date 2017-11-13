package org.vgr.store.rdbms;

import java.util.LinkedHashMap;
import java.util.List;

import org.vgr.store.ds.Bst;
import org.vgr.store.io.Block;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;

public class DML {
	DataWriter writer=null;
	DataReader reader=null;
	Bst idx=null;
	public DML(DataWriter writer) {
		super();
		this.idx=new Bst();
		this.writer = writer;
		/*writer.write(20); //Keysize
		writer.writeInt(0);// index location
*/	}
	public DML(DataReader dataReader,int offset) {
		super();
		this.reader = dataReader;
		loadIndex(offset);
	 }
	
	public void loadIndex(int offset){
	 reader.seek(offset);
	 this.idx= Bst.readFromStorage(reader);
	}
	
    public boolean insert(String tableName,LinkedHashMap<String,String> data) {
	   int id=Integer.valueOf((String)data.get("id"));
	   long pointer=writer.getFilePointer();
	   System.out.println("k-v:"+id+" - "+pointer);
	   this.writeData(data);
	   idx.insert(id, pointer);
	   return true;
   }
   
    public boolean writeIndex() {
    	System.out.println("Index offset: "+writer.getFilePointer());
    	idx.writeToStorage(writer);
    	return true;
    }
    
    public boolean writeData(LinkedHashMap<String,String> data) {
    	Block block=new Block();
    	block.writeVInt(data.keySet().size());
    	data.forEach((key,val)->{
    		block.write(val);
    	});
    	return true;
    }
    
    public void closeWriter() {
    	writer.close();
    }
    public void closeReader() {
    	reader.close();
    }
   
   public LinkedHashMap<String,String>  select(int key) {
	     long pointer=idx.search(key);
	     if(pointer!=-1) {
	    	 reader.seek((int) pointer);
	    	 LinkedHashMap<String,String> data=new LinkedHashMap<>();
	    	 Block block=reader.readBlock(0);
		     List<String> list= block.readList();
		     data.put("pointer",  Long.toString(pointer));
		     data.put("id", list.get(0));
		     data.put("name", list.get(1));
			 data.put("sub1", list.get(2));
			 data.put("sub2", list.get(3));
			 data.put("sub3", list.get(4));
			 return data;
	     }
		 return null;
	   }
   
   
 public boolean update(int keysize) {	
	 return true;
   }

 public boolean updateIndexLocation(int offset) {
	 return true;
 }
 
 
 
}
