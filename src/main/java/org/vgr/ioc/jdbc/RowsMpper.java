package org.vgr.ioc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowsMpper<T> {
	
	public List<T> mapRowsToObjList(ResultSet resultSet)throws SQLException;

}
