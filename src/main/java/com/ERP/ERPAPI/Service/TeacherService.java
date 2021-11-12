package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Teacher;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Repository.TeacherRepository;
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
    

}
