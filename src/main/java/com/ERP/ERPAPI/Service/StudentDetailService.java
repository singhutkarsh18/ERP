package com.ERP.ERPAPI.Service;

import com.ERP.ERPAPI.Model.StudentDetails;
import com.ERP.ERPAPI.Repository.StudentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StudentDetailService
{
    @Autowired
    private StudentDetailRepository studentDetailRepository;
//    @Autowired
//    private StudentAcademicRepo studentAcademicRepo;
    public String addDetails(StudentDetails studentDetail)
    {
        try {
            studentDetailRepository.save(studentDetail);
            return "success";
        }
        catch(Exception e)
        {
            return e.toString();
        }
    }
//    public String addAcademics(StudentAcademics studentAcademics)
//    {
//        try{
//            studentAcademicRepo.save(studentAcademics);
//            return "success";
//        }
//        catch(Exception e)
//        {
//            return e.toString();
//        }
//    }
    public StudentDetails showDetails(String username)
    {
        return studentDetailRepository.findByUsername(username);
    }
//    public StudentAcademics showAcademics(String username)
//    {
//        return studentAcademicRepo.findStudentAcademicsByUsername(username);
//
//    }

}
