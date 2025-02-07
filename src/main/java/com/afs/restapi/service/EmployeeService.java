package com.afs.restapi.service;

import com.afs.restapi.dto.CompanyRequest;
import com.afs.restapi.dto.EmployeeRequest;
import com.afs.restapi.dto.EmployeeResponse;
import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.EmployeeNotFoundException;
import com.afs.restapi.mapper.CompanyMapper;
import com.afs.restapi.mapper.EmployeeMapper;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        return EmployeeMapper.toResponse(employee);
    }

    public void update(Long id, EmployeeRequest employeeRequest) {
        Employee toBeUpdatedEmployee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        // Update age and salary if provided
        if (employeeRequest.getAge() != null) {
            toBeUpdatedEmployee.setAge(employeeRequest.getAge());
        }
        if (employeeRequest.getSalary() != null) {
            toBeUpdatedEmployee.setSalary(employeeRequest.getSalary());
        }

        // Save the updated employee
        employeeRepository.save(toBeUpdatedEmployee);
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
       Employee employee = EmployeeMapper.toEntity(employeeRequest);
        return EmployeeMapper.toResponse(employeeRepository.save(employee));

    }

    public List<Employee> findByPage(Integer pageNumber, Integer pageSize) {
        Page<Employee> employeesInThePage = employeeRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
        return employeesInThePage.stream().collect(Collectors.toList());
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
