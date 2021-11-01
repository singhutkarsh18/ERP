package com.ERP.ERPAPI.JWT;

import java.util.ArrayList;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    Student newStudent = new Student();
    Mail mail=new Mail();
    @Autowired
    StudentRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student= repo.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(),
                new ArrayList<>());
    }
}
