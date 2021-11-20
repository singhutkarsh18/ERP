package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.TeacherTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherTempRepo extends JpaRepository<TeacherTemp,Integer> {
    TeacherTemp findByUsername(String username);
    boolean existsTeacherTempByUsername(String username);
}
