package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Mail;
import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class OtpController {

    Mail mail=new Mail();
    @Autowired
    private OtpService otpService;


    @PostMapping("/sendOTP")
    public void sendOTP(@RequestBody Student student) throws MessagingException
    {
        int otp=otpService.generateOTP(student.getEmail());
        String message="OTP for ERP is "+otp;
        mail.setRecipient(student.getEmail());
        mail.setMessage(message);
        mail.setSubject("OTP");
        System.out.println(mail.getRecipient());
        System.out.println(mail.getMessage());
        otpService.sendMail(mail);
    }
    @GetMapping("/validateOtp")
    public @ResponseBody Boolean validateOtp(@RequestParam(name = "userOtp") Integer userOtp)
    {

        Boolean validOtp;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
        System.out.println("User:"+mail.getRecipient());
        System.out.println("user:"+userOtp);
        if (userOtp >= 0) {
            int generatedOtp=otpService.getOtp(mail.getRecipient());
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
