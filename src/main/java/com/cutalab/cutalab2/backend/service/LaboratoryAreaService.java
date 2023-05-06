package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.LaboratoryAreaDTO;
import com.cutalab.cutalab2.backend.entity.LaboratoryAreaEntity;
import com.cutalab.cutalab2.backend.repository.LaboratoryAreaRepositiory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LaboratoryAreaService {

    @Autowired
    private LaboratoryAreaRepositiory laboratoryAreaRepositiory;

    public List<LaboratoryAreaDTO> findAll() {
        List<LaboratoryAreaDTO> list1 = new ArrayList<>();
        List<LaboratoryAreaEntity> list2 = laboratoryAreaRepositiory.findAll(Sort.by(Sort.Direction.ASC, "name"));
        for(LaboratoryAreaEntity a : list2) {
            LaboratoryAreaDTO laboratoryAreaDTO = new LaboratoryAreaDTO();
            BeanUtils.copyProperties(a, laboratoryAreaDTO);
            list1.add(laboratoryAreaDTO);
        }
        return list1;
    }

    public LaboratoryAreaEntity getEntityById(Integer id) {
        return laboratoryAreaRepositiory.getReferenceById(id);
    }

    public LaboratoryAreaDTO getDTOById(Integer id) {
        LaboratoryAreaEntity laboratoryAreaEntity = laboratoryAreaRepositiory.getReferenceById(id);
        LaboratoryAreaDTO laboratoryAreaDTO = new LaboratoryAreaDTO();
        BeanUtils.copyProperties(laboratoryAreaEntity, laboratoryAreaDTO);
        return laboratoryAreaDTO;
    }

    public LaboratoryAreaDTO getDTOByEntity(LaboratoryAreaEntity laboratoryAreaEntity) {
        LaboratoryAreaDTO laboratoryAreaDTO = new LaboratoryAreaDTO();
        BeanUtils.copyProperties(laboratoryAreaEntity, laboratoryAreaDTO);
        return laboratoryAreaDTO;
    }

    public void create(LaboratoryAreaEntity laboratoryAreaEntity) {
        laboratoryAreaRepositiory.saveAndFlush(laboratoryAreaEntity);
    }

    public void update(LaboratoryAreaDTO laboratoryAreaDTO) {
        LaboratoryAreaEntity laboratoryAreaEntity = new LaboratoryAreaEntity();
        BeanUtils.copyProperties(laboratoryAreaDTO, laboratoryAreaEntity);
        laboratoryAreaRepositiory.saveAndFlush(laboratoryAreaEntity);
    }

    public void remove(LaboratoryAreaDTO laboratoryAreaDTO) {
        laboratoryAreaRepositiory.deleteById(laboratoryAreaDTO.getId());
    }


}
