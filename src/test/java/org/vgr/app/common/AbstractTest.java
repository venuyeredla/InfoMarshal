package org.vgr.app.common;

import org.vgr.ioc.core.BeanFactory;
import org.vgr.ioc.core.IocContainer;
import org.junit.BeforeClass;

public abstract class AbstractTest {
	protected String windows="C:\\Work\\opensource\\appdata\\";
	protected String linux="/home/venugopal/Documents/Work/io/";
	
	public static BeanFactory factory=null;
	 @BeforeClass
    public static void beforClass() {
		  factory=new IocContainer("classes.txt");
	 }
	 
	public String getPath(String fileName) {
			System.getenv().forEach((k,v)->System.out.println(k+":"+v));
			String os=System.getProperty("OS").toLowerCase();
			return os.startsWith("windows")?windows+fileName:linux+fileName;
		}
}
