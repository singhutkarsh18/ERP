package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Service.AdminService;
import com.ERP.ERPAPI.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class AdminDashboardController {

    @Autowired
    TeacherService service;
    @Autowired
    AdminService adminService;
    @PostMapping("/create/Teacher")
    public String createTeacher(@RequestBody Teacher teacher)
    {
       return this.service.create(teacher);
    }
    @GetMapping("/show/teachers")
    public List<Teacher> showAllTeachers()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        System.out.println(username+" "+userDetails.getPassword());

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

}
