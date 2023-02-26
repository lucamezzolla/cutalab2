package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.dashboards.disks.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
}
