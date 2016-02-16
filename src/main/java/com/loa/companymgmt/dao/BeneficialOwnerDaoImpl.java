package com.loa.companymgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.loa.common.db.DBUtil;
import com.loa.companymgmt.model.BeneficialOwner;


public class BeneficialOwnerDaoImpl implements IBeneficialOwnerDao {
	ResultSet rs = null;

	@Override
	public boolean save(BeneficialOwner owner) {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();

			stmt = conn
					.prepareStatement("insert into beneficial_owner(cid, oid, ctime,utime) values (?,?,?,?) ");
			stmt.setInt(1, owner.getCid());
			stmt.setInt(2, owner.getOid());
			stmt.setTimestamp(3, new Timestamp(new Date().getTime()));
			stmt.setTimestamp(4, new Timestamp(new Date().getTime()));
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
	public boolean delete(BeneficialOwner owner) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("delete from beneficial_owner where cid = ? ");
			stmt.setLong(1, owner.getCid());
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
	public List<BeneficialOwner> list(int cid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<BeneficialOwner> list = new ArrayList<BeneficialOwner>();
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("select id, cid, oid,ctime,utime from beneficial_owner where cid = ? ");
			stmt.setInt(1, cid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				BeneficialOwner owner = new BeneficialOwner();
				owner.setId(rs.getInt(1));
				owner.setCid(rs.getInt(2));
				owner.setOid(rs.getInt(3));
				owner.setCtime(new Date(rs.getTimestamp(4).getTime()));
				owner.setUtime(new Date(rs.getTimestamp(5).getTime()));
				list.add(owner);
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
	
}
