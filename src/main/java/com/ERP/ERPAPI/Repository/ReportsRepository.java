package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReportsRepository extends JpaRepository<Report,Integer> {
}
