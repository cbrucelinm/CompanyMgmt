package com.loa.companymgmt.model;

import java.io.Serializable;
import java.util.Date;
 

 

public class Owner extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3L;
	private Date dob = new Date(); // Date of Birth
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	 
}
