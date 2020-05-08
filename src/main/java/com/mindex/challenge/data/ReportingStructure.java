/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.data;

import java.util.ArrayList;
import java.util.List;

public class ReportingStructure {
	//Flip the order from the spec to improve the experience for anyone only interested in numberOfReports
	private int numberOfReports;
	private Employee employee;
	
	public int getNumberOfReports() {
		return numberOfReports;
	}
	
	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	// Ordinarily I would use a parameter name like "input" or "newValue" for accessors like this,
	// but it's probably best to follow the convention set in the existing code.
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}