package com.ERP.ERPAPI.Controller;

import com.ERP.ERPAPI.Model.Attendance;
import com.ERP.ERPAPI.Model.Marks;
import com.ERP.ERPAPI.Model.Report;
import com.ERP.ERPAPI.Model.StudentDetails;
import com.ERP.ERPAPI.Repository.StudentDetailRepository;
import com.ERP.ERPAPI.Service.StudentDetailService;
import com.ERP.ERPAPI.Service.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
public class StudentDashboard {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentDetailService studentDetailService;
    @Autowired
    StudentDetailRepository studentDetailRepository;

    @PostMapping("/update/Password/Student")
    public ResponseEntity<?> updateStudentPassword(@RequestBody Map<String ,String> password)
    {
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.ok(studentService.changePassword(username,password.get("password")));

        }
        catch (Exception e)
        {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/report/student")
    public ResponseEntity<String> report(@RequestBody Report report)
    {
        try {
            return ResponseEntity.ok(studentService.reportProblem(report));
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @GetMapping("/show/announcement")
    public ResponseEntity<?> showAnnouncements()
    {
        try {
            return ResponseEntity.ok(studentService.announcementList());
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PostMapping("/details/personal/student")
    public ResponseEntity<?> personalDetails(@RequestBody StudentDetails studentDetails)
    {
        try {
            //Student number cannot be left empty!!
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                    .getPrincipal();
//            String username = userDetails.getUsername();

            studentService.addStudentNo(studentDetails.getUsername(), studentDetails.getStudentNo());
            return ResponseEntity.ok(studentDetailService.addDetails(studentDetails));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add details");
        }
    }
//    @PostMapping("/details/academic/student")
//    public ResponseEntity<?> academicDetails(@RequestBody StudentAcademics studentAcademics)
//    {
//        try {
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                    .getPrincipal();
//            String username = userDetails.getUsername();
//            studentService.addStudentNo(username, studentAcademics.getStudentNo());
//            return ResponseEntity.ok(studentDetailService.addAcademics(studentAcademics));
//        }
//        catch(Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add details");
//        }
//
//    }
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
        }
    }
//    @GetMapping("/show/studentDetails/academic")
//    public ResponseEntity<?> showAcademicDetails()
//    {
//        try {
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                    .getPrincipal();
//            String username = userDetails.getUsername();
//
//            return ResponseEntity.status(HttpStatus.OK).body(studentDetailService.showAcademics(username));
//        }
//        catch(Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Not authorized");
//        }
//    }
    @GetMapping("/show/attendance")
    public ResponseEntity<?> showAttendance()
    {
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.status(HttpStatus.OK).body(studentService.getAttendance(username));
        }
        catch(Exception e)
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            List<Attendance> attendances =studentService.getAttendance(username);
            if (attendances==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Attendance found");
            else
                return ResponseEntity.status(HttpStatus.OK).body("Error");
        }
    }
    @GetMapping("/show/attendance/total")
    public ResponseEntity<?> showAttendanceTotal()
    {
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            List<Attendance> attendances =studentService.getAttendance(username);
            Integer present=0;
            Integer total=0;
            Iterator itr= attendances.iterator();
            while(itr.hasNext())
            {
                Attendance attendance=(Attendance) itr.next();
                if(attendance.getPresent())
                    present++;
                total++;
            }
            Float presentPercent=(float)(present*100)/total;
            Integer presentPercent1=Math.round(presentPercent);
            Map<String,String> AttendanceTotal =new HashMap<>();
            AttendanceTotal.put("Present",present.toString());
            AttendanceTotal.put("Percent",presentPercent1.toString()+"%");
            AttendanceTotal.put("Total",total.toString());

            return ResponseEntity.status(HttpStatus.OK).body(AttendanceTotal);

        }
        catch(Exception e)
        {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            List<Attendance> attendances =studentService.getAttendance(username);
            if (attendances==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Attendance found");
            else
                return ResponseEntity.status(HttpStatus.OK).body("Error");
        }
    }
//    @PostMapping("/add/feedback")
//    public ResponseEntity<?> addFeedback(@RequestBody Feedback feedback){
//        try{
//            return ResponseEntity.ok(studentService.addFeedback(feedback));
//        }
//        catch(Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.OK).body("Error");
//        }
//    }
    @GetMapping("/show/result")
    public ResponseEntity<?> getResult(){
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            if(studentService.showMarks(username)==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No marks found");
            else
                return ResponseEntity.status(HttpStatus.OK).body(studentService.showMarks(username));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
    @GetMapping("/show/result/total")
    public ResponseEntity<?> getResultTotal()
    {
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            if(studentService.showMarks(username)==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No marks found");
            else {
                List<Marks> marks =studentService.showMarks(username);
                Iterator itr = marks.iterator();
                Float total_marks=0.0f;
                while(itr.hasNext())
                {
                    Marks marks1=(Marks) itr.next();
                    total_marks+=marks1.getMarks();
                }
                Float percentage=total_marks/6.0f;
                Map<String,String> total=new HashMap<String,String>();
                String pass;
                if(percentage>33.0f)
                    pass="Pass";
                else
                    pass ="Fail";

                total.put("TotalMarks",total_marks.toString());
                total.put("Percentage",percentage.toString()+"%");
                total.put("Result",pass);

                return ResponseEntity.status(HttpStatus.OK).body(total);
            }
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @GetMapping("/check/details")
    public ResponseEntity<?> checkDetails()
    {

        try {
            System.out.println("Trigger");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            if(studentDetailRepository.existsByUsername(username))
            {
                return ResponseEntity.status(HttpStatus.OK).body("User details present");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body("User details not present");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}
