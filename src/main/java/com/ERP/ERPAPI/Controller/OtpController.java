package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Mail;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;


    @PostMapping("/sendOTP")
    public void sendOTP(@RequestBody String email) throws MessagingException
    {

        int otp=otpService.generateOTP(email);
        String message="OTP for ERP is "+otp;
        Mail mail =new Mail();
        mail.setRecipient(email);
        mail.setMessage(message);
        mail.setSubject("OTP");
        System.out.println(mail.getRecipient());
        System.out.println(mail.getMessage());
        otpService.sendMail(mail);
    }
    @GetMapping("/validateOtp")
    public @ResponseBody Boolean validateOtp(@RequestParam(name = "userOtp") int userOtp)
    {
        Boolean validOtp;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        if (userOtp >= 0) {
            int generatedOtp=otpService.getOtp(email);
            if(generatedOtp>0)
            {
                if(userOtp==generatedOtp)
                {
                    validOtp=true;
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
}
