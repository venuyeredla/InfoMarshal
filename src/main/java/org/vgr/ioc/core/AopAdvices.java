package org.vgr.ioc.core;

import java.util.HashMap;

public interface AopAdvices {
	/**
	 * Before advice
	 */
  public void before();
  /**
   * After Advice
   */
  public void after();
  /**
   * After Returning Advice
   */
  public void afterReturning();
/**
 * After Throwing Advice.
 */
  public void afterThrowing();
  /**
   * Here give the method name on which you want to apply advice.
   * @return
   */
  public HashMap<String ,AopAdviceType> pointCuts();
}
