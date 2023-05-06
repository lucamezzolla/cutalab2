package com.cutalab.cutalab2.backend.service.admin.aba;

import org.modelmapper.ModelMapper;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.entity.admin.aba.ABAPackageEntity;
import com.cutalab.cutalab2.backend.repository.admin.aba.ABAPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ABAPackageService {

    @Autowired
    private ABAPackageRepository abaPackageRepository;

    private final ModelMapper modelMapper;

    public ABAPackageService() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ABAPackageEntity.class, ABAPackageDTO.class)
            .addMappings(mapper -> mapper.map(ABAPackageEntity::getPayment, ABAPackageDTO::setPayment));
    }

    public List<ABAPackageDTO> getAll() {
        List<ABAPackageEntity> entities = abaPackageRepository.findAll(Sort.by(Sort.Direction.DESC, "payment_id"));
        return entities.stream().map(this::convertABAPackageEntityToDTO).collect(Collectors.toList());
    }

    public ABAPackageDTO getById(Integer id) {
        return convertABAPackageEntityToDTO(abaPackageRepository.getReferenceById(id));
    }

    public void insert(ABAPackageDTO abaPackageDTO) {
        ABAPackageEntity abaPackageEntity = convertABAPackageDTOToEntity(abaPackageDTO);
        abaPackageRepository.saveAndFlush(abaPackageEntity);
    }

    public void update(ABAPackageDTO abaPackageDTO) {
        ABAPackageEntity abaPackageEntity = convertABAPackageDTOToEntity(abaPackageDTO);
        abaPackageRepository.saveAndFlush(abaPackageEntity);
    }

    public void remove(ABAPackageDTO abaPackageDTO) {
        ABAPackageEntity abaPackageEntity = convertABAPackageDTOToEntity(abaPackageDTO);
        abaPackageRepository.delete(abaPackageEntity);
    }

    private ABAPackageDTO convertABAPackageEntityToDTO(ABAPackageEntity entity) {
        return modelMapper.map(entity, ABAPackageDTO.class);
    }

    private ABAPackageEntity convertABAPackageDTOToEntity(ABAPackageDTO dto) {
        return modelMapper.map(dto, ABAPackageEntity.class);
    }

}