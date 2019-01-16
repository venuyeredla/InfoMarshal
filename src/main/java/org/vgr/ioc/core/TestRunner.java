package org.vgr.ioc.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.app.LogLevels;
import org.vgr.ioc.annot.Service;
import org.vgr.ioc.annot.TestConfig;

/**
 * Creates the context for Junit test cases and does Dependency Injection.
 * @author vyeredla
 *
 */
public class TestRunner extends BlockJUnit4ClassRunner{
	private static final Logger LOG=LoggerFactory.getLogger(LogLevels.class);
	private AppContext applicationTestContext=null;
	public TestRunner(Class<?> klass) throws InitializationError {
		  super(klass);
		  this.initializeTextContext(klass);
	}
	
	private void initializeTextContext(Class<?> klass) {
		String testClassName=klass.getName();
	    LOG.info("Junit test class name  :: "+testClassName);
        TestConfig testConfigAnnotation=klass.getAnnotation(TestConfig.class);
        List<String> classesToScan=Arrays.asList(testConfigAnnotation.dependents());
        Set<String> classSet=new HashSet<>(classesToScan);
        classSet.add(testClassName);
		applicationTestContext=new AppContext(()->{ return classSet;}); 
    }
	
	@Override
	 protected Statement methodBlock(FrameworkMethod method) {
	        String beanId=null;
			try {
				String testClassName = method.getDeclaringClass().getName();
				Class<?> testClass=	Class.forName(testClassName);
				Service service= testClass.getDeclaredAnnotation(Service.class);
				beanId=getBeanId(testClassName, service.value());
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Object test=applicationTestContext.getBean(beanId);
	        Statement statement = methodInvoker(method, test);	
	        statement = possiblyExpectingExceptions(method, test, statement);
	        statement = withPotentialTimeout(method, test, statement);
	        statement = withBefores(method, test, statement);
	        statement = withAfters(method, test, statement);
	        statement = withRules(method, test, statement);
	        return statement;
	    }

	private static String getBeanId(String className, String beanId) {
		 if(StringUtils.isEmpty(beanId)) {
			 className=className.substring(className.lastIndexOf(".")+1, className.length());
			 beanId=className.substring(0, 1).toLowerCase() + className.substring(1);
		 }
		return beanId;
	}

	
	 private Statement withRules(FrameworkMethod method, Object target,
	            Statement statement) {
	        List<TestRule> testRules = getTestRules(target);
	        Statement result = statement;
	        result = withMethodRules(method, testRules, target, result);
	        result = withTestRules(method, testRules, result);

	        return result;
	    }

	  private Statement withMethodRules(FrameworkMethod method, List<TestRule> testRules,
	            Object target, Statement result) {
	        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
	            if (!testRules.contains(each)) {
	                result = each.apply(result, method, target);
	            }
	        }
	        return result;
	    }

	    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
	        return rules(target);
	    }
	    private Statement withTestRules(FrameworkMethod method, List<TestRule> testRules,
	            Statement statement) {
	        return testRules.isEmpty() ? statement :
	                new RunRules(statement, testRules, describeChild(method));
	    }
}
