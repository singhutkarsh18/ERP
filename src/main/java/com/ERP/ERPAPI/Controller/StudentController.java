package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService service;

    @PostMapping("/createStudent")
    public void createStudent(@RequestBody Student student)
    {
        service.registerStudent(student);
    }


}
