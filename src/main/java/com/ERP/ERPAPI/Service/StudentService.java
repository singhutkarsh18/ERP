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
public class StudentService {

    Mail mail =new Mail();
    @Autowired
    StudentTempRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ReportsRepository reportsRepository;
    @Autowired
    OtpService otpService;
    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    StudentRepository  studentRepository;
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    FeedbackRepo feedbackRepo;
    @Autowired
    private MarksRepo marksRepo;
    @Autowired
    private TeacherTempRepo teacherTempRepo;
    @Autowired
    private AdminTempRepository adminTempRepository;

    public String create(StudentTemp student){
        StudentTemp student1 =new StudentTemp();

        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(student.getUsername(),regexEmail)) {
            student1.setName(student.getName());
            student1.setUsername(student.getUsername());
            student1.setValid(false);
            System.out.println(studentRepository.existsStudentByUsername(student.getUsername())+" "+repo.existsStudentByUsername(student.getUsername()));
            if ((!studentRepository.existsStudentByUsername(student.getUsername()))&&(!repo.existsStudentByUsername(student.getUsername()))) {
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
            } else if(repo.existsStudentByUsername(student.getUsername())){
                    StudentTemp student2=repo.findByUsername(student.getUsername());
                if (student2.getValid())
                {
                    return "Otp verified create password";
                }
                else
                {
                    if(!otpService.otpExpired(student2.getOTP(),student2.getUsername()))
                    {
                        int otp = otpService.generateOTP(student2.getUsername());
                        student2.setId(student2.getId());
                        student2.setOTP(otp);
                        String message = "OTP for ERP is " + otp;
                        mail.setRecipient(student2.getUsername());
                        mail.setMessage(message);
                        mail.setSubject("OTP");
                        System.out.println(mail.getRecipient());
                        System.out.println(mail.getMessage());
                        otpService.sendMail(mail);
                        repo.save(student2);
                    }
                    return "User not verified";
                }

            }
            else {
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
    public boolean validStudentOtp(Integer userOtp,String username)
    {
        try {
            StudentTemp student= repo.findByUsername(username);
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
            StudentTemp student = repo.findByUsername(username);
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
        Student student1 = studentRepository.findByUsername(email);
        StudentTemp student =new StudentTemp();
        student.setName(student1.getName());
        student.setUsername(student1.getUsername());
        student.setPassword(student1.getPassword());
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&studentRepository.existsStudentByUsername(email)&&!repo.existsStudentByUsername(email)) {
            int otp = otpService.generateOTP(email);
            int id=student1.getId();
            student.setId(id);
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
        else if(repo.existsStudentByUsername(email))
        {
            return "OTP already sent";
        }
        else
        {
            return "Invalid Email";
        }
    }
    public String createPassword(String username,String password)
    {

        try {
            StudentTemp student = repo.findByUsername(username);
            System.out.println(password);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(password, regexPass) ) {
                if(student.getValid()) {
                    student.setPassword(passwordEncoder.encode(password));
                    Student student1 = new Student();
                    if (studentRepository.existsStudentByUsername(username))
                    {
                        Student temp=studentRepository.findByUsername(username);
                        student1.setId(temp.getId());
                    }
                    student1.setName(student.getName());
                    student1.setUsername(student.getUsername());
                    student1.setPassword(student.getPassword());
                    repo.deleteByUsername(student.getUsername());
                    ResponseEntity.ok(studentRepository.save(student1));
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
        if(studentRepository.existsStudentByUsername(username)) {
            Student student = studentRepository.findByUsername(username);
            student.setPassword(passwordEncoder.encode(password));
            studentRepository.save(student);
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
    public void addStudentNo(String username,Integer sno)
    {
        Student student= studentRepository.findByUsername(username);
        student.setId(student.getId());
        student.setStudentNo(sno);
        studentRepository.save(student);
    }
    public List<Attendance> getAttendance(String username)
    {
        return attendanceRepo.findAttendanceByUsername(username);
    }
    public String addFeedback(Feedback feedback)
    {

        try {
            feedbackRepo.save(feedback);
            return "feedback added";
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "Error";
        }
    }
    public List<Marks> showMarks(String username)
    {
        List<Marks> marks=marksRepo.findAllByUsername(username);

        return marks;
    }
    public String resendOtp(String username)
    {
        if(repo.existsStudentByUsername(username))
        {
            StudentTemp student =repo.findByUsername(username);
            int id=student.getId();
            student.setId(id);
            int otp = otpService.generateOTP(username);
            student.setValid(false);
            student.setOTP(otp);
            repo.save(student);
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(username);
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            repo.deleteByUsername(username);
            return "OTP resent";

        }
        else if (adminTempRepository.existsAdminByUsername(username))
        {
            AdminTemp adminTemp=adminTempRepository.findByUsername(username);
            int id=adminTemp.getId();
            adminTemp.setId(id);
            int otp = otpService.generateOTP(username);
            adminTemp.setValid(false);
            adminTemp.setOTP(otp);
            adminTempRepository.save(adminTemp);
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(username);
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            adminTempRepository.deleteByUsername(username);
            return "OTP resent";
        }
        else if (teacherTempRepo.existsTeacherTempByUsername(username))
        {
            TeacherTemp teacherTemp= teacherTempRepo.findByUsername(username);
            int id=teacherTemp.getId();
            teacherTemp.setId(id);
            int otp = otpService.generateOTP(username);
            teacherTemp.setValid(false);
            teacherTemp.setOTP(otp);
            teacherTempRepo.save(teacherTemp);
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(username);
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            teacherTempRepo.deleteByUsername(username);
            return "OTP resent";
        }
        else
        {
            return "OTP not sent";
        }
    }

}
