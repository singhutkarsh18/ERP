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
    public String addDetails(StudentDetails studentDetail)
    {
        try {
            studentDetailRepository.save(studentDetail);
            return "success";
        }
        catch(Exception e)
        {
            String error=e.toString();
            return error;
        }
    }
}
