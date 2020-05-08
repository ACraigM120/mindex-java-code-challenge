/*
 * Applicant: Andrew McCartney
 * Note: Modified to read compensation from json
 */

package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

@Component
public class DataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/employee_database.json";
    private static final String COMPENSATION_LOCATION = "/static/compensation_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);
        InputStream compensationStream = this.getClass().getResourceAsStream(COMPENSATION_LOCATION);

        Employee[] employees = null;
        Compensation[] compensations = null;

        try {
            employees = objectMapper.readValue(inputStream, Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            compensations = objectMapper.readValue(compensationStream, Compensation[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }
        
        for (Compensation compensation : compensations) {
        	compensationRepository.insert(compensation);
        }
    }
}