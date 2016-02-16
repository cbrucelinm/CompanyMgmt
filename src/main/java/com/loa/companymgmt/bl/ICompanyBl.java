package com.loa.companymgmt.bl;

import java.util.ArrayList;

import com.loa.companymgmt.model.Company;
import com.loa.companymgmt.model.Owner;

public interface ICompanyBl {
	 public boolean  createCompany(Company company);
	 public ArrayList<String> getCompanyList();
	 public Company getDetailsOfCompany(String companyName);
	 public boolean updateCompany(Company company);
	 public boolean addBeneficialOwner(String companyName , int ownerId);
	 public ArrayList<Owner> getOwnerList();
	 public boolean createOwner(Owner owner);
	 public boolean updateOwner(Owner owner);
	 public Owner getDetailsOfOwner(String email);
	 public Owner getDetailsOfOwner(Owner owner);
	Company checkDetailsOfCompany(String companyName);
	Company getCompanyDetails(String companyName);
	Owner getDetailsOfOwner(String name, String email);
	public boolean updateCompanyByName(Company company);
}
