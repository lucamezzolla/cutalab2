package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.AreaLinkDTO;
import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;
import com.cutalab.cutalab2.backend.repository.AreaLinkRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaLinkService {

    @Autowired
    private AreaLinkRepository areaLinkRepository;

    public List<AreaLinkDTO> findAll() {
        List<AreaLinkDTO> list1 = new ArrayList<>();
        List<AreaLinkEntity> list2 = areaLinkRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        for(AreaLinkEntity a : list2) {
            AreaLinkDTO areaLinkDTO = new AreaLinkDTO();
            BeanUtils.copyProperties(a, areaLinkDTO);
            list1.add(areaLinkDTO);
        }
        return list1;
    }

    public AreaLinkEntity getEntityById(Integer id) {
        return areaLinkRepository.getReferenceById(id);
    }

    public AreaLinkDTO getDTOById(Integer id) {
        AreaLinkEntity areaLinkEntity = areaLinkRepository.getReferenceById(id);
        AreaLinkDTO areaLinkDTO = new AreaLinkDTO();
        BeanUtils.copyProperties(areaLinkEntity, areaLinkDTO);
        return areaLinkDTO;
    }

    public AreaLinkDTO getDTOByEntity(AreaLinkEntity areaLinkEntity) {
        AreaLinkDTO areaLinkDTO = new AreaLinkDTO();
        BeanUtils.copyProperties(areaLinkEntity, areaLinkDTO);
        return areaLinkDTO;
    }

    public AreaLinkEntity create(AreaLinkEntity areaLinkEntity) {
        return areaLinkRepository.saveAndFlush(areaLinkEntity);
    }

    public AreaLinkEntity update(AreaLinkDTO areaLinkDTO) {
        AreaLinkEntity areaLinkEntity = new AreaLinkEntity();
        BeanUtils.copyProperties(areaLinkDTO, areaLinkEntity);
        return areaLinkRepository.saveAndFlush(areaLinkEntity);
    }

    public void remove(AreaLinkDTO areaLinkDTO) {
        areaLinkRepository.deleteById(areaLinkDTO.getId());
    }

}