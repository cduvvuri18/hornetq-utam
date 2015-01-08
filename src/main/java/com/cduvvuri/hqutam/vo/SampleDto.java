package com.cduvvuri.hqutam.vo;

import java.io.Serializable;

public class SampleDto implements Serializable {
	private String empName;
	private int empId;

	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
}
