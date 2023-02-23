package com.cutalab.cutalab2.backend.repository;

import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;
import com.cutalab.cutalab2.backend.entity.LinkEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {

    List<LinkEntity> findAllByAreaLinkEntity(AreaLinkEntity areaLinkEntity, Sort sort);

}
