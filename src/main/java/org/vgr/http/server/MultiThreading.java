package org.vgr.http.server;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Implementing Runnable or extending Thread.
 * 
 * Monitors (Synchronized) vs Locks (through locks package)
 * 
 * @author venugopal
 *
 */
public class MultiThreading {
	
	LinkedBlockingDeque<String> blockingDeque=new LinkedBlockingDeque<>();
	
	public void testThread() {
		
		TMessage tMessage = new TMessage("TST");
		
		Runnable runnable1= ()-> {
			IntStream.of(10,12,13,14).forEach(System.out::print);
			System.out.println();
			synchronized (tMessage) {
				tMessage.notify();
				tMessage.notifyAll();
			}
		
		};
		
		
		Runnable runnable2= ()-> {
			System.out.println();
			IntStream.of(1,2,3,4).forEach(System.out::print);
			synchronized (tMessage) {
				try {
					tMessage.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		};
		new Thread(runnable1).start();
		new Thread(runnable2).start();
		
		
		
		Callable<String> callable=()->{
			
			return null;
			
		};
		
		
		FutureTask<String> futureTask=new FutureTask<>(callable);
		
		ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
		newSingleThreadExecutor.execute(futureTask);
		
		while(!futureTask.isDone()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		newSingleThreadExecutor.shutdown();
	}
	
	
	public void testLocks() {
		
	}

}












class Producer implements Callable<String>{
	LinkedBlockingQueue<String> blockingDeque;

	public Producer(LinkedBlockingQueue<String> blockingDeque) {
		super();
		this.blockingDeque = blockingDeque;
	}

	@Override
	public String call() throws Exception {
		Stream.of("Venu","gopal","Reddy").forEach(s-> blockingDeque.add(s));
		return null;
	}
}


class Consumer implements Callable<String>{
	LinkedBlockingQueue<String> blockingDeque;

	public Consumer(LinkedBlockingQueue<String> blockingDeque) {
		super();
		this.blockingDeque = blockingDeque;
	}

	@Override
	public String call() throws Exception {
		ArrayList<String> drainedList=new ArrayList<>();
		int drainTo = blockingDeque.drainTo(drainedList);
		drainedList.stream().forEach(System.out::println);
		return null;
	}
}


 class Lock{

	  private boolean isLocked = false;

	  public synchronized void lock()
	  throws InterruptedException{
	    while(isLocked){
	      wait();
	    }
	    isLocked = true;
	  }

	  public synchronized void unlock(){
	    isLocked = false;
	    notify();
	  }
	}


class TMessage{
	private String msg;
	Lock  lock=new Lock();
	

	public TMessage(String string) {
		// TODO Auto-generated constructor stub
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void Updatemsg(String newMsg) {
		try {
			lock.lock();
			this.msg=newMsg;
			lock.unlock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}