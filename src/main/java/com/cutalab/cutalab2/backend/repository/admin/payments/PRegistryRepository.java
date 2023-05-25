package com.cutalab.cutalab2.backend.repository.admin.payments;

import com.cutalab.cutalab2.backend.entity.admin.payments.PRegistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PRegistryRepository extends JpaRepository<PRegistryEntity, Integer> {

}