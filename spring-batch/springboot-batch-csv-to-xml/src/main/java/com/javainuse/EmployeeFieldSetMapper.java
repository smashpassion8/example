package com.javainuse;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.javainuse.model.Employee;

public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {

	public Employee mapFieldSet(FieldSet fieldSet) {

		Employee employee = new Employee();
		employee.setEmployeeId(fieldSet.readString(0));
		employee.setEmployeeName(fieldSet.readString(1));
		return employee;

	}

}