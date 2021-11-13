package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {

}
