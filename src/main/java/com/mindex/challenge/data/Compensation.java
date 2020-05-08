/*
 * Applicant: Andrew McCartney
 */

package com.mindex.challenge.data;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Compensation {
	private Employee employee;
	private double salary;

	//@JsonSerialize(using = SerializeDate.class)
	//@JsonDeserialize(using = DeserializeDate.class)
	private Date effectiveDate;
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}

//class SerializeDate extends JsonSerializer {
//
//	@Override
//	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//		Date date;
//		if(value == null) {
//			gen.writeNull();
//		} else {
//			date = (Date)value;
//			gen.writeString(date.toString());
//		}
//	}
//}
//
//class DeserializeDate extends JsonDeserializer {
//
//	@Override
//	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//		
//		return null;
//	}
//	
//}