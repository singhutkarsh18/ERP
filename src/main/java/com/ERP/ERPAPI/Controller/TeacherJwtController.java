package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.JWT.JwtUtil;
import com.ERP.ERPAPI.JWT.TeacherJwtUserDetailService;
import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.TeacherRepository;
import com.ERP.ERPAPI.Service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@CrossOrigin("*")
@Transactional@AllArgsConstructor
public class TeacherJwtController {

    @Autowired
    TeacherJwtUserDetailService teacherJwtUserDetailService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TeacherService teacherService;

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
    @PostMapping("/forgot/password/teacher")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String,String> request)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.forgot(request.get("username")));
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/validate/Otp/teacher")
    public ResponseEntity<?> validateForgotPassword(@RequestBody OTP otp)
    {
        try {
            System.out.println(otp.getUserOtp());
            boolean c = teacherService.validTeacherOtp(otp.getUserOtp(), otp.getUsername());
            System.out.println(c);
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping( value="/create/password/teacher" )
    public ResponseEntity<?> createNewPassword(@RequestBody PasswordDTO passwordDTO)
    {
//        try {
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.createPassword(passwordDTO.getUsername(), passwordDTO.getPassword()));
//        }
//        catch(Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
//        }
    }


}
