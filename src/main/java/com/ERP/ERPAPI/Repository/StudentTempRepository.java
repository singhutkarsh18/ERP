package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Student;
import com.ERP.ERPAPI.Model.StudentTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTempRepository extends JpaRepository<StudentTemp,Integer> {
    Boolean existsStudentByUsername(String username);
    StudentTemp findByUsername(String username);
    void deleteByUsername(String username);


}
