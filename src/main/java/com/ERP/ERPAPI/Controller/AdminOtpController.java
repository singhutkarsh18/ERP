package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.AdminTemp;
import com.ERP.ERPAPI.Model.OTP;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AdminOtpController {


    @Autowired
    AdminRepository repo;
    @Autowired
    AdminService adminService;

    @PostMapping("/create/Admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminTemp admin)
    {
        try {
            return ResponseEntity.ok(adminService.create(admin));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
    @PostMapping("/forgot/AdminPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String,String> username)
    {
        try {
            return ResponseEntity.ok(adminService.forgotPassword(username.get("username")));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
    @PostMapping("/validate/AdminOtp")
    public @ResponseBody ResponseEntity<?> validateOtp(@RequestBody OTP otp)
    {
        try {
            return ResponseEntity.ok(adminService.validOtp(otp.getUserOtp(),otp.getUsername()));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
    @PostMapping("/create/AdminNewPassword")
    public ResponseEntity<?> createNewPassword(@RequestBody PasswordDTO passwordDTO)
    {
        try {
            return ResponseEntity.ok(adminService.createPassword(passwordDTO.getUsername(),passwordDTO.getPassword()));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }
    @PostMapping("/validate/forgetPassword")
    public ResponseEntity<?> validateForgotPassword(@RequestBody OTP otp)
    {
        try {
            return ResponseEntity.ok(adminService.validOtp(otp.getUserOtp(),otp.getUsername()));
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

}
