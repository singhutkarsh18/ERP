package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Announcement;
import com.ERP.ERPAPI.Model.Mail;
import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Repository.AnnouncementRepository;
import com.ERP.ERPAPI.Repository.ReportsRepository;
import com.ERP.ERPAPI.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    ReportsRepository reportsRepository;
    @Autowired
    OtpService otpService;
    @Autowired
    AnnouncementRepository announcementRepository;

    public String create(Student student){
        Student student1 =new Student();

        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(student.getUsername(),regexEmail)) {
            student1.setId(student.getId());
            student1.setName(student.getName());
            student1.setUsername(student.getUsername());
            student1.setValid(false);
            if (!repo.existsStudentByUsername(student.getUsername())) {
                int otp = otpService.generateOTP(student.getUsername());
                student1.setOTP(otp);
                String message = "OTP for ERP is " + otp;
                mail.setRecipient(student.getUsername());
                mail.setMessage(message);
                mail.setSubject("OTP");
                System.out.println(mail.getRecipient());
                System.out.println(mail.getMessage());
                otpService.sendMail(mail);
                repo.save(student1);
                return "Valid Email OTP Sent";
            } else{
                Student student2 = repo.findByUsername(student.getUsername());
                if (student2.getPassword() == null && student2.getValid())
                    return "Otp verified create password";
                else {
                    return "User already present";
                }
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
    public boolean validStudentOtp(Integer userOtp,String username)
    {
        try {
            Student student= repo.findByUsername(username);
            Boolean validOtp;
            System.out.println("User:" + username);
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = student.getOTP();
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        student.setValid(true);
                        repo.save(student);
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
            System.out.println("UserOtp:"+userOtp);
            System.out.println(userOtp);
            return false;
        }
    }
    public boolean validateForgotPassword(Integer userOtp,String username)
    {
        try {
            Student student = repo.findByUsername(username);
            Boolean validOtp;
            System.out.println("User:" + username);
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = student.getOTP();
                System.out.println(generatedOtp);
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        student.setValid(true);
                        validOtp = true;
                        repo.save(student);
//                        System.out.println();
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
        Student student = repo.findByUsername(email);
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&repo.existsStudentByUsername(email)) {
            int otp = otpService.generateOTP(email);
            student.setValid(false);
            student.setOTP(otp);
            repo.save(student);
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
    public String createPassword(String username,String pass)
    {

        try {
            Student student = repo.findByUsername(username);
        System.out.println(pass);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(pass, regexPass) ) {
                if(student.getValid()) {
                    student.setPassword(passwordEncoder.encode(pass));
                    ResponseEntity.ok(repo.save(student));
                    return "Password Valid\nStudent SignUp Successful";
                }
                else
                {
                    return "Student not validated through OTP";
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
    public String changePassword(String username,String password)
    {
        if(repo.existsStudentByUsername(username)) {
            Student student = repo.findByUsername(username);
            student.setPassword(passwordEncoder.encode(password));
            repo.save(student);
            return "Password updated";
        }
        else{
            return "User not present";
        }
    }
    public String reportProblem(Report report)
    {
        Report newReport = new Report();
        newReport.setUser(report.getUser());
        newReport.setDate(report.getDate());
        newReport.setProblem(report.getProblem());
        reportsRepository.save(newReport);
        return "Report saved";
    }
    public List<Announcement> announcementList()
    {
        return announcementRepository.findAll();
    }


}
