package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.ClassAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassAllotmentRepo extends JpaRepository<ClassAllotment,Integer> {
    ClassAllotment findByUsername(String username);
}
