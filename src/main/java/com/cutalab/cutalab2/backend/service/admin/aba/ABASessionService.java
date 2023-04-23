package com.cutalab.cutalab2.backend.service.admin.aba;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.dto.admin.aba.ABASessionDTO;
import com.cutalab.cutalab2.backend.entity.admin.aba.ABAPackageEntity;
import com.cutalab.cutalab2.backend.entity.admin.aba.ABASessionEntity;
import com.cutalab.cutalab2.backend.repository.admin.aba.ABASessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ABASessionService {

    @Autowired
    private ABASessionRepository abaSessionRepository;

    private final ModelMapper modelMapper;

    public ABASessionService() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ABASessionEntity.class, ABASessionDTO.class)
            .addMappings(mapper -> mapper.map(src -> src.getABAPackage(), ABASessionDTO::setABAPackage));
    }

    public List<ABASessionDTO> getAll(ABAPackageDTO packageDTO) {
        List<ABASessionEntity> sessions = abaSessionRepository.findAllByABAPackageOrderByDayDesc(convertABAPackageDTOToEntity(packageDTO));
        return sessions.stream().map(this::convertABASessionEntityToDTO).collect(Collectors.toList());
    }

    public ABASessionDTO getById(Integer id) {
        return convertABASessionEntityToDTO(abaSessionRepository.getReferenceById(id));
    }

    public void insert(ABASessionDTO abaSessionDTO) {
        abaSessionRepository.saveAndFlush(convertABASessionDTOToEntity(abaSessionDTO));
    }

    public void update(ABASessionDTO abaSessionDTO) {
        abaSessionRepository.saveAndFlush(convertABASessionDTOToEntity(abaSessionDTO));
    }

    public void remove(ABASessionDTO abaSessionDTO) {
        abaSessionRepository.delete(convertABASessionDTOToEntity(abaSessionDTO));
    }

    private ABASessionDTO convertABASessionEntityToDTO(ABASessionEntity entity) {
        return modelMapper.map(entity, ABASessionDTO.class);
    }

    private ABASessionEntity convertABASessionDTOToEntity(ABASessionDTO dto) {
        return modelMapper.map(dto, ABASessionEntity.class);
    }

    private ABAPackageEntity convertABAPackageDTOToEntity(ABAPackageDTO dto) {
        return modelMapper.map(dto, ABAPackageEntity.class);
    }

}