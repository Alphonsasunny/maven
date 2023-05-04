package com.hystrix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hystrix.entity.Employee;
import com.hystrix.repository.EmployeeRepository;

@Service
public class EmployeeserviceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> create(Employee employee){
        if (employeeRepository.existsById(employee.getId())){
            return Optional.empty();
        }else{
            return Optional.of(employeeRepository.save(employee));
        }
    }

    public List<Employee> findall(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> retrieveOne(int empid){
    	if(empid==1000) {
    		throw new RuntimeException();
    	}
        return employeeRepository.findById(empid);
    }
    
    public Optional<Employee> updateById(Employee employee){
        if (employeeRepository.existsById(employee.getId())){
            return Optional.of(employeeRepository.save(employee));
        }else{
            return Optional.empty();
        }
    }   

    public String delete(int empid){
        if (employeeRepository.existsById(empid)){
            employeeRepository.deleteById(empid);
            return empid + " deleted successfully!";
        }else{
            return "The employee data does not exist in records!";
        }
        
    }
    
}