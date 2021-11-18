package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.OTP;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Model.StudentTemp;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class StudentOtpController {


    @Autowired
    StudentRepository repo;

    @Autowired
    StudentService studentService;

    @PostMapping("/createStudent")
    public String sendOTP(@RequestBody StudentTemp student)
    {
//        HttpHeaders headers=new HttpHeaders();
//        try {

        System.out.println(student.getUsername());
        System.out.println(student.getName());
        return studentService.create(student);
//    }
//        catch (Exception e) {
//        headers.add("Message", "false");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to add the user");
//    }
    }

    @PostMapping("/forgotStudentPassword")
    public String forgotPassword(@RequestBody Map<String,String> request)
    {
        return studentService.forgot(request.get("username"));
    }
    @PostMapping("/validateStudentOtp")
    public @ResponseBody Boolean validateOtp(@RequestBody OTP otp) {
        System.out.println(otp.getUserOtp());
        return studentService.validStudentOtp(otp.getUserOtp(),otp.getUsername());
    }
    @PostMapping("/validateStudentForgotPassword")
    public Boolean validateForgotPassword(@RequestBody OTP otp)
    {
        System.out.println(otp.getUserOtp());
        boolean c=studentService.validStudentOtp(otp.getUserOtp(),otp.getUsername());
        System.out.println(c);
        return c;
    }
    @PostMapping( value="/createStudentNewPassword" )
    public String createNewPassword( @RequestBody PasswordDTO passwordDTO)
    {
        return studentService.createPassword(passwordDTO.getUsername(),passwordDTO.getPassword());
    }
    @PostMapping("/delete/allStudents")
    public String deleteAll()
    {
        repo.deleteAll();
        return "DB clear";
    }
    @PostMapping("/hello")
    public String hello()
    {
        return "Hello";
    }
}
