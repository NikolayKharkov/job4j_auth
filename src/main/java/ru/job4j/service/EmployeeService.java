package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Employee;
import ru.job4j.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    public Optional<Employee> findById(int id) {
        return employeeRepository.findById(id);
    }

    public Employee createOrUpdate(Employee employee) {
        return employeeRepository.save(employee);
    }


}
