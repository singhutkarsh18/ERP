package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailService;
import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.AdminJwtRequest;
import com.ERP.ERPAPI.Model.JwtResponse;
import com.ERP.ERPAPI.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class AdminJwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminJwtUserDetailService adminJwtUserDetailService;
    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/authenticateAdmin")
    public ResponseEntity<?> createAdminAuthenticationToken(@RequestBody AdminJwtRequest authenticationRequest) throws Exception {

        String auth =authenticateAdmin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(auth.equals("true")) {
            final UserDetails userDetails = adminJwtUserDetailService
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
        try {
            Admin admin = adminRepository.findByUsername(username);
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return "true";
            } else {
                System.out.println(username);
                System.out.println(passwordEncoder.matches(password, admin.getPassword()));
                return "false";
            }
        }
        catch (Exception e)
        {
            return "User not found";
        }

    }
}
