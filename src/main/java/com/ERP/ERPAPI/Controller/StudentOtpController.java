package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public String sendOTP(@RequestBody Student student) throws MessagingException
    {
        newStudent.setId(student.getId());
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        if(repo.existsAdminByEmail(student.getEmail())==false) {
            int otp = otpService.generateOTP(student.getEmail());
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(student.getEmail());
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
    @PostMapping("/forgotStudentPassword")
    public void forgotPassword(@RequestParam String email) throws MessagingException
    {

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
    public ResponseEntity<Student> createNewPassword(@RequestParam String pass)
    {
        newStudent.setPassword(passwordEncoder.encode(pass));
        return ResponseEntity.ok(repo.save(newStudent));
    }
}
