package com.shafaq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shafaq.models.Employee;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {
    // Method to find employees by department ID
//	List<Employee> findByDeptId(Integer departmentId);
	//search employee by name 
	Employee findByName(String name);

}
