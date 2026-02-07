package com.shafaq.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Department {

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    
    private List<Employee> employees = new ArrayList<>();

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    // ✅ VERY IMPORTANT: must be getEmployees
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmp(Employee e) {
        this.employees.add(e);
        e.setDept(this);
    }

    public void removeEmp(Employee e) {
        this.employees.remove(e);
        e.setDept(null);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
