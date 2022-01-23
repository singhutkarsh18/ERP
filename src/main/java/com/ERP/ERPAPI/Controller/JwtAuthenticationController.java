package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailService;
import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.JWT.StudentJwtUserDetailsService;
import com.ERP.ERPAPI.Model.JwtResponse;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Model.StudentJwtRequest;
import com.ERP.ERPAPI.Repository.StudentRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")@AllArgsConstructor
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StudentJwtUserDetailsService userStudentDetailsService;
    @Autowired
    private AdminJwtUserDetailService adminJwtUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/authenticateStudent")
    public ResponseEntity<?> createStudentAuthenticationToken(@RequestBody StudentJwtRequest authenticationRequest) throws Exception {

        String auth =authenticateStudent(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(auth.equals("true")) {
            final UserDetails userDetails = userStudentDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        }
        else
        {
            return new ResponseEntity<>(auth, HttpStatus.OK);
        }
    }

    private String authenticateStudent(String username, String password) throws Exception {

        try {
            Student student = studentRepository.findByUsername(username);
            if (passwordEncoder.matches(password, student.getPassword())) {
                return "true";
            } else {
                System.out.println(username);
                System.out.println(passwordEncoder.matches(password, student.getPassword()));
                return "false";
            }
        }
        catch(ExpiredJwtException e1)
        {
            System.out.println(e1);
            return "JWT token has expired";
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "User not found";
        }
    }


}