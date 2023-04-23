package com.cutalab.cutalab2.backend.service.admin.payments;

import com.cutalab.cutalab2.backend.dto.admin.payments.PPaymentDTO;
import com.cutalab.cutalab2.backend.entity.admin.payments.PPaymentEntity;
import com.cutalab.cutalab2.backend.repository.admin.payments.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private final ModelMapper modelMapper;

    public PaymentService() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(PPaymentEntity.class, PPaymentDTO.class)
                .addMappings(mapper -> mapper.map(PPaymentEntity::getRegistry, PPaymentDTO::setRegistry))
                .addMappings(mapper -> mapper.map(PPaymentEntity::getService, PPaymentDTO::setService))
                .addMappings(mapper -> mapper.map(PPaymentEntity::getCurrency, PPaymentDTO::setCurrency));
    }

    public List<PPaymentDTO> getAllProgressiPayments() {
        List<PPaymentEntity> payments = paymentRepository.findAllProgressiPayments();
        return payments.stream().map(this::convertPaymentEntityToDTO).collect(Collectors.toList());
    }

    public void insert(PPaymentDTO paymentDTO) {
        paymentRepository.saveAndFlush(convertPaymentDTOToEntity(paymentDTO));
    }

    private PPaymentDTO convertPaymentEntityToDTO(PPaymentEntity entity) {
        return modelMapper.map(entity, PPaymentDTO.class);
    }

    private PPaymentEntity convertPaymentDTOToEntity(PPaymentDTO dto) {
        return modelMapper.map(dto, PPaymentEntity.class);
    }

}