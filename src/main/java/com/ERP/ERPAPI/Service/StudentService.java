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



}
