package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskStyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskStyleRepository extends JpaRepository<DiskStyleEntity, Integer> {

}