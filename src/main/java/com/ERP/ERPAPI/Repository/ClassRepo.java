package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.ClassTeachers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<ClassTeachers,Integer> {
    ClassTeachers findByCls(String cls);
    boolean existsByCls(String cls);
}
