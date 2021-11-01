package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    public boolean loginValidation(Student student)
    {
        List<Student> students = new ArrayList<>();
        students=repo.findAll();
        boolean valid_pass=false;
        for(Student student1 : students)
        {
            if((student1.getEmail().equals(student.getEmail()))&&(passwordEncoder.matches(student.getPassword(),student1.getPassword())))
            {
                System.out.println(student.getPassword());
                valid_pass=true;
            }
        }
        return valid_pass;
    }


}
