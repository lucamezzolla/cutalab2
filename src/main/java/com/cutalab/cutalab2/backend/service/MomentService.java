package com.cutalab.cutalab2.backend.service;

import com.cutalab.cutalab2.backend.dto.MomentDTO;
import com.cutalab.cutalab2.backend.entity.MomentEntity;
import com.cutalab.cutalab2.backend.repository.MomentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService {

    @Autowired
    private MomentRepository momentRepository;

    public List<MomentDTO> findAll() {
        List<MomentDTO> list1 = new ArrayList<>();
        List<MomentEntity> list2 = momentRepository.findAll(Sort.by(Sort.Direction.DESC, "position"));
        for(MomentEntity m : list2) {
            MomentDTO momentDTO = new MomentDTO();
            BeanUtils.copyProperties(m, momentDTO);
            list1.add(momentDTO);
        }
        return list1;
    }

}
