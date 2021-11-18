package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance,Integer> {
    List<Attendance> findAttendanceByUsername(String username);

}
