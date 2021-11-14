package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Announcement;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.StudentDetails;
import com.ERP.ERPAPI.Service.StudentDetailService;
import com.ERP.ERPAPI.Service.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/personal/details")
    public String personalDetails(@RequestBody StudentDetails studentDetails)
    {
        //Student number cannot be left empty!!
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        studentService.addStudentNo(username,studentDetails.getStudentNo());
        return studentDetailService.addDetails(studentDetails);
    }

}
