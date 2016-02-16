package com.loa.common.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaDbStatement  {
	public PreparedStatement getStmt() {
		return stmt;
	}
	public void setStmt(PreparedStatement stmt) {
		this.stmt = stmt;
	}
	private PreparedStatement stmt ;
	public int executeUpdate() {
		try {
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	public List<List<Object>> list() {
		ResultSet rs = null ;
		List<List<Object>> rows = new ArrayList<List<Object>>();
		try {
			rs = stmt.executeQuery();
			while ( rs.next()) {
				ResultSetMetaData meta = rs.getMetaData();
				int count = meta.getColumnCount();
				List<Object> row = new ArrayList<Object>();
				for ( int i = 1; i < count+1 ; i++ ) {
					row.add(rs.getObject(i));
				}
				rows.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if( stmt != null ) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rows;
	}
}
