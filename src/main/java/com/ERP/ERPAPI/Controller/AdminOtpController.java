package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create/Admin")
    public String sendOTP(@RequestBody Admin admin) throws MessagingException
    {
        newAdmin.setUsername(admin.getUsername());

        if(repo.existsAdminByUsername(admin.getUsername())==false) {
            int otp = otpService.generateOTP(admin.getUsername());
            System.out.println("OTP SENT");
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(admin.getUsername());
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
    @PostMapping("/forgot/AdminPassword")
    public void forgotPassword(@RequestBody Map<String,String> email) throws MessagingException
    {

            int otp = otpService.generateOTP(email.get("email"));
            System.out.println("OTP Sent");
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(email.get("email"));
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
    }
    @GetMapping("/validate/AdminOtp")
    public @ResponseBody Boolean validateOtp(@RequestBody Map<String,Integer> userOtp1)
    {

        Boolean validOtp;
        int userOtp=userOtp1.get("userOtp");
        System.out.println("User:"+mail.getRecipient());
        System.out.println("user:"+userOtp);
        if (userOtp >= 0) {
            int generatedOtp=otpService.getOtp(mail.getRecipient());
            if(generatedOtp>0)
            {
                if(userOtp==generatedOtp)
                {
                    validOtp=true;
                    ResponseEntity.ok(repo.save(newAdmin));
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
    @PostMapping("/create/AdminNewPassword")
    public void createNewPassword(@RequestBody Map<String,String> pass)
    {
        newAdmin.setPassword(passwordEncoder.encode(pass.get("pass")));
        ResponseEntity.ok(repo.save(newAdmin));
    }
}
