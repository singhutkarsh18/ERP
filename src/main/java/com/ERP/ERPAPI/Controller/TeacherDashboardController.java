package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Repository.AttendanceRepo;
import com.ERP.ERPAPI.Repository.TeacherDetailsRepo;
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
    @Autowired
    TeacherDetailsRepo teacherDetailsRepo;

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
    @PostMapping("/add/details/teacher")
    public ResponseEntity<?> addTeacherDetails(@RequestBody TeacherDetails teacherDetails)
    {
        HttpHeaders headers = new HttpHeaders();
        try{
            return ResponseEntity.status(HttpStatus.OK).body(teacherService.addDetails(teacherDetails));
        }
        catch (Exception e)
        {
            headers.add("Message","false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Error");
        }
    }
    @GetMapping("/show/details/teacher")
    public ResponseEntity<?> showTeacherDetails(@RequestBody Map<String, String> Username)
    {
        HttpHeaders headers = new HttpHeaders();
        try{
            if(teacherDetailsRepo.existsByUsername(Username.get("username")))
                return ResponseEntity.status(HttpStatus.OK).body(teacherDetailsRepo.findTeacherDetailsByDepartment(Username.get("username")));
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found");
        }
        catch (Exception e)
        {
                headers.add("Message","false");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Error");
        }
    }
    @GetMapping("/username")
    public String showUsername()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        return username;
    }
    @PostMapping("/allot/class")
    public ResponseEntity<?> allotClass(@RequestBody ClassAllotment classAllotment)
    {
        try{
            return ResponseEntity.ok(teacherService.classAllot(classAllotment));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
//    @PostMapping("/show/feedback")
//    public ResponseEntity<?> showTeacherFeedback()
//    {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        String username=userDetails.getUsername();
//        return teacherService.showFeedback(username);
//    }
    @PostMapping("/add/marks")
    public ResponseEntity<?> addMarks(@RequestBody Marks marks)
    {
        try{
            return ResponseEntity.ok(teacherService.giveMarks(marks));
        }
        catch (Exception e){
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }


}
