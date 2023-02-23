package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.MomentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentRepository extends JpaRepository<MomentEntity, Integer> {

}