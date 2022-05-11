package org.jpm.spring.employee.controller;

import org.jpm.spring.employee.exception.ResourceNotFoundException;
import org.jpm.spring.employee.model.Employee;
import org.jpm.spring.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://app-ems-bos.herokuapp.com")
@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

     //Get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
       return employeeRepository.findAll();
    }

    //Create Employee
    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
         return employeeRepository.save(employee);
    }

    //Get Employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id:" + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    //Update Employee by id
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long employeeId, @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id:" + employeeId));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    //Delete Employee by id
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id:" + employeeId));
        employeeRepository.deleteById(employeeId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
