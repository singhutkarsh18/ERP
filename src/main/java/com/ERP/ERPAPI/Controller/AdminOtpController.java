package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Admin;
import com.ERP.ERPAPI.Entities.Mail;
import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class AdminOtpController {

    Admin newAdmin = new Admin();
    Mail mail=new Mail();
    @Autowired
    AdminRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/createAdmin")
    public void sendOTP(@RequestBody Admin admin) throws MessagingException
    {
        newAdmin.setEmail(admin.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int otp=otpService.generateOTP(admin.getEmail());
        String message="OTP for ERP is "+otp;
        mail.setRecipient(admin.getEmail());
        mail.setMessage(message);
        mail.setSubject("OTP");
        System.out.println(mail.getRecipient());
        System.out.println(mail.getMessage());
        otpService.sendMail(mail);
    }
    @PostMapping("/forgotAdminPassword")
    public void forgotPassword(@RequestParam String email) throws MessagingException
    {
//Check whether email is present in DB
        int otp=otpService.generateOTP(email);
        String message="OTP for ERP is "+otp;
        mail.setRecipient(email);
        mail.setMessage(message);
        mail.setSubject("OTP");
        System.out.println(mail.getRecipient());
        System.out.println(mail.getMessage());
        otpService.sendMail(mail);
    }
    @GetMapping("/validateAdminOtp")
    public @ResponseBody Boolean validateOtp(@RequestParam(name = "userOtp") Integer userOtp)
    {

        Boolean validOtp;
        System.out.println("User:"+mail.getRecipient());
        System.out.println("user:"+userOtp);
        if (userOtp >= 0) {
            int generatedOtp=otpService.getOtp(mail.getRecipient());
            if(generatedOtp>0)
            {
                if(userOtp==generatedOtp)
                {
                    validOtp=true;
                    repo.save(newAdmin);
                }
                else
                {
                    validOtp=false;
                }
            }
            else
            {
                validOtp=false;
            }
        }
        else
        {
            validOtp=false;
        }
        return validOtp;
    }
    @PostMapping("/createAdminNewPassword")
    public void createNewPassword(@RequestParam String pass)
    {
        newAdmin.setPassword(passwordEncoder.encode(pass));
        repo.save(newAdmin);
    }
}
