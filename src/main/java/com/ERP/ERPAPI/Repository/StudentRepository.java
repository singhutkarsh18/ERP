package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {


}
