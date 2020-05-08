/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportingStructureService;
	
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    private Employee sam;
    private Employee merry;
    private Employee pippin;
	private Employee frodo;
	private Employee boromir;
	private Employee aragorn;
    private Employee gimli;
	private Employee legolas;
	private Employee gandalf;
    
    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureIdUrl = "http://localhost:" + port + "/reportingstructure/{employeeId}";
        
        createReportingStructure();
    }

    @Test
    public void testRead() {
        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, gandalf.getEmployeeId()).getBody();
        assertEquals(8, readReportingStructure.getNumberOfReports());
        assertCorrectReportingStructure(gandalf, readReportingStructure);
    }
    
    private static void assertCorrectReportingStructure(Employee originator, ReportingStructure actual) {
    	assertEquals(originator.getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertDirectReportsMatch(originator.getDirectReports(), actual.getEmployee().getDirectReports());
    }
    
    private static void assertDirectReportsMatch(List<Employee> expected, List<Employee> actual) {
    	assertEquals(expected == null, actual == null);
    	if(expected == null) {
    		return;
    	}
    	assertEquals(expected.size(), actual.size());
    	if(expected.size() > 0) {
	    	for(int i = 0; i < expected.size(); i++) {
	    		assertEquals(expected.get(i).getEmployeeId(), actual.get(i).getEmployeeId());
	    		assertDirectReportsMatch(expected.get(i).getDirectReports(), actual.get(i).getDirectReports());
	    	}
    	}
    }

    private void createReportingStructure() {
    	List<Employee> frodoReports = new ArrayList<Employee>(3);
    	List<Employee> aragornReports = new ArrayList<Employee>(1);
    	List<Employee> gandalfReports = new ArrayList<Employee>(4);
    	
    	sam = new Employee();
    	sam.setFirstName("Samwise");
    	sam.setLastName("Gamgee");
    	sam.setDepartment("Housekeeping");
        sam.setPosition("Groundskeeper");
        sam = restTemplate.postForEntity(employeeUrl, sam, Employee.class).getBody();
        
        merry = new Employee();
    	merry.setFirstName("Meriadoc");
    	merry.setLastName("Brandybuck");
    	merry.setDepartment("Housekeeping");
    	merry.setPosition("Stableboy");
    	merry = restTemplate.postForEntity(employeeUrl, merry, Employee.class).getBody();
    	
    	pippin = new Employee();
    	pippin.setFirstName("Peregrin");
    	pippin.setLastName("Took");
    	pippin.setDepartment("Housekeeping");
    	pippin.setPosition("Cleaner");
    	pippin = restTemplate.postForEntity(employeeUrl, pippin, Employee.class).getBody();

    	frodo = new Employee();
    	frodo.setFirstName("Frodo");
    	frodo.setLastName("Baggins");
    	frodo.setDepartment("Housekeeping");
    	frodo.setPosition("Ringbearer");
    	frodoReports.add(sam);
    	frodoReports.add(merry);
    	frodoReports.add(pippin);
    	frodo.setDirectReports(frodoReports);
    	frodo = restTemplate.postForEntity(employeeUrl, frodo, Employee.class).getBody();

    	boromir = new Employee();
        boromir.setFirstName("Boromir");
    	boromir.setLastName("Denethorson");
    	boromir.setDepartment("Legal");
        boromir.setPosition("Lawyer");
        boromir = restTemplate.postForEntity(employeeUrl, boromir, Employee.class).getBody();

        aragorn = new Employee();
    	aragorn.setFirstName("Aragorn II");
        aragorn.setLastName("Elessar");
        aragorn.setDepartment("Management");
        aragorn.setPosition("COO");
        aragornReports.add(boromir);
        aragorn.setDirectReports(aragornReports);
        aragorn = restTemplate.postForEntity(employeeUrl, aragorn, Employee.class).getBody();

        gimli = new Employee();
    	gimli.setFirstName("Gimli");
    	gimli.setLastName("Gloinson");
    	gimli.setDepartment("Construction");
    	gimli.setPosition("Architect");
    	gimli = restTemplate.postForEntity(employeeUrl, gimli, Employee.class).getBody();

    	legolas = new Employee();
        legolas.setFirstName("Legolas");
    	legolas.setLastName("Thranduilion");
    	legolas.setDepartment("Forestry");
    	legolas.setPosition("Park Ranger");
    	legolas = restTemplate.postForEntity(employeeUrl, legolas, Employee.class).getBody();

    	gandalf = new Employee();
     	gandalf.setFirstName("Gandalf");
        gandalf.setLastName("Grey");
        gandalf.setDepartment("Management");
        gandalf.setPosition("CEO");
        gandalfReports.add(frodo);
        gandalfReports.add(aragorn);
        gandalfReports.add(legolas);
        gandalfReports.add(gimli);
        gandalf.setDirectReports(gandalfReports);
    	gandalf = restTemplate.postForEntity(employeeUrl, gandalf, Employee.class).getBody();
    }
}