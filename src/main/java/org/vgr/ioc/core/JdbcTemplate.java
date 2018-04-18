package org.vgr.ioc.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;

@Service(id="jdbcTemplate")
public class JdbcTemplate {
	public static final Logger logger=LoggerFactory.getLogger(JdbcTemplate.class);
	public JdbcTemplate(){ }
   /**
    *  If it is DML gives the number of rows affected otherwise it gives zero(0) for DDL.
    * @param sql   SQL Query either DML or DDL .
    * @return
   */
	
   public int executeUpdate(String sql){
		Statement statement=null;
		try {
			statement=getStatement(); 
			int rows=statement.executeUpdate(sql);
	     	return rows;
		  } catch (SQLException e) {
			  rollBack(statement, e);
		  }
		  finally{
			 commitClose(statement,null);
			}
		   return 0;
	}
   
   
   
/**
 * This method will execute the query for insert and will give us auto generated key
 * @param sql
 * @return	
 */
	public int executePK(String sql){
		int PK=0;
		 PreparedStatement  preparedStatement =null;
		Connection connection=null;
		try {
			 connection=getConnection();
			 connection.setAutoCommit(false);
			 preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 preparedStatement.executeUpdate();
			 ResultSet rs = preparedStatement.getGeneratedKeys();
			 if(rs.next()){
				 PK= rs.getInt(1);	
			 }
			 connection.commit();
		} catch (SQLException e) {
			 rollBack(preparedStatement, e);
		}finally{
			commitClose(preparedStatement,null);
			}
		return PK;
	}
	
	/**
	 * Can be usefule for parameterized query to the database.
	 * @param sql
	 * @param objects
	 * @return
	 */
	public int executeUpdate(String sql ,Object[] objects){
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement=getPreparedStatement(getConnection(), sql);
			for (int i = 0; i < objects.length; i++) {
				preparedStatement.setObject(i+1, objects[i]);
              }
	        int rows=preparedStatement.executeUpdate();
	     	return rows;
    		} catch (SQLException e) {
    			rollBack(preparedStatement, e);
	     	}
   		  finally{
			  commitClose( preparedStatement,null);
		    }
		return 0;
	}

	/**
	 * For getting Single object List Use This method.
	 * @param sql  this statment consist of sql query
	 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
	 * @return
	 */
	public int[] executeBatch(List<String> sqls){
		Statement statement=null;
		int[] count=null;
		try {
			statement=getStatement();
			for (String string : sqls) {
				statement.addBatch(string);
			}
		    count=statement.executeBatch();
		} catch (SQLException e) {
			rollBack(statement, e);
		}
		finally{
		commitClose(statement,null);
		}
			
		return count;
	}

	/**
	 * For getting Single object Use This method.
	 * @param sql  this statment consist of sql query
	 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
	 * @return
	 */
	
	public void queryForObj(String sql ,JdbcRowMapper<?> rowMapper){
		 ResultSet resultSet=null;
		 PreparedStatement preparedStatement=null;
		try {
			preparedStatement=getPreparedStatement(getConnection(), sql);
			resultSet=preparedStatement.executeQuery();
			if(resultSet!=null){
				rowMapper.mapRowToObj(resultSet);
			  }
				
		} catch (SQLException e) {
		  rollBack(preparedStatement, e);
		}
		
		finally{
			commitClose(preparedStatement,resultSet);
		}
	}
	
	
	public void queryForObjTemp(String sql ,JdbcRowMapper<?> rowMapper){
		 ResultSet resultSet=null;
		 PreparedStatement preparedStatement=null;
		try {
			preparedStatement=getPreparedStatement(getConnection(), sql);
			resultSet=preparedStatement.executeQuery();
			if(resultSet!=null){
				rowMapper.mapRowToObj(resultSet);
			  }
		} catch (SQLException e) {
		  rollBack(preparedStatement, e);
		}
		finally{
			commitClose(preparedStatement,resultSet);
		}
	}
	
	
	
	
/**
 * For getting Single object List Use This method.
 * @param sql  this statment consist of sql query
 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
 * @return
 */
public void queryForObjList(String sql ,JdbcRowsMpper<?> rowMapper){
	 ResultSet resultSet=null;
	 PreparedStatement preparedStatement=null;
	try {
		preparedStatement=getPreparedStatement(getConnection(), sql);
		resultSet=preparedStatement.executeQuery();
		if(resultSet!=null)
			rowMapper.mapRowsToObjList(resultSet);
		resultSet.close();
    	} catch (SQLException e) { rollBack(preparedStatement, e); }
    	finally{
		commitClose(preparedStatement,resultSet);
    	}
}

