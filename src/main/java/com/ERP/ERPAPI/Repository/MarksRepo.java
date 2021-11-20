package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepo extends JpaRepository<Marks,Integer> {
    List<Marks> findAllByUsername(String username);
    Boolean existsBySubjectAndUsername(String subject,String username);
    Marks findBySubjectAndUsername(String subject,String username);
}
