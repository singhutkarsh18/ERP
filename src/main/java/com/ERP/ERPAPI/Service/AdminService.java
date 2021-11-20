package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AdminService {

    Mail mail=new Mail();
    @Autowired
    AdminTempRepository repo;
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
    @Autowired
    AdminRepository adminRepository;
    public String create(AdminTemp admin)
    {
        AdminTemp admin1=new AdminTemp();
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(admin.getUsername(),regexEmail)) {
            admin1.setName(admin.getName());
            admin1.setUsername(admin.getUsername());
            admin1.setValid(false);
            if (!adminRepository.existsAdminByUsername(admin.getUsername())&&!repo.existsAdminByUsername(admin.getUsername())) {
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
            else if(repo.existsAdminByUsername(admin.getUsername())){
                AdminTemp admin2=repo.findByUsername(admin.getUsername());
                if (admin2.getValid())
                {
                    return "Otp verified create password";
                }
                else
                {
                    if(!otpService.otpExpired(admin2.getOTP(),admin2.getUsername()))
                    {
                        int otp = otpService.generateOTP(admin2.getUsername());
                        admin2.setId(admin2.getId());
                        admin2.setOTP(otp);
                        String message = "OTP for ERP is " + otp;
                        mail.setRecipient(admin2.getUsername());
                        mail.setMessage(message);
                        mail.setSubject("OTP");
                        System.out.println(mail.getRecipient());
                        System.out.println(mail.getMessage());
                        otpService.sendMail(mail);
                        repo.save(admin2);
                    }
                    return "User not verified";
                }


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
    public Boolean validOtp(Integer userOtp,String username)
    {
        try {
            AdminTemp admin = repo.findByUsername(username);
            Boolean validOtp;
            System.out.println("User:" + admin.getUsername());
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp =admin.getOTP();
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        admin.setValid(true);
                        repo.save(admin);
//                        System.out.println();
                        validOtp = true;
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
            System.out.println("nullll");
            System.out.println("UserOtp:"+userOtp);
            System.out.println(userOtp);
            return false;
        }
    }
    public String createPassword(String username,String password)
    {
        try {

                AdminTemp admin =repo.findByUsername(username);
                System.out.println(admin.getValid());
                System.out.println(username);
                System.out.println(password);
                String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
                if (isValid(password, regexPass)) {
                    if (admin.getValid()) {
                        admin.setPassword(passwordEncoder.encode(password));
                        Admin admin1 = new Admin();
                        if (adminRepository.existsAdminByUsername(username))
                        {
                            Admin temp=adminRepository.findByUsername(username);
                            admin1.setId(temp.getId());
                        }
                        else
                            admin1.setId(admin.getId());
                        admin1.setName(admin.getName());
                        admin1.setUsername(admin.getUsername());
                        admin1.setPassword(admin.getPassword());
                        repo.deleteByUsername(admin.getUsername());
                        ResponseEntity.ok(adminRepository.save(admin1));
                        return "Password Valid SignUp Successful";
                    } else {
                        return "Admin not validated through OTP";
                    }
                } else {

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
        Admin admin1=adminRepository.findByUsername(email);
        AdminTemp admin = new AdminTemp();
        admin.setName(admin1.getName());
        admin.setUsername(admin1.getUsername());
        admin.setPassword(admin1.getPassword());
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&adminRepository.existsAdminByUsername(email)) {
            int otp = otpService.generateOTP(email);
            admin.setValid(false);
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
            Admin admin = adminRepository.findByUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);
            return "Password updated";
    }
    public String addAnnouncement(Announcement announcement)
    {
        announcementRepository.save(announcement);
        return "Announcement added";
    }
    public String removeAnnounecemnt(String date)
    {
        if(announcementRepository.existsByDate(date))
        {
            announcementRepository.deleteByDate(date);
            return "deleted";
        }
        else
        {
            return "announcement not present";
        }

    }
}
