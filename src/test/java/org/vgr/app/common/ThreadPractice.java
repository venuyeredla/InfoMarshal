package org.vgr.app.common;

import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPractice {
	static Logger logger=LoggerFactory.getLogger(ThreadPractice.class);
	
	@Test
	@Ignore
	public void waitNotifyNotifyAll(){
		
		    Message msg = new Message();
	        Reader reader = new Reader(msg);
	        new Thread(reader,"Reader1").start();
	         
	        Reader waiter1 = new Reader(msg);
	        new Thread(waiter1, "Reader2").start();
	         
	        Writer notifier = new Writer(msg,reader);
	        new Thread(notifier, "Writer").start();
	        logger.info("All the threads are started");
	}
	
	public static void main(String...strings){
		    Message msg = new Message();
	        Reader reader = new Reader(msg);
	        new Thread(reader,"Reader1").start();
	         
	       /* Reader waiter1 = new Reader(msg);
	        new Thread(waiter1, "Reader2").start();*/
	         
	        Writer notifier = new Writer(msg,reader);
	        new Thread(notifier, "Writer").start();
	        logger.info("All the threads are started");
	}

}


class Message {
    private String msg;
     
   public Message(){
   }
    public String getMsg() {
        return msg;
    }
 
    public void setMsg(String str) {
        this.msg=str;
    }
 
}

class Reader implements Runnable{
    private Message msg;
    public boolean flag;
    public Reader(Message m){
        this.msg=m;
    }
 
    @Override
    public void run() {
        flag=true;
		while (flag) {
			if(msg.getMsg()==null){
			  synchronized (msg) {
					  try {
						msg.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  }
			  }
			  System.out.println("Value Read : "+msg.getMsg());
			  msg.setMsg(null);
			  if(flag){
			  synchronized (msg) {
				 msg.notify();
			  }
			  }
       }
    }
}

class Writer implements Runnable {
	 
    private Message msg;
    private Reader reader;
     
    public Writer(Message msg, Reader reader) {
        this.msg = msg;
        this.reader=reader;
    }
 
    @Override
    public void run() {
    	
    	List<String> data=new java.util.ArrayList<String>();
    	data.add("Venu");
    	data.add("Gopal");
    	data.add("Reddy");
    	
    	@SuppressWarnings("unchecked")
		Iterator<String> iterator=data.iterator();
    	
    	while(iterator.hasNext()){
    		if(msg.getMsg()!=null){
   			  synchronized (msg) {
			     try {
						msg.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				  }
    		}
    		
    		msg.setMsg(iterator.next());
   			 synchronized (msg) {
  	               msg.notify();
  	              // msg.notifyAll();
  	            }
    		 }
    	reader.flag=false;
    }
}


