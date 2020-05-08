/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        String employeeId = compensation.getEmployee().getEmployeeId();

        //Desired action in this case is not defined in the spec, can be changed to create the employee if needed
        //At any rate, it is reasonable to conclude that we only want to compensate Employees under the current spec
        if(employeeRepository.findByEmployeeId(employeeId) == null) {
        	throw new RuntimeException("Attempted to create Compensation for invalid employeeId: " + employeeId + System.lineSeparator() +
        	"Please create the employee first.");
        }
        
        compensationRepository.insert(compensation);
        
        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Creating compensation with employeeId [{}]", employeeId);

        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(employeeId);

        if (compensation == null) {
        	if(employeeRepository.findByEmployeeId(employeeId) == null) {
        		throw new RuntimeException("Invalid employeeId: " + employeeId);
        	} else {
        		throw new RuntimeException("No compensation found for employeeId: " + employeeId);
        	}
        }
        
        //Uncomment below if the employee field should be fully defined in the returned Compensation
        //compensation.setEmployee(employeeRepository.findByEmployeeId(employeeId));

        return compensation;
    }
}
