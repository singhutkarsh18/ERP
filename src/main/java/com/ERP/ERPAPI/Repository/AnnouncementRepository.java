package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
        void deleteByDate(String date);
        Boolean existsByDate(String date);
}
