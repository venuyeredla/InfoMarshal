package org.vgr.ioc.core;
/**
 * This interface defines methods to accessing beans from Venugopal IOC container.
 * @author venugopal
 *
 */
public interface BeanFactory {
	/**
	 * By using this method we can get bean from Venugopal Container
	 * @param beanID
	 * @return
	 */
   public Object getBean(String beanID);
   /**
    * This method gives the no of beans in container.
    * @return
    */
   public int beansSize();
   /**
    * This method is useful for testing weather bean existed or not.
    * @param beanId
    * @return
    */
   public boolean isBeanExisted(String beanId);
   
   public boolean destroy();
   
}
