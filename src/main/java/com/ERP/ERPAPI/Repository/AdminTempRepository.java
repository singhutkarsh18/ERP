package com.ERP.ERPAPI.Repository;


import com.ERP.ERPAPI.Model.AdminTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTempRepository extends JpaRepository<AdminTemp,Integer> {
    Boolean existsAdminByUsername(String username);
    AdminTemp findByUsername(String username);
    void deleteByUsername(String username);

}
