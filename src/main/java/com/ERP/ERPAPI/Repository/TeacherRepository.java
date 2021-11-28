package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer>
{
    Boolean existsTeacherByUsername(String username);
    Teacher findByUsername(String username);
    void deleteByUsername(String username);
    List<Teacher> findTeacherByDepartment(String department);
    Boolean existsTeacherByDepartment(String department);
    void deleteById(Integer id);
    boolean existsById(Integer id);
}
