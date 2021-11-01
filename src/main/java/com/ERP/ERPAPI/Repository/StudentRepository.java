package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Admin;
import com.ERP.ERPAPI.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Boolean existsAdminByEmail(String email);
    Student findByEmail(String email);


}