/**
 * Use This method  only for Insering data means . Only for IN parameters
 * @param procedureCall
 * @param objects
 * @return int
 */
public int callProcedure(String procedureCall,Object[] objects){
	   int rowNbrs=0;
	   CallableStatement callableStatement=null;
	try {
		callableStatement=getCallableStatement(getConnection(), procedureCall);
		for (int i = 0; i < objects.length; i++) {
			callableStatement.setObject(i+1, objects[i]);
			}
		rowNbrs=callableStatement.executeUpdate();
   	} catch (SQLException e) {
		rollBack(callableStatement, e);
	}finally{
		commitClose(callableStatement,null);
	}
			
	return rowNbrs;
}

/**
 * This method is useful for IN and OUT parameters.
 * @param procedureCall
 * @param objects
 * @return int
 */
public Map<Integer,Object> callProcWithIndex(String procedureCall,Map<Integer,Object> inputParams ,Map<Integer,Object> outMap){
	   CallableStatement callableStatement=null;
	 try {
		  callableStatement=getCallableStatement(getConnection(), procedureCall);
		 for (Integer in : inputParams.keySet()) { 
			   callableStatement.setObject(in,inputParams.get(in));
		     }
		 for (Integer out : outMap.keySet()) {
			 callableStatement.registerOutParameter(out, Types.JAVA_OBJECT);
		   }
    	 callableStatement.execute();
    	 for (Integer out : outMap.keySet()) {
			 outMap.put(out, callableStatement.getObject(out));
		   }
         } catch (SQLException e) {
     		rollBack(callableStatement, e);
     	}finally{
     		commitClose(callableStatement,null);
     	}
	return outMap;
}

public Map<String,Object> callProcedure(String procedureCall,Map<String,Object> inputParams ,Map<String,Object> outMap){
	   CallableStatement callableStatement=null;
	 try {
		  callableStatement=getCallableStatement(getConnection(), procedureCall);
		 Set<String> input= inputParams.keySet();
		 for (String in : input) { 
			   callableStatement.setObject(in,inputParams.get(in));
		     }
		 for (String out : outMap.keySet()) {
			 callableStatement.registerOutParameter(out, Types.JAVA_OBJECT);
		   }
     	 callableStatement.execute();
     	 for (String out : outMap.keySet()) {
			 outMap.put(out, callableStatement.getObject(out));
		   }
      } catch (SQLException e) {
	   		rollBack(callableStatement, e);
	   	}finally{
	   		commitClose(callableStatement,null);
	   	}
	return outMap;
}

private static DataSource dataSource=null;
@Inject(value="jdbc:mysql://localhost:3306/venugopal")
private String url=null;
@Inject(value="root")
private String user=null;
@Inject(value="venugopal")
private String password=null;

public Connection getConnection() throws SQLException{
	return dataSource==null?null:dataSource.getConnection();
}

public Statement  getStatement(){
	try {
		Connection connection=getConnection();
	    Statement statement=getConnection().createStatement();
		logger.info("Statment is created");
		connection.setAutoCommit(false);
	    logger.info("transaction AutoCommit is set to false");
	    return  statement;
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	return null;
	}

/**
 * This is finally These methods are overloaded.
 * @param connection
 * @param resultSet
 * @param statement
 */
public static void commitClose(Statement statement,ResultSet resultSet){
		try{
			if(resultSet!=null) {
				resultSet.close();
	      	}
	      	if(statement!=null){
	      		statement.getConnection().close();
				statement.close();
				//logger.info("Statement is Closed");
	      	}
	      
	      	
		}catch (Exception e) {
			e.printStackTrace();
		}
}

public void rollBack(Statement statement ,Exception e){
	  try {
		  statement.getConnection().rollback();
	     } catch (SQLException e1) {
	    	 e.printStackTrace();
	    	e1.printStackTrace();
	        }
	    e.printStackTrace();
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
     public  PreparedStatement  getPreparedStatement(Connection connection,String sql) throws SQLException{
	    PreparedStatement	preparedStatement=connection.prepareStatement(sql);
		logger.info("Statment is created");
		connection.setAutoCommit(false);
    	logger.info("transaction AutoCommit is set to false");
    	return  preparedStatement;
	}



public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}

public String getUser() {
	return user;
}

public void setUser(String user) {
	this.user = user;
}

public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
