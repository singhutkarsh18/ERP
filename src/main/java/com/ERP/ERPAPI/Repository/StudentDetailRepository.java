package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailRepository extends JpaRepository<StudentDetails,Integer> {
    StudentDetails findByUsername(String username);
}
