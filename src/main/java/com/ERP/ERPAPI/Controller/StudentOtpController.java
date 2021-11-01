package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Entities.Mail;
import com.ERP.ERPAPI.Entities.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
public class StudentOtpController {

    Student newStudent = new Student();
    Mail mail=new Mail();
    @Autowired
    StudentRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/createStudent")
    public void sendOTP(@RequestBody Student student) throws MessagingException
    {
        newStudent.setId(student.getId());
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent.setPassword(passwordEncoder.encode(student.getPassword()));
        int otp=otpService.generateOTP(student.getEmail());
        String message="OTP for ERP is "+otp;
        mail.setRecipient(student.getEmail());
        mail.setMessage(message);
        mail.setSubject("OTP");
        System.out.println(mail.getRecipient());
        System.out.println(mail.getMessage());
        otpService.sendMail(mail);
    }
    @PostMapping("/forgotStudentPassword")
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
    @GetMapping("/validateStudentOtp")
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
                    repo.save(newStudent);
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
    @GetMapping("/validateStudentForgotPassword")
    public Boolean validateForgotPassword(@RequestParam(name="userOtp") Integer userOtp)
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
    @PostMapping("/createStudentNewPassword")
    public void createNewPassword(@RequestParam String pass)
    {
        newStudent.setPassword(passwordEncoder.encode(pass));
        repo.save(newStudent);
    }
}
