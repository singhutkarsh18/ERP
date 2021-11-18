package com.ERP.ERPAPI.Repository;

import com.ERP.ERPAPI.Model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepo extends JpaRepository<ImageModel,Long> {

    Optional<ImageModel> findByName(String name);
}
