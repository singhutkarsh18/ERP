package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Announcement;
import com.ERP.ERPAPI.Model.PasswordDTO;
import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentDashboard {

    @Autowired
    StudentService studentService;

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
}
