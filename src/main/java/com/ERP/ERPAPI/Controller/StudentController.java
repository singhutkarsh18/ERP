package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

//    @PostMapping("/createStudent")
//    public void createStudent(@RequestBody Student student)
//    {
//        studentService.registerStudent(student);
//    }
//    @GetMapping("/loginStudent")
//    public Boolean loginStudent(@RequestBody Student student)
//    {
//        return studentService.loginValidation(student);
//    }
//    @PostMapping("/forgotPassword")
//    public void forgotPassword(@RequestBody Student student)
//    {
//
//    }

}
