package org.vgr.ioc.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateUtils {
	   public static final Logger logger=LoggerFactory.getLogger(JDBCTemplate.class);
	/**
	 * This methdo gives the Statment
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	 public static Statement  getStatement(Connection connection) throws SQLException{
	    Statement	statement=connection.createStatement();
		logger.info("Statment is created");
		connection.setAutoCommit(false);
     	logger.info("transaction AutoCommit is set to false");
     	return  statement;
	}
	 
	 public static CallableStatement getCallableStatement(Connection connection ,String sql)throws SQLException{
		 CallableStatement callableStatement=connection.prepareCall(sql);
		 logger.info("Callable Statment is created");
		 connection.setAutoCommit(false);
	     logger.info("transaction AutoCommit is set to false");
	     return  callableStatement;
	 }
	 
	 
	 /**
	  * This methods gives the PreparedStatement.
	  * @param connection
	  * @param sql
	  * @return
	  * @throws SQLException
	  */
	      public static PreparedStatement  getPreparedStatement(Connection connection,String sql) throws SQLException{
		    PreparedStatement	preparedStatement=connection.prepareStatement(sql);
			logger.info("Statment is created");
			connection.setAutoCommit(false);
	     	logger.info("transaction AutoCommit is set to false");
	     	return  preparedStatement;
		}
	
	      public static void callRollBack(Connection connection ,Exception e){
	    	  
	    	  try {
	    		  connection.rollback();
	  			//error("excetption is occured transaction is rolled back", e);
	  		     } catch (SQLException e1) {
	  		    	e1.printStackTrace();
	  		        }
	  		    e.printStackTrace();
	      }
	      
	      /**
	       * This is finally These methods are overloaded.
	       * @param connection
	       * @param resultSet
	       * @param statement
	       */
	      public static void finallyBlock(Connection connection,Statement statement){
	    		try{
	            	if(statement!=null){
	    				statement.close();
	    			//	logger.info("Statement is Closed");
	            	}
	            	if(connection!=null){
	    				connection.close();
	    			//	logger.info("Connection  is Closed");
	            	}
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    		}
	      }
	      
	      public static void finallyBlock(Connection connection,ResultSet resultSet ,PreparedStatement preparedStatement){
	    		try{
	    			if(resultSet!=null){
	    				resultSet.close();
	    			//	logger.info("Resultset is Closed");
	    			}
	    	        	if(preparedStatement!=null){
	    				//	logger.info("PreparedStatment is Closed");
	    	        		preparedStatement.close();
	    	        	}
	    	        	if(connection!=null){
		    				connection.close();
		    			//	logger.info("Connection  is Closed");
		            	}
	    			}
	    			catch (Exception e) {
	    				e.printStackTrace();
	    			}
	      }
}
