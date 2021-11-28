package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TeacherService {


    Mail mail=new Mail();
    @Autowired
    private TeacherRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentDetailRepository studentDetailRepository;
    @Autowired
    private TeacherDetailsRepo teacherDetailsRepo;
    @Autowired
    private ClassAllotmentRepo classAllotmentRepo;
    @Autowired
    private ClassRepo classRepo;
    @Autowired
    private MarksRepo marksRepo;
    @Autowired
    private TeacherTempRepo teacherTempRepo;
    @Autowired
    private OtpService otpService;

    public String create(Teacher teacher)
    {
        if (!repo.existsTeacherByUsername(teacher.getUsername())) {
            Teacher newTeacher = new Teacher();
            newTeacher.setId(teacher.getId());
            newTeacher.setUsername(teacher.getUsername());
            newTeacher.setName(teacher.getName());
            newTeacher.setDepartment(teacher.getDepartment());
            newTeacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
            repo.save(newTeacher);
            return "Teacher Saved";
        }
        else
        {
            return "Teacher Already present";
        }

    }
    public List<Teacher> showAll()
    {
        List<Teacher> teachers=new ArrayList<>();
        teachers =repo.findAll();
        return teachers;
    }
    public String remove(Integer id)
    {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Teacher removed from database";
        }
        else
        {
            return "Teacher not present";
        }
    }
    public String changePassword(String username,String password)
    {
        if(repo.existsTeacherByUsername(username))
        {
            Teacher teacher=repo.findByUsername(username);
            teacher.setPassword(passwordEncoder.encode(password));
            repo.save(teacher);
            return "Password updated";
        }
        else
        {
            return  "User not present";
        }
    }
    public List<Teacher> foundByDepartment(String department)
    {
        List<Teacher> teachers=new ArrayList<>();
        if(repo.existsTeacherByDepartment(department))
        {
            teachers = repo.findTeacherByDepartment(department);
        }

        return teachers;

    }
    public List<AttendanceDTO> showClass(String cls)
    {
        List<StudentDetails> studentDetails=new ArrayList<>();
        studentDetails=studentDetailRepository.findByCls(cls);
        Iterator itr=studentDetails.iterator();
        List<AttendanceDTO> attendanceDTOs=new ArrayList<>();
        while(itr.hasNext())
        {
            StudentDetails studentDetails1= (StudentDetails) itr.next();
            AttendanceDTO attendanceDTO=new AttendanceDTO(studentDetails1.getUsername(),studentDetails1.getName(),studentDetails1.getStudentNo());
            attendanceDTOs.add(attendanceDTO);
        }
        return attendanceDTOs;
    }
    public String addDetails(TeacherDetails teacherDetails)
    {
            teacherDetailsRepo.save(teacherDetails);
            return "success";
    }
    public String classAllot(ClassAllotment classAllotment)
    {
        ClassTeachers classTeachers;
        try{
        if(classRepo.existsByCls(classAllotment.getCls())) {
            classTeachers = classRepo.findByCls(classAllotment.getCls());
        }
        else {
            classTeachers=new ClassTeachers(classAllotment.getCls(),"","","","","","");
        }
            String sub=classAllotment.getSubject();
            if(sub.equals("sub1")){
                classTeachers.setSub1(classAllotment.getUsername());
                classRepo.save(classTeachers);
            }
            else if(sub.equals("sub2")){
                classTeachers.setSub2(classAllotment.getUsername());
                classRepo.save(classTeachers);

            }
            else if(sub.equals("sub3")){
                classTeachers.setSub3(classAllotment.getUsername());
                classRepo.save(classTeachers);

            }
            else if(sub.equals("sub4")){
                classTeachers.setSub4(classAllotment.getUsername());
                classRepo.save(classTeachers);
            }
            else if(sub.equals("sub5")){
                classTeachers.setSub5(classAllotment.getUsername());
                classRepo.save(classTeachers);
            }
            else{
                classTeachers.setSub6(classAllotment.getUsername());
                classRepo.save(classTeachers);
            }
            classAllotmentRepo.save(classAllotment);
            return "Class Alloted";
        }
        catch(Exception e)
        {
            System.out.println(e);
            return "class not alloted";
        }
    }
