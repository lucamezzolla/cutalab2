package com.cutalab.cutalab2.backend.service;


import com.cutalab.cutalab2.backend.dto.dashboards.disks.StatusDTO;
import com.cutalab.cutalab2.backend.entity.dashboards.disks.StatusEntity;
import com.cutalab.cutalab2.backend.repository.StatusRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<StatusDTO> findAll() {
        List<StatusEntity> list1 = statusRepository.findAll();
        List<StatusDTO> list2 = new ArrayList<>();
        for(StatusEntity s : list1) {
            StatusDTO statusDTO = new StatusDTO();
            BeanUtils.copyProperties(s, statusDTO);
            list2.add(statusDTO);
        }
        return list2;
    }

}
