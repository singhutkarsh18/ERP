package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.AdminRepository;
import com.ERP.ERPAPI.Repository.AnnouncementRepository;
import com.ERP.ERPAPI.Repository.ReportsRepository;
import com.ERP.ERPAPI.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminService {

    Mail mail=new Mail();
    @Autowired
    AdminRepository repo;
    @Autowired
    private OtpService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ReportsRepository reportsRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    public String create(Admin admin)
    {
        Admin admin1=new Admin();
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(admin.getUsername(),regexEmail)) {
            admin1.setId(admin.getId());
            admin1.setName(admin.getName());
            admin1.setUsername(admin.getUsername());
            admin1.setValid(false);
            if (!repo.existsAdminByUsername(admin.getUsername())) {
                int otp = otpService.generateOTP(admin.getUsername());
                admin1.setOTP(otp);
                System.out.println("OTP SENT");
                String message = "OTP for ERP is " + otp;
                mail.setRecipient(admin.getUsername());
                mail.setMessage(message);
                mail.setSubject("OTP");
                System.out.println(mail.getRecipient());
                System.out.println(mail.getMessage());
                otpService.sendMail(mail);
                repo.save(admin1);
                return "Valid email OTP Sent";
            }
            else
            {
                Admin admin2= repo.findByUsername(admin.getUsername());
                if (admin2.getPassword() == null && admin2.getValid())
                    return "Otp verified create password";
                else {
                    return "User already present";
                }
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
    public Boolean validOtp(Integer userOtp,String username)
    {
        try {
            Admin admin = repo.findByUsername(username);
            Boolean validOtp;
            System.out.println("User:" + mail.getRecipient());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp =admin.getOTP();
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        admin.setValid(true);
                        repo.save(admin);
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
    public String createPassword(String username,String password)
    {
        try {
            Admin admin =repo.findByUsername(username);
            System.out.println(password);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(password, regexPass) ) {
                if(admin.getValid()) {
                    admin.setPassword(passwordEncoder.encode(password));
                    ResponseEntity.ok(repo.save(admin));
                    return "Password Valid SignUp Successful";
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
            Admin admin = repo.findByUsername(email);
            int otp = otpService.generateOTP(email);

            admin.setOTP(otp);
            repo.save(admin);
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
//    public Boolean validForgotOtp(int userOtp)
//    {
//        try {
//
//            Boolean validOtp;
//            System.out.println("User:" + mail.getRecipient());
//            System.out.println("user:" + userOtp);
//
//            if (userOtp >= 0) {
//                int generatedOtp = otpService.getOtp(mail.getRecipient());
//                if (generatedOtp > 0) {
//                    if (userOtp == generatedOtp) {
//                        newAdmin.setValid(true);
//                        repo.save(newAdmin);
//                        validOtp = true;
//                        otpService.clearOTP(mail.getRecipient());
//                    } else {
//                        validOtp = false;
//                    }
//                } else {
//                    validOtp = false;
//                }
//            } else {
//                validOtp = false;
//            }
//            System.out.println(validOtp);
//            return validOtp;
//        }
//        catch(NullPointerException n)
//        {
//            System.out.println("UserOtp:"+userOtp);
//            System.out.println(userOtp);
//            return false;
//        }
//    }
    public List<Report> showReports()
    {
        return reportsRepository.findAll();
    }
    public String changePassword(String username,String password)
    {
        if(teacherRepository.existsTeacherByUsername(username))
        {
            Admin admin = repo.findByUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            repo.save(admin);
            return "Password updated";
        }
        else
        {
            return  "User not present";
        }
    }
    public String addAnnouncement(Announcement announcement)
    {
        announcementRepository.save(announcement);
        return "Announcement added";
    }
}
