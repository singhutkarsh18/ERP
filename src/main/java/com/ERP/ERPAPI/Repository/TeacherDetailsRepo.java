package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherDetailsRepo extends JpaRepository<TeacherDetails, Integer> {
    TeacherDetails findTeacherDetailsByUsername(String username);
    List<TeacherDetails> findTeacherDetailsByDepartment(String department);
    Boolean existsByUsername(String username);
}
