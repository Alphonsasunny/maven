package com.hystrix.service;

import java.util.List;
import java.util.Optional;

import com.hystrix.entity.Employee;

public interface EmployeeService {
	
	public Optional<Employee> create(Employee employee);
	public List<Employee> findall();
	public Optional<Employee> retrieveOne(int empid);
	public String delete(int empid);
	public Optional<Employee> updateById(Employee employee);

}
