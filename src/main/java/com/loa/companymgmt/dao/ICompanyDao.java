package com.loa.companymgmt.dao;

import java.util.List;

import com.loa.companymgmt.model.Company;

public interface ICompanyDao {
	public boolean save(Company company);
	public boolean update(Company company);
	public Company load(Company company);
	public List<Company> list();
	boolean updateByName(Company company);
}
