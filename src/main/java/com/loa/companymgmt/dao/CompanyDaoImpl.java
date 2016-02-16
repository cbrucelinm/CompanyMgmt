package com.loa.companymgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.loa.common.db.DBUtil;
import com.loa.companymgmt.model.Company;


public class CompanyDaoImpl implements ICompanyDao {
	ResultSet rs = null;

	@Override
	public boolean save(Company company) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();

			stmt = conn
					.prepareStatement("insert into company (name, address, city, country, email, phone, ctime,utime) values ( ?, ?,?,?,?,?,?,?) ");
			stmt.setString(1, company.getName());
			stmt.setString(2, company.getAddress());
			stmt.setString(3, company.getCity());
			stmt.setString(4, company.getCountry());
			stmt.setString(5, company.getEmail());
			stmt.setString(6, company.getPhone());
			stmt.setTimestamp(7, new Timestamp(new Date().getTime()));
			stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
			stmt.executeUpdate();
			stmt.close();
			stmt = conn.prepareStatement("select id from company where name  = ? ");
			stmt.setString(1, company.getName());
			rs = stmt.executeQuery();
			if (rs.next()) {
				company.setId(rs.getInt(1));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean update(Company company) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("update company set name = ? , address = ? , city =?, country =? , email= ? , phone = ? ,  utime = ?  where id = ? ");
			stmt.setInt(8, company.getId());
			stmt.setString(1, company.getName());
			stmt.setString(2, company.getAddress());
			stmt.setString(3, company.getCity());
			stmt.setString(4, company.getCountry());
			stmt.setString(5, company.getEmail());
			stmt.setString(6, company.getPhone());
			stmt.setTimestamp(7,  new Timestamp(new Date().getTime()));
			stmt.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean updateByName(Company company) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("update company set name = ? , address = ? , city =?, country =? , email= ? , phone = ? ,  utime = ?  where name = ? ");
			stmt.setString(8, company.getName());
			stmt.setString(1, company.getName());
			stmt.setString(2, company.getAddress());
			stmt.setString(3, company.getCity());
			stmt.setString(4, company.getCountry());
			stmt.setString(5, company.getEmail());
			stmt.setString(6, company.getPhone());
			stmt.setTimestamp(7,  new Timestamp(new Date().getTime()));
			stmt.executeUpdate();
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	
	@Override
	public List<Company> list() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Company> list = new ArrayList<Company>();
		try {
			conn = DBUtil.getConnection();
			System.out.println("Connection : " + conn);
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, ctime,utime from company ORDER BY name, email ASC");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Company c = new Company();
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setAddress(rs.getString(3));
				c.setCity(rs.getString(4));
				c.setCountry(rs.getString(5));
				c.setEmail(rs.getString(6));
				c.setPhone(rs.getString(7));
				c.setCtime(new Date((rs.getTimestamp(8).getTime())));
				c.setUtime(new Date((rs.getTimestamp(9).getTime())));
				list.add(c);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public Company load(Company company) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, ctime,utime from company where name = ?");
			stmt.setString(1, company.getName());
			rs = stmt.executeQuery();
			if (rs.next()) {
				Company c = new Company();
				c.setId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setAddress(rs.getString(3));
				c.setCity(rs.getString(4));
				c.setCountry(rs.getString(5));
				c.setEmail(rs.getString(6));
				c.setPhone(rs.getString(7));
				c.setCtime(new Date((rs.getTimestamp(8).getTime())));
				c.setUtime(new Date((rs.getTimestamp(9).getTime())));
				return c;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}