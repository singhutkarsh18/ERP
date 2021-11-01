package com.ERP.ERPAPI.Repository;


import com.ERP.ERPAPI.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Boolean existsAdminByEmail(String email);

}
