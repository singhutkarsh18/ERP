package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.*;
import com.ERP.ERPAPI.Service.StudentDetailService;
import com.ERP.ERPAPI.Service.StudentService;
import net.bytebuddy.asm.Advice;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class StudentDashboard {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentDetailService studentDetailService;

    @PostMapping("/update/Password/Student")
    public String updateStudentPassword(@RequestBody PasswordDTO passwordDTO)
    {
        return studentService.changePassword(passwordDTO.getUsername(),passwordDTO.getPassword());
    }
    @PostMapping("/report/student")
    public String report(@RequestBody Report report)
    {
        return studentService.reportProblem(report);
    }
    @GetMapping("/show/announcement")
    public List<Announcement> showAnnouncements()
    {
        return studentService.announcementList();
    }

    @PostMapping("/details/personal/student")
    public ResponseEntity<?> personalDetails(@RequestBody StudentDetails studentDetails)
    {
        try {
            //Student number cannot be left empty!!
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            studentService.addStudentNo(username, studentDetails.getStudentNo());
            return ResponseEntity.ok(studentDetailService.addDetails(studentDetails));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add details");
        }
    }
    @PostMapping("/details/academic/student")
    public ResponseEntity<?> academicDetails(@RequestBody StudentAcademics studentAcademics)
    {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            studentService.addStudentNo(username, studentAcademics.getStudentNo());
            return ResponseEntity.ok(studentDetailService.addAcademics(studentAcademics));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add details");
        }

    }
    @GetMapping("/show/studentDetails/personal")
    public ResponseEntity<?> showStudentDetails()
    {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.status(HttpStatus.OK).body(studentDetailService.showDetails(username));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not authorized");
        }
    }
    @GetMapping("/show/studentDetails/academic")
    public ResponseEntity<?> showAcademicDetails()
    {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();

            return ResponseEntity.status(HttpStatus.OK).body(studentDetailService.showAcademics(username));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not authorized");
        }
    }

}
