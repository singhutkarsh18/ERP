package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDetailRepository extends JpaRepository<StudentDetails,Integer> {
    StudentDetails findByUsername(String username);
    List<StudentDetails> findByCls(String Cls);
    Boolean existsByUsername(String username);
}
