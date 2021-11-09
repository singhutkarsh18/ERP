package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.Teacher;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Service.AdminService;
import com.ERP.ERPAPI.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PostMapping("/show/teachers")
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
    @PostMapping("/show/Reports")
    public List<Report> viewReport()
    {
        return adminService.showReports();
    }
    @PostMapping("/update/password/Admin")
    public String changePassword(@RequestBody PasswordDTO passwordDTO)
    {
        return adminService.changePassword(passwordDTO.getUsername(),passwordDTO.getPassword());
    }
    @PostMapping("/hello1")
    public String hello()
    {
        return "Hello";
    }
}
