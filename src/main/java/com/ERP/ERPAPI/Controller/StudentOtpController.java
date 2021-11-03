package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
public class StudentOtpController {

    Mail mail=new Mail();
    @Autowired
    StudentRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    StudentService studentService;
    @PostMapping("/createStudent")
    public String sendOTP(@RequestBody Student student) throws MessagingException
    {
        return studentService.create(student);
    }

    @PostMapping("/forgotStudentPassword")
    public String forgotPassword(@RequestBody Student studentU) throws MessagingException
    {
        return studentService.forgot(studentU);
    }
    @PostMapping("/validateStudentOtp")
    public @ResponseBody Boolean validateOtp(@RequestParam("userOtp") int userOtp) {
        return studentService.validStudentOtp(userOtp);
    }
    @PostMapping("/validateStudentForgotPassword")
    public Boolean validateForgotPassword(@RequestParam int userOtp)
    {
        return studentService.validStudentOtp(userOtp);
    }
    @PostMapping("/createStudentNewPassword")
    public String createNewPassword(@RequestParam("pass") String pass)
    {
        return studentService.createPassword(pass);
    }
}
