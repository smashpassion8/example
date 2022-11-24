package com.javainuse;

import org.springframework.batch.item.ItemProcessor;

import com.javainuse.model.Employee;

public class CustomEmployeeProcessor implements
		ItemProcessor<Employee, Employee> {

	public Employee process(Employee employee) throws Exception {

		System.out.println("Processing..." + employee);
		System.out.println(employee.getEmployeeName());
		return employee;
	}

}