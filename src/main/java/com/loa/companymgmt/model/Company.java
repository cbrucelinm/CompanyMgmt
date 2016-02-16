package com.loa.companymgmt.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Company extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2L;
	private ArrayList<BeneficialOwner> beneficialOwners = new ArrayList<BeneficialOwner>();
	private ArrayList<Owner> owners = new ArrayList<Owner>();
	public ArrayList<Owner> getOwners() {
		return owners;
	}
	public void setOwners(ArrayList<Owner> owners) {
		this.owners = owners;
	}
	public ArrayList<BeneficialOwner> getBeneficialOwners() {
		return beneficialOwners;
	}
	public void setBeneficialOwners(ArrayList<BeneficialOwner> beneficialOwners) {
		this.beneficialOwners = beneficialOwners;
	}
	
}
