package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RestTemplate rest;
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    public EmployeeController(EmployeeService employeeService, RestTemplate rest) {
        this.employeeService = employeeService;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                employeeService.createOrUpdate(employee),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/account/")
    public ResponseEntity<Employee> addPerson(@PathVariable("id") int id, @RequestBody Person person) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
        Person personCreated = rest.postForObject(API, person, Person.class);
        employee.addPerson(personCreated);
        employeeService.createOrUpdate(employee);
        return new ResponseEntity<>(
                employee,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}/account/")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        rest.put(API, person);
        employee.addPerson(person);
        employeeService.createOrUpdate(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/account/{accountId}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id, @PathVariable("accountId") int accountId) {
        Employee employee = employeeService.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        rest.delete(API_ID, accountId);
        return ResponseEntity.ok().build();
    }
}
