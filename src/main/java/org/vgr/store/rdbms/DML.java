package org.vgr.store.rdbms;

import java.util.LinkedHashMap;
import java.util.List;

import org.vgr.store.ds.BST;
import org.vgr.store.io.DataReader;
import org.vgr.store.io.DataWriter;

public class DML {
	DataWriter idxWriter=null;
	DataReader idxReader=null;
	DataWriter dw=null;
	DataReader dr=null;
	BST bst=null;
	
	public DML(DataWriter idxWriter,DataWriter dw) {
		super();
		this.idxWriter = idxWriter;
		this.dw = dw;
	}
	
	public DML(DataReader idxReader, DataReader dr) {
		super();
		this.idxReader = idxReader;
		this.dr = dr;
	}
	
	
	public DML(DataWriter idxWriter, DataReader idxReader, DataWriter dw, DataReader dr) {
		super();
		this.idxWriter = idxWriter;
		this.idxReader = idxReader;
		this.dw = dw;
		this.dr = dr;
	}

	public void loadIndex() {
	 this.bst=new BST(false);
	  bst.readFromFile(idxReader);
	}
	
	public void intIndex() {
	  this.bst=new BST(false);
  	}
	
    public boolean insert(String tableName,LinkedHashMap<String,String> data) {
	   int id=Integer.valueOf((String)data.get("id"));
	   long pointer=dw.getFilePointer();
	   System.out.println("key-pointer:"+id+" - "+pointer);
	   this.writeData(data);
	   bst.insert(id, pointer);
	   return true;
   }
   
    public boolean writeIndex() {
    	bst.writeToFile(idxWriter);
    	return true;
    }
    
    public boolean writeData(LinkedHashMap<String,String> data) {
    	dw.writeInt(data.keySet().size());
    	data.forEach((key,val)->{
    		dw.writeString(val);
    	});
    	
    	return true;
    }
    
    public void closeWriters() {
    	dw.close();
    	/*idxWriter.close();*/
    }
   
   
   public LinkedHashMap<String,String>  select(int key) {
	     long pointer=bst.search(key);
	     if(pointer!=-1) {
	    	 System.out.println("key -pointer:"+key+":"+pointer);
	    	 dr.seek((int) pointer);
	    	 LinkedHashMap<String,String> data=new LinkedHashMap<>();
		     List<String> list= dr.readList();
		     data.put("id", list.get(0));
		     data.put("name", list.get(1));
			 data.put("sub1", list.get(2));
			 data.put("sub2", list.get(3));
			 data.put("sub3", list.get(4));
			 return data;
	     }
		 return null;
	   }
   
   
 public boolean update(String tableName) {	
	 return true;
   }

 public boolean delete(String tableName) {
	 return true;
 }
 
 
 
}
