package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.OTP;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Model.StudentTemp;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Repository.StudentRepository;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> sendOTP(@RequestBody StudentTemp student)
    {
        HttpHeaders headers=new HttpHeaders();
        try {

        System.out.println(student.getUsername());
        System.out.println(student.getName());
        return ResponseEntity.status(HttpStatus.OK).body(studentService.create(student));
        }
        catch (Exception e) {
        headers.add("Message", "false");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to add the user");
        }
    }

    @PostMapping("/forgotStudentPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String,String> request)
    {
        try{
        return ResponseEntity.status(HttpStatus.OK).body(studentService.forgot(request.get("username")));
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/validateStudentOtp")
    public @ResponseBody ResponseEntity<?> validateOtp(@RequestBody OTP otp) {
        try{
            System.out.println(otp.getUserOtp());
            return ResponseEntity.status(HttpStatus.OK).body(studentService.validStudentOtp(otp.getUserOtp(),otp.getUsername()));
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
    @PostMapping("/validateStudentForgotPassword")
    public ResponseEntity<?> validateForgotPassword(@RequestBody OTP otp)
    {
        try {
            System.out.println(otp.getUserOtp());
            boolean c = studentService.validStudentOtp(otp.getUserOtp(), otp.getUsername());
            System.out.println(c);
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping( value="/createStudentNewPassword" )
    public ResponseEntity<?> createNewPassword(@RequestBody PasswordDTO passwordDTO)
    {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(studentService.createPassword(passwordDTO.getUsername(), passwordDTO.getPassword()));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
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
    @PostMapping("/resend/otp")
    public ResponseEntity<?> resendOTP(@RequestBody Username username)
    {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(studentService.resendOtp(username.getUsername()));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error");
        }
    }
}
