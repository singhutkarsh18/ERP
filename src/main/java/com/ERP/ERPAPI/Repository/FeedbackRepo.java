package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {
    boolean existsByUsername(String username);
}
