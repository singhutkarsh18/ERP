package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailService;
import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.Model.AdminJwtRequest;
import com.ERP.ERPAPI.Model.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminJwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminJwtUserDetailService adminJwtUserDetailService;

    @PostMapping("/authenticateAdmin")
    public ResponseEntity<?> createAdminAuthenticationToken(@RequestBody AdminJwtRequest authenticationRequest) throws Exception {

        authenticateAdmin(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = adminJwtUserDetailService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticateAdmin(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(username+" "+password);
            System.out.println("Invalid");
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}
