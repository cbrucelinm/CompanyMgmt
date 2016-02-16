package com.loa.companymgmt.bl;

import java.util.ArrayList;
import java.util.List;

import com.loa.companymgmt.dao.BeneficialOwnerDaoImpl;
import com.loa.companymgmt.dao.CompanyDaoImpl;
import com.loa.companymgmt.dao.IBeneficialOwnerDao;
import com.loa.companymgmt.dao.ICompanyDao;
import com.loa.companymgmt.dao.IOwnerDao;
import com.loa.companymgmt.dao.OwnerDaoImpl;
import com.loa.companymgmt.model.BeneficialOwner;
import com.loa.companymgmt.model.Company;
import com.loa.companymgmt.model.Owner;

public class CompanyBLImpl implements ICompanyBl {
	
	private IOwnerDao ownerDao = new OwnerDaoImpl();
	private ICompanyDao companyDao = new CompanyDaoImpl();
	private IBeneficialOwnerDao beneficialOwnerDao = new BeneficialOwnerDaoImpl();

	@Override
	public boolean createCompany(Company company) {
		companyDao.save(company);
		if( company.getId() != -1 ) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<String> getCompanyList() {
		List<Company> companies = companyDao.list();
		ArrayList<String> result = new ArrayList<String>();
		for(Company company : companies) {
			result.add(company.getName());
		}
		return result;
	}

	@Override
	public Company getCompanyDetails(String companyName) {
		Company company = new Company();
		company.setName(companyName);
		company =  companyDao.load(company);
		company.setBeneficialOwners((ArrayList<BeneficialOwner>) beneficialOwnerDao.list(company.getId()));
		for(BeneficialOwner o : company.getBeneficialOwners()) {
			Owner owner = new Owner();
			owner.setId(o.getOid());
			owner = ownerDao.load(owner);
			company.getOwners().add(owner);
		}
		return company;
	}
	
	@Override
	public Company getDetailsOfCompany(String companyName) {
		Company company = new Company();
		company.setName(companyName);
		company =  companyDao.load(company);
		company.setBeneficialOwners((ArrayList<BeneficialOwner>) beneficialOwnerDao.list(company.getId()));
		return company;
	}
	
	@Override
	public Company checkDetailsOfCompany(String companyName) {
		Company company = new Company();
		company.setName(companyName);
		return company =  companyDao.load(company);
	}

	@Override
	public boolean updateCompany(Company company) {
		BeneficialOwner beneficialOwner = new BeneficialOwner();
		beneficialOwner.setCid(company.getId());
		beneficialOwnerDao.delete(beneficialOwner);
		return companyDao.update(company);
	}
	
	@Override
	public boolean updateCompanyByName(Company company) {
		BeneficialOwner beneficialOwner = new BeneficialOwner();
		beneficialOwner.setCid(company.getId());
		beneficialOwnerDao.delete(beneficialOwner);
		return companyDao.updateByName(company);
	}

	@Override
	public boolean addBeneficialOwner(String companyName , int ownerId) {
		BeneficialOwner beneficialOwner = new BeneficialOwner();
		beneficialOwner.setOid(ownerId);
		Company company = getDetailsOfCompany(companyName);
		beneficialOwner.setCid(company.getId());
		return beneficialOwnerDao.save(beneficialOwner);
	}

	@Override
	public ArrayList<Owner> getOwnerList() {
		return (ArrayList<Owner>) ownerDao.list();
	}

	@Override
	public boolean createOwner(Owner owner) {
		ownerDao.save(owner);
		if( owner.getId() != -1 ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateOwner(Owner owner) {
		return ownerDao.update(owner);
	}

	@Override
	public Owner getDetailsOfOwner(String email) {
		Owner owner = new Owner();
		owner.setEmail(email);
		return ownerDao.load(owner);
	}
	
	@Override
	public Owner getDetailsOfOwner(Owner owner) {
		return ownerDao.loadById(owner);
	}
	
	@Override
	public Owner getDetailsOfOwner(String name, String email) {
		Owner owner = new Owner();
		owner.setEmail(email);
		owner.setName(name);
		return ownerDao.loadByNameAndEmail(owner);
	}
}
