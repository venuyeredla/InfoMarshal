package org.vgr.ioc.web;

import org.vgr.ioc.annot.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


@Service(id="cacheManager")
public class AppCacheManager {
   public static CacheManager cacheManager=null;
   private static Cache cache=null;
   public static Logger logger=LoggerFactory.getLogger(AppListener.class);
    /**
     * 
     */
    static {
	      cacheManager=CacheManager.newInstance();
	      cache=cacheManager.getCache("sampleCache1");
        }
	
	public void put(String key, Object obj){
		Element element=new Element(key, obj);
		cache.putIfAbsent(element);
	}
	
   public Object getValue(String key){
	 Element  element=  cache.get(key);
	 return element.getObjectValue();
   }
	
}
