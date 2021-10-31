package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void registerStudent(Student student)
    {
        Student newStudent = new Student();
        newStudent.setId(student.getId());
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent.setEnabled(false);
        newStudent.setPassword(passwordEncoder.encode(student.getPassword()));
        newStudent.setVerification_code(RandomString.make(64));
        repo.save(newStudent);
    }
    public Boolean loginValidation(Student student)
    {
        
    }

}
