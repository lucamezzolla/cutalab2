package com.cutalab.cutalab2.backend.repository.admin.aba;

import com.cutalab.cutalab2.backend.entity.admin.aba.ABAPackageEntity;
import com.cutalab.cutalab2.backend.entity.admin.aba.ABASessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ABASessionRepository extends JpaRepository<ABASessionEntity, Integer> {

    List<ABASessionEntity> findAllByABAPackageOrderByDayDesc(ABAPackageEntity packageEntity);

    @Query(value = "select sum(hours) as total from aba_sessions a where is_open = 0 and package_id = :packageEntity", nativeQuery = true)
    Integer getWorkedHoursTotal(ABAPackageEntity packageEntity);

    @Query(value = "select is_open from aba_sessions a where package_id = :packageEntity", nativeQuery = true)
    List<Boolean> allSessionsClosed(ABAPackageEntity packageEntity);

}