package org.vgr.ioc.jdbc;

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

import org.vgr.ioc.annot.Inject;
import org.vgr.ioc.annot.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(id="jdbcTemplate")
public class JDBCTemplate {
	public static final Logger logger=LoggerFactory.getLogger(JDBCTemplate.class);
	
	private static DataSource dataSource=null;
	
	@Inject(value="jdbc:mysql://localhost:3306/venugopal")
	private String url=null;
	@Inject(value="root")
	private String user=null;
	@Inject(value="venugopal")
	private String password=null;
	
	public Connection getConnection(){
		if(dataSource==null){
		   }
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JDBCTemplate(){ }
/**
 *  If it is DML gives the number of rows affected otherwise it gives zero(0) for DDL.
 * @param sql   SQL Query either DML or DDL .
 * @return
 */
	public int executeUpdate(String sql){
	    int rows=0;
		Statement statement=null;
		Connection connection=null;
		try {
			connection=getConnection();
			statement=TemplateUtils.getStatement(connection);
	     	rows=statement.executeUpdate(sql);
	     	logger.debug("No of rows affected : "+rows);
	     	connection.commit();
		  } catch (SQLException e) {
			TemplateUtils.callRollBack(connection, e);
		  }
		finally{
			TemplateUtils.finallyBlock(connection, statement);
			}
		return rows;
	}
	
/**
 * This method will execute the query for insert and will give us auto generated key
 * @param sql
 * @return	
 */
	public int executePK(String sql){
		int PK=0;
		Statement statement=null;
		Connection connection=null;
		try {
			 connection=getConnection();
			 connection.setAutoCommit(false);
			 PreparedStatement  preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 preparedStatement.executeUpdate();
			 ResultSet rs = preparedStatement.getGeneratedKeys();
			 if(rs.next()){
				 PK= rs.getInt(1);	
			 }
			 connection.commit();
		} catch (SQLException e) {
			TemplateUtils.callRollBack(connection, e);
		}
		finally{
		TemplateUtils.finallyBlock(connection, statement);
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
		int rows=0;
		PreparedStatement preparedStatement=null;
		Connection connection=null;
		try {
			connection=getConnection();
			preparedStatement=TemplateUtils.getPreparedStatement(connection, sql);
			for (int i = 0; i < objects.length; i++) {
				preparedStatement.setObject(i+1, objects[i]);
              }
	     	rows=preparedStatement.executeUpdate();
	     	connection.commit();
    		} catch (SQLException e) {
		    	TemplateUtils.callRollBack(connection, e);
	     	}
   		  finally{
			  TemplateUtils.finallyBlock(connection, preparedStatement);
		    }
		return rows;
	}

	/**
	 * For getting Single object List Use This method.
	 * @param sql  this statment consist of sql query
	 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
	 * @return
	 */
	public int[] executeBatch(List<String> sqls){
		Statement statement=null;
		Connection connection=null;
		int[] count=null;
		try {
			connection=getConnection();
			statement=TemplateUtils.getStatement(connection);
			
			for (String string : sqls) {
				statement.addBatch(string);
			}
		    count=statement.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			TemplateUtils.callRollBack(connection, e);
		}
		finally{
		TemplateUtils.finallyBlock(connection, statement);
		}
			
		return count;
	}

	/**
	 * For getting Single object Use This method.
	 * @param sql  this statment consist of sql query
	 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
	 * @return
	 */
	
	public Object queryForObj(String sql ,RowMapper<?> rowMapper){
		 Object object=null;
		 ResultSet resultSet=null;
		 PreparedStatement preparedStatement=null;
		 Connection connection=null;
		try {
			connection=getConnection();
			preparedStatement=TemplateUtils.getPreparedStatement(connection, sql);
			resultSet=preparedStatement.executeQuery();
		
			if(resultSet!=null){
				 object =rowMapper.mapRowToObj(resultSet);
			  }
			connection.commit();
				
		} catch (SQLException e) {
		  TemplateUtils.callRollBack(connection, e);
		}
		
		finally{
			TemplateUtils.finallyBlock(connection, resultSet, preparedStatement);
		}
		return object;
	}
	
	
/**
 * For getting Single object List Use This method.
 * @param sql  this statment consist of sql query
 * @param rowMapper RowMapper Implementation class to read data from ResultSet and stuffing it into Object
 * @return
 */
public List<?> queryForObjList(String sql ,RowsMpper<?> rowMapper){
	 List<?> object=null;
	 ResultSet resultSet=null;
	 PreparedStatement preparedStatement=null;
	 Connection connection=null;
	try {
		connection=getConnection();
		preparedStatement=TemplateUtils.getPreparedStatement(connection, sql);
		resultSet=preparedStatement.executeQuery();
		if(resultSet!=null)
			object =(List<?>)rowMapper.mapRowsToObjList(resultSet);
		connection.commit();
    	} catch (SQLException e) { TemplateUtils.callRollBack(connection, e); }
    	finally{
		TemplateUtils.finallyBlock(connection, resultSet, preparedStatement);
    	}
	return object;
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
	   Connection connection=null;
	try {
		connection=getConnection();
		callableStatement=TemplateUtils.getCallableStatement(connection, procedureCall);
		
		for (int i = 0; i < objects.length; i++) {
			callableStatement.setObject(i+1, objects[i]);
			}
		rowNbrs=callableStatement.executeUpdate();
		
		connection.commit();
			
	} catch (SQLException e) {
		TemplateUtils.callRollBack(connection, e);
	}
	
	finally{
		TemplateUtils.finallyBlock(connection, callableStatement);
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
	   Connection connection=null;
	 try {
		 connection=getConnection();
		  callableStatement=TemplateUtils.getCallableStatement(connection, procedureCall);
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
    	 
    	 connection.commit();

         } catch (SQLException e) {
		    TemplateUtils.callRollBack(connection, e);
	     }
	 finally{
		TemplateUtils.finallyBlock(connection, callableStatement);
	  }
	return outMap;
}

public Map<String,Object> callProcedure(String procedureCall,Map<String,Object> inputParams ,Map<String,Object> outMap){
	   CallableStatement callableStatement=null;
	   Connection connection=null;
	 try {
		 connection=getConnection();
		  callableStatement=TemplateUtils.getCallableStatement(connection, procedureCall);
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
     	 connection.commit();
      } catch (SQLException e) {
		    TemplateUtils.callRollBack(connection, e);
	     }
	 finally{
		TemplateUtils.finallyBlock(connection, callableStatement);
	  }
	return outMap;
}


public Set<?> queryForObjser(String sql ,RowsMpper<?> rowMapper){
	 Set<?> object=null;
	 ResultSet resultSet=null;
	 PreparedStatement preparedStatement=null;
	 Connection connection=null;
	try {
		connection=getConnection();
		preparedStatement=TemplateUtils.getPreparedStatement(connection, sql);
		resultSet=preparedStatement.executeQuery();
		if(resultSet!=null)
			object =(Set<?>)rowMapper.mapRowsToObjList(resultSet);
		connection.commit();
   	} catch (SQLException e) { TemplateUtils.callRollBack(connection, e); }
   	finally{
		TemplateUtils.finallyBlock(connection, resultSet, preparedStatement);
   	}
	return object;
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
