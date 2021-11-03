package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {


    Mail mail =new Mail();
    @Autowired
    StudentRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OtpService otpService;

    public String create(Student student){

        Student newStudent = new Student();
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(student.getUsername(),regexEmail)) {
            newStudent.setId(student.getId());
            newStudent.setName(student.getName());
            newStudent.setUsername(student.getUsername());
            newStudent.setValid(false);
            if (repo.existsStudentByUsername(student.getUsername()) == false) {
                int otp = otpService.generateOTP(student.getUsername());
                String message = "OTP for ERP is " + otp;
                mail.setRecipient(student.getUsername());
                mail.setMessage(message);
                mail.setSubject("OTP");
                System.out.println(mail.getRecipient());
                System.out.println(mail.getMessage());
                otpService.sendMail(mail);
                repo.save(newStudent);
                return "Valid Email\nOTP Sent";
            } else {
                return "User already present";
            }
        }
        else
        {
            return "Invalid email";
        }
    }
    public static boolean isValid(String emailOrPass,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailOrPass);
        return matcher.matches();
    }
    public boolean validStudentOtp(Integer userOtp)
    {
        try {
            Student newStudent = repo.findByUsername(mail.getRecipient());
            newStudent.setOTP(userOtp);
            Boolean validOtp;
            System.out.println("User:" + mail.getRecipient());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = otpService.getOtp(mail.getRecipient());
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        newStudent.setValid(true);
                        repo.save(newStudent);
//                        System.out.println();
                        validOtp = true;
                        otpService.clearOTP(mail.getRecipient());
                    } else {
                        validOtp = false;
                    }
                } else {
                    validOtp = false;
                }
            } else {
                validOtp = false;
            }
            return validOtp;
        }
        catch(NullPointerException n)
        {
            System.out.println(userOtp);
            return false;
        }
    }
    public String forgot(Student studentU)
    {
        String email = studentU.getUsername();
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)) {
            int otp = otpService.generateOTP(email);
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(email);
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            return "Valid Email\nOtp Sent";
        }
        else
        {
            return "Invalid Email";
        }
    }
    public String createPassword(String pass)
    {
        Student student = repo.findByUsername(mail.getRecipient());
        student.setPassword(pass);
        try {
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(student.getPassword(), regexPass) && student.getValid()) {
                student.setPassword(passwordEncoder.encode(student.getPassword()));
                ResponseEntity.ok(repo.save(student));
                return "Password Valid\nStudent SignUp Successful";
            }
            else{

                return "Invalid Password";
            }
        }
        catch(NullPointerException n)
        {
//            System.out.println(studentP.getPassword());
//            System.out.println(student.getPassword());
//            System.out.println(student.getUsername());
//            System.out.println(student.getValid());
            return "Null Password";
        }
    }


}
