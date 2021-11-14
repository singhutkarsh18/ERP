package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.StudentAcademics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAcademicRepo extends JpaRepository<StudentAcademics,Integer> {
    StudentAcademics findStudentAcademicsByUsername(String username);

    Boolean existsStudentAcademicsByUsername(String username);
}
