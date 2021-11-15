package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Service.AdminService;
import com.ERP.ERPAPI.Service.StudentService;
import com.ERP.ERPAPI.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
@CrossOrigin("*")
public class AdminDashboardController {

    @Autowired
    TeacherService service;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @PostMapping("/create/Teacher")
    public String createTeacher(@RequestBody Teacher teacher)
    {
       //Add validation
        return this.service.create(teacher);
    }
    @GetMapping("/show/teachers")
    public List<Teacher> showAllTeachers()
    {

        return service.showAll();
    }
    @PostMapping("/delete/teacher")
    public String removeTeacher(@RequestBody Username username)
    {
        return this.service.remove(username);
    }
    @GetMapping("/show/Reports")
    public List<Report> viewReport()
    {
        return adminService.showReports();
    }
    @PostMapping("/update/password/Admin")
    public String changePassword(@RequestBody Password password)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        return adminService.changePassword(username,password.getPassword());
    }
    @PostMapping("/hello1")
    public String hello()
    {
        return "Hello";
    }
    @PostMapping("/create/announcement")
    public String createAnnouncement(@RequestBody Announcement announcement)
    {
        return adminService.addAnnouncement(announcement);
    }

    @PostMapping("/delete/announcement")
    public String deleteAnnouncement(@RequestBody Map<String,String> Date)
    {
        return adminService.removeAnnounecemnt(Date.get("date"));
    }
    @PostMapping("/show/teacher/byDepartment")
    public List<Teacher> showTeacherByDepartment(@RequestBody Map<String,String> Department)
    {
        return service.foundByDepartment(Department.get("department"));
    }
//    @PostMapping("/update/teacher")
//    public ResponseEntity<?> updateTeacher(@RequestBody Map<String,String> Username)
//    {
//
//    }

}
