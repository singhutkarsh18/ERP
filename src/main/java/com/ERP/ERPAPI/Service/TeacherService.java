package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

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
    public String remove(Username username)
    {
        if (repo.existsTeacherByUsername(username.getUsername())) {
            repo.deleteByUsername(username.getUsername());
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
    public List<StudentDetails> showClass(String cls)
    {
        List<StudentDetails> studentDetails=new ArrayList<>();
        studentDetails=studentDetailRepository.findByCls(cls);
        return studentDetails;
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

}
