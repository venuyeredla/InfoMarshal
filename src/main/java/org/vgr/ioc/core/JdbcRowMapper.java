package org.vgr.ioc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface JdbcRowMapper<T> {
	public T mapRowToObj(ResultSet resultSet)throws SQLException;
}
