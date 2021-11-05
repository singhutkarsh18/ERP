package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    Student newStudent=new Student();
    Mail mail =new Mail();
    @Autowired
    StudentRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OtpService otpService;

    public String create(Student student){
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(student.getUsername(),regexEmail)) {
            newStudent.setId(student.getId());
            newStudent.setName(student.getName());
            newStudent.setUsername(student.getUsername());
            newStudent.setValid(false);
            if (!repo.existsStudentByUsername(student.getUsername())) {
                int otp = otpService.generateOTP(student.getUsername());
                newStudent.setOTP(otp);
                String message = "OTP for ERP is " + otp;
                mail.setRecipient(student.getUsername());
                mail.setMessage(message);
                mail.setSubject("OTP");
                System.out.println(mail.getRecipient());
                System.out.println(mail.getMessage());
                otpService.sendMail(mail);
                repo.save(newStudent);
                return "Valid Email OTP Sent";
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

            Boolean validOtp;
            System.out.println("User:" + mail.getRecipient());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = newStudent.getOTP();
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
            System.out.println(validOtp);
            return validOtp;
        }
        catch(NullPointerException n)
        {
            System.out.println("UserOtp:"+userOtp);
            System.out.println(userOtp);
            return false;
        }
    }
    public boolean validateForgotPassword(Integer userOtp)
    {
        try {

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
            System.out.println(validOtp);
            return validOtp;
        }
        catch(NullPointerException n)
        {
            System.out.println("UserOtp:"+userOtp);
            System.out.println(userOtp);
            return false;
        }
    }
    public String forgot(String email)
    {
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&repo.existsStudentByUsername(email)) {
            int otp = otpService.generateOTP(email);

            newStudent.setOTP(otp);
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

        try {
        System.out.println(pass);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(pass, regexPass) && newStudent.getValid()) {
                newStudent.setPassword(passwordEncoder.encode(pass));
                ResponseEntity.ok(repo.save(newStudent));
                return "Password Valid\nStudent SignUp Successful";
            }
            else{

                return "Invalid Password";
            }
        }
        catch(NullPointerException n)
        {
            return "Null Password";
        }
    }


}
