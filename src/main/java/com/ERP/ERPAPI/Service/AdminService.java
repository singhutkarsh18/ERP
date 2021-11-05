package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminService {

    Admin newAdmin = new Admin();
    Mail mail=new Mail();
    @Autowired
    AdminRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String create(Admin admin)
    {
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(admin.getUsername(),regexEmail)) {
            newAdmin.setId(admin.getId());
            newAdmin.setName(admin.getName());
            newAdmin.setUsername(admin.getUsername());
            newAdmin.setValid(false);
            if (repo.existsAdminByUsername(admin.getUsername()) == false) {
                int otp = otpService.generateOTP(admin.getUsername());
                newAdmin.setOTP(otp);
                System.out.println("OTP SENT");
                String message = "OTP for ERP is " + otp;
                mail.setRecipient(admin.getUsername());
                mail.setMessage(message);
                mail.setSubject("OTP");
                System.out.println(mail.getRecipient());
                System.out.println(mail.getMessage());
                otpService.sendMail(mail);
                return "Valid email OTP Sent";
            }
            else
            {
                return "User already present";
            }
        }
        else {
            return "Invalid email";
        }

    }
    public static boolean isValid(String emailOrPass,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailOrPass);
        return matcher.matches();
    }
    public Boolean validOtp(int userOtp)
    {
        try {

            Boolean validOtp;
            System.out.println("User:" + mail.getRecipient());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = newAdmin.getOTP();
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        newAdmin.setValid(true);
                        repo.save(newAdmin);
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
    public String createPassword(String pass)
    {
        try {
            System.out.println(pass);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(pass, regexPass) ) {
                if(newAdmin.getValid()) {
                    newAdmin.setPassword(passwordEncoder.encode(pass));
                    ResponseEntity.ok(repo.save(newAdmin));
                    return "Password Valid\nStudent SignUp Successful";
                }
                else
                {
                    return "Admin not validated through OTP";
                }
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
    public String forgotPassword(String email)
    {
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&repo.existsAdminByUsername(email)) {
            int otp = otpService.generateOTP(email);

            newAdmin.setOTP(otp);
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
    public Boolean validForgotOtp(int userOtp)
    {
        try {

            Boolean validOtp;
            System.out.println("User:" + mail.getRecipient());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = otpService.getOtp(mail.getRecipient());
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        newAdmin.setValid(true);
                        repo.save(newAdmin);
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
}
