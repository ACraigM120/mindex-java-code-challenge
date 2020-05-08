/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	 private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	    @Autowired
	    private EmployeeRepository employeeRepository;
	    
	    @Override
	    public ReportingStructure read(String employeeId) {
	        LOG.debug("Reading ReportingStructure with employeeId [{}]", employeeId);

	        Employee employee = employeeRepository.findByEmployeeId(employeeId);
	        ReportingStructure reportingStructure = new ReportingStructure();

	        if (employee == null) {
	            throw new RuntimeException("Invalid employeeId: " + employeeId);
	        }

	        reportingStructure.setEmployee(employee);
	        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));
	        
	        return reportingStructure; //ReportingStructure.getString(employee);
	    }
	    
    	private int calculateNumberOfReports(Employee employee) {
    		List<Employee> directReports = employee.getDirectReports();
    		if(directReports == null) {
    			return 0;
    		}
    		int calculatedResult = directReports.size();
    		for (int i = 0; i < directReports.size(); i++) {
    			employee.getDirectReports().set(i, employeeRepository.findByEmployeeId(directReports.get(i).getEmployeeId()));
    			calculatedResult += calculateNumberOfReports(employee.getDirectReports().get(i));
    		}
    		return calculatedResult;
    	}
	
}
