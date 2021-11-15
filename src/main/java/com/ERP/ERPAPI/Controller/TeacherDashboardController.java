package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Attendance;
import com.ERP.ERPAPI.Model.Password;
import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Model.StudentDetails;
import com.ERP.ERPAPI.Repository.AttendanceRepo;
import com.ERP.ERPAPI.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TeacherDashboardController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    AttendanceRepo attendanceRepo;
    @PostMapping("/add/attendance")
    public ResponseEntity<?> addAttendance(@RequestBody Attendance attendance)
    {
        HttpHeaders headers=new HttpHeaders();
        try{
            attendanceRepo.save(attendance);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        catch(Exception e)
        {
            headers.add("Message", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to add attendance");
        }
    }
    @PostMapping("/update/password/teacher")
    public ResponseEntity<?> updateTeacherPassword(@RequestBody Password password)
    {
        HttpHeaders headers = new HttpHeaders();
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username=userDetails.getUsername();
            return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.changePassword(username,password.getPassword()));
        }
        catch (Exception e){
            headers.add("Message", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Error");
        }

    }
    @GetMapping("/show/students/class")
    public List<StudentDetails> showClasswise(@RequestBody Map<String, String> Cls)
    {
        //variable name is cls

        return teacherService.showClass(Cls.get("cls"));
    }
}
