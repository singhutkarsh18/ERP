package com.ERP.ERPAPI.Repository;


import com.ERP.ERPAPI.Model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<ImageModel,Integer> {
    boolean existsByUsername(String username);
    ImageModel findImageModelByUsername(String username);

}
