package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.LaboratoryAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryAreaRepositiory extends JpaRepository<LaboratoryAreaEntity, Integer> {

}