package com.shafaq.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.shafaq.service.DepartmentEmpService;

// COMMENT OUT THIS LINE to disable console input
// @Component
public class DepartmentEmpRunner implements CommandLineRunner {

    private DepartmentEmpService service;

    @Autowired
    public DepartmentEmpRunner(DepartmentEmpService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        // Disabled - Using REST API now
    }
}
