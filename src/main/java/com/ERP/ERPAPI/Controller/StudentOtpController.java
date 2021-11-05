package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Password;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.OtpService;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

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
    public String forgotPassword(@RequestBody Map<String,String> request) throws MessagingException
    {
        return studentService.forgot(request.get("username"));
    }
    @PostMapping("/validateStudentOtp")
    public @ResponseBody Boolean validateOtp(@RequestBody Map<String,Integer> request) {
        System.out.println(request.get("userOtp"));
        return studentService.validStudentOtp(request.get("userOtp"));
    }
    @PostMapping("/validateStudentForgotPassword")
    public Boolean validateForgotPassword(@RequestBody Map<String,Integer> userOtp)
    {
        System.out.println(userOtp.get("userOtp"));
        boolean c=studentService.validateForgotPassword(userOtp.get("userOtp"));
        System.out.println(c);
        return c;
    }
    @PostMapping( value="/createStudentNewPassword" )
    public String createNewPassword( @RequestBody Password password)
    {
        return studentService.createPassword(password.getPassword());
    }
    @PostMapping("/delete/allStudents")
    public String deleteAll()
    {
        repo.deleteAll();
        return "DB clear";
    }
}
