package com.loa.companymgmt.model;

import com.loa.companymgmt.model.Company;

public class CompanyUI extends Company {

	private static final long serialVersionUID = 4L;
	private String updateName ;
	private String ownerIds;

	public String getOwnerIds() {
		return ownerIds;
	}

	public void setOwnerIds(String ownerIds) {
		this.ownerIds = ownerIds;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}
