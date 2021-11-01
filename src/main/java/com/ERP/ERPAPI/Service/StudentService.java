package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Repository.StudentRepository;
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
