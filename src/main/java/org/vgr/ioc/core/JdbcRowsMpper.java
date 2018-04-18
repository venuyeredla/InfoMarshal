package org.vgr.ioc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface JdbcRowsMpper<T> {
	public void mapRowsToObjList(ResultSet resultSet)throws SQLException;
}
