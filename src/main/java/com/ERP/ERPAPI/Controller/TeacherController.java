package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.Teacher;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    TeacherService service;
//    @PostMapping("/create/Teacher")
//    public String createTeacher(@RequestBody Teacher teacher)
//    {
//       return this.service.create(teacher);
//    }
//    @PostMapping("/show/teachers")
//    public List<Teacher> showAllTeachers()
//    {
//        return this.service.showAll();
//    }
//    @PostMapping("/remove/teacher")
//    public String removeTeacher(@RequestBody Username username)
//    {
//        return this.service.remove(username);
//    }
//    @PostMapping("/view/Reports")
//    public List<Report> viewReport()
//    {
//        return this.service.showReports();
//    }
}
