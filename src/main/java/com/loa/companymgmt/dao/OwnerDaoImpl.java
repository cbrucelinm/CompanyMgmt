package com.loa.companymgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.loa.common.db.DBUtil;
import com.loa.companymgmt.model.Owner;
import com.loa.companymgmt.model.OwnerUI;


public class OwnerDaoImpl implements IOwnerDao {
	ResultSet rs = null;

	@Override
	public boolean save(Owner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();

			stmt = conn
					.prepareStatement("insert into owner (name, address, city, country, email, phone, dob, ctime,utime) values ( ?, ?,?,?,?,?,?,?,?) ");
			stmt.setString(1, owner.getName());
			stmt.setString(2, owner.getAddress());
			stmt.setString(3, owner.getCity());
			stmt.setString(4, owner.getCountry());
			stmt.setString(5, owner.getEmail());
			stmt.setString(6, owner.getPhone());
			stmt.setTimestamp(7, new Timestamp(owner.getDob().getTime()));
			stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
			stmt.setTimestamp(9, new Timestamp(new Date().getTime()));
			stmt.executeUpdate();
			stmt.close();
			stmt = conn.prepareStatement("select id from owner where email  = ? and name = ?");
			stmt.setString(1, owner.getEmail());
			stmt.setString(2, owner.getName());
			rs = stmt.executeQuery();
			if (rs.next()) {
				owner.setId(rs.getInt(1));
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
	public boolean update(Owner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("update owner set name = ? , address = ? , city =?, country =? , email= ? , phone = ? , dob = ? ,  utime = ?  where id = ? ");
			stmt.setInt(9, owner.getId());
			stmt.setString(1, owner.getName());
			stmt.setString(2, owner.getAddress());
			stmt.setString(3, owner.getCity());
			stmt.setString(4, owner.getCountry());
			stmt.setString(5, owner.getEmail());
			stmt.setString(6, owner.getPhone());
			stmt.setTimestamp(7, new Timestamp(owner.getDob().getTime()));
			stmt.setTimestamp(8,  new Timestamp(new Date().getTime()));
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
	public List<Owner> list() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Owner> list = new ArrayList<Owner>();
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, dob, ctime,utime from owner ORDER BY name, email ASC");
			rs = stmt.executeQuery();
			while (rs.next()) {
				OwnerUI o = new OwnerUI();
				o.setId(rs.getInt(1));
				o.setName(rs.getString(2));
				o.setAddress(rs.getString(3));
				o.setCity(rs.getString(4));
				o.setCountry(rs.getString(5));
				o.setEmail(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setDob(new Date((rs.getTimestamp(8).getTime())));
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				o.setDobStr(format.format(o.getDob()));
				o.setCtime(new Date((rs.getTimestamp(9).getTime())));
				o.setUtime(new Date((rs.getTimestamp(10).getTime())));
				list.add(o);
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
	public Owner loadById(Owner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, dob, ctime,utime from owner where id = ?");
			stmt.setInt(1, owner.getId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				OwnerUI o = new OwnerUI();
				o.setId(rs.getInt(1));
				o.setName(rs.getString(2));
				o.setAddress(rs.getString(3));
				o.setCity(rs.getString(4));
				o.setCountry(rs.getString(5));
				o.setEmail(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setDob(new Date((rs.getTimestamp(8).getTime())));
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				o.setDobStr(format.format(o.getDob()));
				o.setCtime(new Date((rs.getTimestamp(9).getTime())));
				o.setUtime(new Date((rs.getTimestamp(10).getTime())));
				return o;
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

	
	@Override
	public Owner load(Owner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, dob, ctime,utime from owner where email = ?");
			stmt.setString(1, owner.getEmail());
			rs = stmt.executeQuery();
			if (rs.next()) {
				Owner o = new Owner();
				o.setId(rs.getInt(1));
				o.setName(rs.getString(2));
				o.setAddress(rs.getString(3));
				o.setCity(rs.getString(4));
				o.setCountry(rs.getString(5));
				o.setEmail(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setDob(new Date((rs.getTimestamp(8).getTime())));
				o.setCtime(new Date((rs.getTimestamp(9).getTime())));
				o.setUtime(new Date((rs.getTimestamp(10).getTime())));
				return o;
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

	@Override
	public Owner loadByNameAndEmail(Owner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select id, name, address, city, country, email, phone, dob, ctime,utime from owner where name = ? and email = ?");
			stmt.setString(1, owner.getName());
			stmt.setString(2, owner.getEmail());
			rs = stmt.executeQuery();
			if (rs.next()) {
				Owner o = new Owner();
				o.setId(rs.getInt(1));
				o.setName(rs.getString(2));
				o.setAddress(rs.getString(3));
				o.setCity(rs.getString(4));
				o.setCountry(rs.getString(5));
				o.setEmail(rs.getString(6));
				o.setPhone(rs.getString(7));
				o.setDob(new Date((rs.getTimestamp(8).getTime())));
				o.setCtime(new Date((rs.getTimestamp(9).getTime())));
				o.setUtime(new Date((rs.getTimestamp(10).getTime())));
				return o;
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