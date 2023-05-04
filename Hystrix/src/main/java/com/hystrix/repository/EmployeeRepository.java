package com.hystrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hystrix.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    
}