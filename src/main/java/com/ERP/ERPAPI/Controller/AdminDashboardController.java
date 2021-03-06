package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Service.AdminService;
import com.ERP.ERPAPI.Service.StudentService;
import com.ERP.ERPAPI.Service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.spec.ECField;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
@CrossOrigin("*")@AllArgsConstructor
public class AdminDashboardController {

    @Autowired
    TeacherService service;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @PostMapping("/create/Teacher")
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher)
    {
       try{
            return ResponseEntity.ok(this.service.create(teacher));
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
       }
    }
    @GetMapping("/show/teachers")
    public ResponseEntity<?> showAllTeachers()
    {
        try{
            return ResponseEntity.ok(service.showAll());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/delete/teacher")
    public ResponseEntity<?> removeTeacher(@RequestBody Map<String ,Integer> id)
    {
        try{
            return ResponseEntity.ok(service.remove(id.get("id")));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @GetMapping("/show/Reports")
    public ResponseEntity<?> viewReport()
    {
        try{
            return ResponseEntity.ok(adminService.showReports());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/update/password/Admin")
    public ResponseEntity<?> changePassword(@RequestBody Password password)
    {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            System.out.println(username);
            return ResponseEntity.ok(adminService.changePassword(username, password.getPassword()));
        }
        catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
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
    public String deleteAnnouncement(@RequestBody Map<String,Integer> Id)
    {
        return adminService.removeAnnounecemnt(Id.get("id"));
    }
    @PostMapping("/show/teacher/byDepartment")
    public List<Teacher> showTeacherByDepartment(@RequestBody Map<String,String> Department)
    {
        return service.foundByDepartment(Department.get("department"));
    }

}
