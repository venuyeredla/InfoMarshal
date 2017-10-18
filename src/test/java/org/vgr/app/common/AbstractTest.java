package org.vgr.app.common;

import java.util.Set;

import org.junit.BeforeClass;
import org.vgr.ioc.core.BeanFactory;
import org.vgr.ioc.core.IocContainer;

public abstract class AbstractTest {
	protected String windows="C:\\Work\\opensource\\appdata\\";
	protected String linux="/home/venugopal/Documents/Work/io/";
	protected Set<String> classes;
	
	public BeanFactory factory=null;
	@BeforeClass
    public void beforClass() {
		  factory=new IocContainer(this.getClasses());
	}
	
   public abstract Set<String> getClasses();
	public String getPath(String fileName) {
			System.getenv().forEach((k,v)->System.out.println(k+":"+v));
			String os=System.getProperty("OS").toLowerCase();
	   	    return os.startsWith("windows")?windows+fileName:linux+fileName;
	}
}
