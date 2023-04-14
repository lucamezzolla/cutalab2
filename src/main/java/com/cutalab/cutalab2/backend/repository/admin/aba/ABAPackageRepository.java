package com.cutalab.cutalab2.backend.repository.admin.aba;

import com.cutalab.cutalab2.backend.entity.admin.aba.ABAPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ABAPackageRepository extends JpaRepository<ABAPackageEntity, Integer> {

}