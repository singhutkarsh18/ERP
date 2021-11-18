package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailService;
import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.JWT.TeacherJwtUserDetailService;
import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherJwtController {

    @Autowired
    TeacherJwtUserDetailService teacherJwtUserDetailService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate/Teacher")
    public ResponseEntity<?> createTeacherAuthenticationToken(@RequestBody AdminJwtRequest authenticationRequest) throws Exception {

        String auth =authenticateAdmin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(auth.equals("true")) {
            final UserDetails userDetails = teacherJwtUserDetailService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        }
        else
        {
            return new ResponseEntity<>(auth, HttpStatus.OK);
        }
    }

    private String authenticateAdmin(String username, String password) throws Exception {
        Teacher teacher = teacherRepository.findByUsername(username);
        try {
            if (passwordEncoder.matches(password, teacher.getPassword())) {
                return "true";
            } else {
                System.out.println(username);
                System.out.println(passwordEncoder.matches(password, teacher.getPassword()));
                return "false";
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            return "User not found";
        }

    }

}
