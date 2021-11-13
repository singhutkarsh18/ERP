package com.ERP.ERPAPI.JWT;

import com.ERP.ERPAPI.Model.Teacher;
import com.ERP.ERPAPI.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeacherJwtUserDetailService implements UserDetailsService {
    @Autowired
    TeacherRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher= repo.findByUsername(username);
        if (teacher== null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(teacher.getUsername(), teacher.getPassword(),
                new ArrayList<>());
    }
}