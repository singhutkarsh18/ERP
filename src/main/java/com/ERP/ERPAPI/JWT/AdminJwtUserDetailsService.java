package com.ERP.ERPAPI.JWT;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminJwtUserDetailsService implements UserDetailsService {

    @Autowired
    AdminRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repo.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
                new ArrayList<>());
    }
}