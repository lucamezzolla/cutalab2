package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.*;
import com.cutalab.cutalab2.backend.entity.LaboratoryAreaEntity;
import com.cutalab.cutalab2.backend.entity.LaboratoryEntity;
import com.cutalab.cutalab2.backend.repository.LaboratoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<LaboratoryDTO> findAll() {
        List<LaboratoryDTO> list1 = new ArrayList<>();
        List<LaboratoryEntity> list2 = laboratoryRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        for(LaboratoryEntity laboratoryEntity : list2) {
            LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
            LaboratoryAreaDTO laboratoryAreaDTO = new LaboratoryAreaDTO();
            BeanUtils.copyProperties(laboratoryEntity, laboratoryDTO);
            BeanUtils.copyProperties(laboratoryEntity.getLaboratoryAreaEntity(), laboratoryAreaDTO);
            laboratoryDTO.setLaboratoryAreaDTO(laboratoryAreaDTO);
            list1.add(laboratoryDTO);
        }
        return list1;
    }

    public void create(LaboratoryDTO laboratoryDTO) {
        LaboratoryEntity laboratoryEntity = new LaboratoryEntity();
        LaboratoryAreaEntity laboratoryAreaEntity = new LaboratoryAreaEntity();
        BeanUtils.copyProperties(laboratoryDTO, laboratoryEntity);
        BeanUtils.copyProperties(laboratoryDTO.getLaboratoryAreaDTO(), laboratoryAreaEntity);
        laboratoryEntity.setLaboratoryAreaEntity(laboratoryAreaEntity);
        laboratoryRepository.saveAndFlush(laboratoryEntity);
    }


}
