package com.loa.companymgmt.dao;

import java.util.List;

import com.loa.companymgmt.model.BeneficialOwner;





public interface IBeneficialOwnerDao {
	public boolean save(BeneficialOwner owner);
	public boolean delete(BeneficialOwner owner);
	public List<BeneficialOwner> list(int bid);
}
