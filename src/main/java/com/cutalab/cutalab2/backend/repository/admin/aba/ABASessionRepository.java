package com.cutalab.cutalab2.backend.repository.admin.aba;

import com.cutalab.cutalab2.backend.entity.admin.aba.ABAPackageEntity;
import com.cutalab.cutalab2.backend.entity.admin.aba.ABASessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ABASessionRepository extends JpaRepository<ABASessionEntity, Integer> {

    List<ABASessionEntity> findAllByABAPackageOrderByDayDesc(ABAPackageEntity packageEntity);

}