package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.Teacher;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Repository.ReportsRepository;
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
    @Autowired
    private ReportsRepository reportsRepository;
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
//    public List<Teacher> showAll()
//    {
//        List<Teacher> teachers=new ArrayList<>();
//        teachers =repo.findAll();
//        return teachers;
//    }
//    public String remove(Username username)
//    {
//        if (repo.existsTeacherByUsername(username)) {
//            repo.deleteByUsername(username);
//            return "Teacher removed from database";
//        }
//        else
//        {
//            return "Teacher not present";
//        }
//    }
//    public List<Report> showReports()
//    {
//        return reportsRepository.findAll();
//    }
}
