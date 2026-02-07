package com.shafaq.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shafaq.models.Department;
import com.shafaq.models.Employee;
import com.shafaq.repository.DepartmentRepository;
import com.shafaq.repository.EmpRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DepartmentController {
    
    @Autowired
    private DepartmentRepository deptRepo;
    
    @Autowired
    private EmpRepository empRepo;
    
    // GET all departments with employees
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return deptRepo.findAll();
    }
    
    // POST create department
    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department dept) {
        Department saved = deptRepo.save(dept);
        return ResponseEntity.ok(saved);
    }
    
    // PUT update department
    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department dept) {
        Department existing = deptRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(dept.getName());
            return ResponseEntity.ok(deptRepo.save(existing));
        }
        return ResponseEntity.notFound().build();
    }
    
    // DELETE department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        deptRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    // GET all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }
    
    // POST create employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequest request) {
        Department dept = deptRepo.findById(request.getDepartmentId()).orElse(null);
        if (dept != null) {
            Employee emp = new Employee(request.getName());
            emp.setDept(dept);
            dept.addEmp(emp);
            return ResponseEntity.ok(empRepo.save(emp));
        }
        return ResponseEntity.badRequest().build();
    }
    
    // PUT update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee emp) {
        Employee existing = empRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(emp.getName());
            return ResponseEntity.ok(empRepo.save(existing));
        }
        return ResponseEntity.notFound().build();
    }
    
    // PUT transfer employee
    @PutMapping("/employees/{id}/transfer")
    public ResponseEntity<Employee> transferEmployee(@PathVariable Integer id, @RequestBody TransferRequest request) {
        Employee emp = empRepo.findById(id).orElse(null);
        Department newDept = deptRepo.findById(request.getDepartmentId()).orElse(null);
        
        if (emp != null && newDept != null) {
            Department oldDept = emp.getDept();
            if (oldDept != null) {
                oldDept.removeEmp(emp);
            }
            emp.setDept(newDept);
            newDept.addEmp(emp);
            return ResponseEntity.ok(empRepo.save(emp));
        }
        return ResponseEntity.badRequest().build();
    }
    
    // DELETE employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        Employee emp = empRepo.findById(id).orElse(null);
        if (emp != null) {
            Department dept = emp.getDept();
            if (dept != null) {
                dept.removeEmp(emp);
            }
            empRepo.delete(emp);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Helper classes
    static class EmployeeRequest {
        private String name;
        private Integer departmentId;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getDepartmentId() { return departmentId; }
        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    }
    
    static class TransferRequest {
        private Integer departmentId;
        
        public Integer getDepartmentId() { return departmentId; }
        public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }
    }
}



