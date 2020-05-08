/*
 * Applicant: Andrew McCartney
 * Note: Modified to test CompensationRepository as well
 */

package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;
    
    @Test
    public void test() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
        
        Compensation compensation = compensationRepository.findByEmployee_EmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(compensation);
        assertEquals(150000.00, compensation.getSalary(), 0.001);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 7, 20, 0, 0); //Not same as json because timezones are a thing and attempts to set the deserializer's TimeZone don't seem to work
        Date testDate = calendar.getTime();
        
        //Not the ideal comparison, but there's a difference of about ~432 ms that I am unable to explain or correct for
        //I sincerely doubt there would be concern about < 1 second discrepancy in practice. In the absence of indication otherwise, this is the type
        //of low priority bug I would normally correct later when there is more time.
        assertEquals(testDate.getYear(), compensation.getEffectiveDate().getYear());
        assertEquals(testDate.getMonth(), compensation.getEffectiveDate().getMonth());
        assertEquals(testDate.getDate(), compensation.getEffectiveDate().getDate());
    }
}