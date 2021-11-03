package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailsService;
import com.ERP.ERPAPI.JWT.JwtUserDetailsService;
import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.Model.JwtRequest;
import com.ERP.ERPAPI.Model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AdminJwtUserDetailsService adminJwtUserDetailsService;

    @PostMapping("/authenticateStudent")
    public ResponseEntity<?> createStudentAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticateStudent(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticateStudent(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
//    @PostMapping("/authenticateAdmin")
//    public ResponseEntity<?> createAdminAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//
//        authenticateAdmin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = adminJwtUserDetailsService
//                .loadUserByUsername(authenticationRequest.getUsername());
//
//        final String token = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticateAdmin(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            System.out.println("Invalid");
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }

}