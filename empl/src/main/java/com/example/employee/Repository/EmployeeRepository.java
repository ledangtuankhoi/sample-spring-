package com.example.employee.Repository;

import com.example.employee.Model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<EmployeeEntity, String> {}
