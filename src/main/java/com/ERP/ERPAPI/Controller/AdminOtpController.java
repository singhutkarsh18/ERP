package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

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
    public String sendOTP(@RequestBody Admin admin) throws MessagingException
    {
        newAdmin.setEmail(admin.getEmail());

        if(repo.existsAdminByEmail(admin.getEmail())==false) {
            int otp = otpService.generateOTP(admin.getEmail());
            System.out.println("OTP SENT");
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(admin.getEmail());
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            return "OTP Sent";
        }
        else
        {
            return "User already present";
        }
    }
    @PostMapping("/forgotAdminPassword")
    public void forgotPassword(@RequestParam String email) throws MessagingException
    {

            int otp = otpService.generateOTP(email);
            System.out.println("OTP Sent");
            String message = "OTP for ERP is " + otp;
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
                    otpService.clearOTP(mail.getRecipient());
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
