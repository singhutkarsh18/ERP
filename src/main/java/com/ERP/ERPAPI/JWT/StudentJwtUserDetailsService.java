package com.ERP.ERPAPI.JWT;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service@AllArgsConstructor
public class StudentJwtUserDetailsService implements UserDetailsService {

    @Autowired
    StudentRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student= repo.findByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(),
                new ArrayList<>());
    }
}