//    public String showFeedback(String username)
//    {
//        ClassAllotment classAllotment=classAllotmentRepo.findByUsername(username);
//        ClassTeachers classTeachers=classRepo.findByCls(classAllotment.getCls());
//        if(classTeachers.getSub1().equals(username)){
//
//        }
//        else if(classTeachers.getSub2().equals(username)){
//
//        }
//        else if(classTeachers.getSub2().equals(username)){
//
//        }
//        else if(classTeachers.getSub3().equals(username)){
//
//        }
//        else if(classTeachers.getSub4().equals(username)){
//
//        }
//        else if(classTeachers.getSub5().equals(username)){
//
//        }
//        else{
//
//        }
//        return "";
//
//    }
    public String giveMarks(Marks marks)
    {
        Marks marks1;
        if(marksRepo.existsBySubjectAndUsername(marks.getSubject(), marks.getUsername())) {
            System.out.println(true);
            marks1=marksRepo.findBySubjectAndUsername(marks.getSubject(),marks.getUsername());
            marks1.setMarks(marks.getMarks());
        }
        else
            marks1=marks;
        marksRepo.save(marks1);
        return "marks saved";
    }
    public String forgot(String email)
    {
        Teacher teacher1 = repo.findByUsername(email);
        TeacherTemp teacher= new TeacherTemp();
        teacher.setName(teacher1.getName());
        teacher.setUsername(teacher1.getUsername());
        teacher.setPassword(teacher1.getPassword());
        String regexEmail="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        if(isValid(email,regexEmail)&&repo.existsTeacherByUsername(email)&&!teacherTempRepo.existsTeacherTempByUsername(email)) {
            int otp = otpService.generateOTP(email);
            teacher.setValid(false);
            teacher.setOTP(otp);
            teacherTempRepo.save(teacher);
            String message = "OTP for ERP is " + otp;
            mail.setRecipient(email);
            mail.setMessage(message);
            mail.setSubject("OTP");
            System.out.println(mail.getRecipient());
            System.out.println(mail.getMessage());
            otpService.sendMail(mail);
            return "Valid Email\nOtp Sent";
        }
        else if(teacherTempRepo.existsTeacherTempByUsername(email))
        {
            return "OTP already sent";
        }
        else
        {
            return "Invalid Email";
        }
    }
    public static boolean isValid(String emailOrPass,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailOrPass);
        return matcher.matches();
    }
    public boolean validTeacherOtp(Integer userOtp,String username)
    {
        try {
            TeacherTemp teacher= teacherTempRepo.findByUsername(username);
            Boolean validOtp;
            System.out.println("User:" + username);
            System.out.println("user:" + userOtp);

            if (userOtp >= 0) {
                int generatedOtp = teacher.getOTP();
                if (generatedOtp > 0) {
                    if (userOtp == generatedOtp) {
                        teacher.setValid(true);
                        teacherTempRepo.save(teacher);
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
    public String createPassword(String username,String password)
    {

        try {
            TeacherTemp teacher = teacherTempRepo.findByUsername(username);
            System.out.println(password);
            String regexPass = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
            if (isValid(password, regexPass) ) {
                if(teacher.getValid()) {
                    teacher.setPassword(passwordEncoder.encode(password));
                    Teacher teacher1 = new Teacher();
                    if (repo.existsTeacherByUsername(username))
                    {
                        Teacher temp=repo.findByUsername(username);
                        teacher1.setId(temp.getId());
                    }
                    teacher1.setName(teacher.getName());
                    teacher1.setUsername(teacher.getUsername());
                    teacher1.setPassword(teacher.getPassword());
                    repo.deleteByUsername(teacher.getUsername());
                    ResponseEntity.ok(repo.save(teacher1));
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
}
