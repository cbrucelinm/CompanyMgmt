package com.loa.companymgmt.dao;

import java.util.List;

import com.loa.companymgmt.model.Owner;


public interface IOwnerDao {
	public boolean save(Owner owner);
 	public boolean update(Owner owner);
 	public List<Owner> list();
	Owner load(Owner owner);
	public Owner loadByNameAndEmail(Owner owner);
	Owner loadById(Owner owner);
}