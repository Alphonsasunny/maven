package com.hystrix.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.entity.Employee;
import com.hystrix.service.EmployeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/api/employee")
public class Controller {
    
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public String saveEmployee(@RequestBody Employee employee) {
        Optional<Employee> _employee = employeeService.create(employee);
        if(_employee.isPresent()){
            return "The employee data has been saved successfully!";
        }else{
            return "Employee already exist in records";
        }
    }

    @HystrixCommand(fallbackMethod = "getEmployeeByIdFallback")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
      Optional<Employee> employee = employeeService.retrieveOne(id);  
      if (employee.isPresent()) {
        return new ResponseEntity<>(employee.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }    

    @GetMapping
    public List<Employee> getAllEmployees() {
      return employeeService.findall();
    }

    @PutMapping
    public String updateEmployee(@RequestBody Employee employee) {
        Optional<Employee> employee1 = employeeService.updateById(employee);
        if(!employee1.isPresent()){
            return "The employee data does not exist in records!";
        }else{
            return "The employee data has been updated successfully!";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable("id") int id) {
      return  employeeService.delete(id);   
    } 
    private static Logger logger = LoggerFactory.getLogger(Controller.class);
    public ResponseEntity<Employee> getEmployeeByIdFallback( int id) {
    	logger.info("fallback----inside");
    	Optional<Employee> employee = employeeService.retrieveOne(id);  
        if (employee.isPresent()) {
          return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
