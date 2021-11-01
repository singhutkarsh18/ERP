package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/studentLogin")
    public Boolean login(Student student)
    {
        System.out.println(studentService.loginValidation(student));
        return studentService.loginValidation(student);

    }

}
