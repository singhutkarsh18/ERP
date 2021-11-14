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
    public String personalDetails(@RequestBody StudentDetails studentDetails)
    {
        //Student number cannot be left empty!!
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        studentService.addStudentNo(username,studentDetails.getStudentNo());
        return studentDetailService.addDetails(studentDetails);
    }
    @PostMapping("/details/academic/student")
    public String academicDetails(@RequestBody StudentAcademics studentAcademics)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        studentService.addStudentNo(username,studentAcademics.getStudentNo());
        return studentDetailService.addAcademics(studentAcademics);

    }
    @GetMapping("/show/studentDetails/personal")
    public ResponseEntity<?> showStudentDetails()
    {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.ok(studentDetailService.showDetails(username));
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
            return ResponseEntity.ok( studentDetailService.showAcademics(username));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not authorized");
        }
    }


}
