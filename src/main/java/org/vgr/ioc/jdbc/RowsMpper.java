package org.vgr.ioc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowsMpper<T> {
	public void mapRowsToObjList(ResultSet resultSet)throws SQLException;
}
