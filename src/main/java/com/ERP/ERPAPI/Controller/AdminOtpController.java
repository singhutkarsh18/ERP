package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.OTP;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Service.AdminService;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AdminOtpController {

    Admin newAdmin = new Admin();
    Mail mail=new Mail();
    @Autowired
    AdminRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AdminService adminService;

    @PostMapping("/create/Admin")
    public String createAdmin(@RequestBody Admin admin) throws MessagingException
    {
      return adminService.create(admin);
    }
    @PostMapping("/forgot/AdminPassword")
    public String forgotPassword(@RequestBody Map<String,String> username) throws MessagingException
    {
        return adminService.forgotPassword(username.get("username"));
    }
    @PostMapping("/validate/AdminOtp")
    public @ResponseBody Boolean validateOtp(@RequestBody OTP otp)
    {
        return adminService.validOtp(otp.getUserOtp(),otp.getUsername());
    }
    @PostMapping("/create/AdminNewPassword")
    public String createNewPassword(@RequestBody PasswordDTO passwordDTO)
    {
        return adminService.createPassword(passwordDTO.getUsername(),passwordDTO.getPassword());
    }
    @PostMapping("/validate/forgetPassword")
    public Boolean validateForgotPassword(@RequestBody OTP otp)
    {
        return adminService.validOtp(otp.getUserOtp(),otp.getUsername());
    }

}
