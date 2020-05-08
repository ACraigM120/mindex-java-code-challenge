/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mindex.challenge.data.Compensation;

//Since all Employees are compensated (I would hope), I would normally ask whether Employee
//should be modified instead of setting up the below. That said, a case could be made for
//tracking compensation in such a way that one instance == one check, in which case there
//would be multiple per Employee.

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
	Compensation findByEmployee_EmployeeId(String employeeId);
}
