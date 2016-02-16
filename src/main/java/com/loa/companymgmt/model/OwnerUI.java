package com.loa.companymgmt.model;

import java.io.Serializable;
 

 

public class OwnerUI extends Owner implements Serializable {
	private static final long serialVersionUID = 3L;
	private String dobStr; // Date of Birth
	public String getDobStr() {
		return dobStr;
	}
	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}
	 
}
