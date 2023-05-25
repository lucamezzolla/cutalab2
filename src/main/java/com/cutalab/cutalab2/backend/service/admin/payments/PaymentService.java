package com.cutalab.cutalab2.backend.service.admin.payments;

import com.cutalab.cutalab2.backend.dto.admin.payments.PCurrencyDTO;
import com.cutalab.cutalab2.backend.dto.admin.payments.PPaymentDTO;
import com.cutalab.cutalab2.backend.dto.admin.payments.PRegistryDTO;
import com.cutalab.cutalab2.backend.dto.admin.payments.PServiceDTO;
import com.cutalab.cutalab2.backend.entity.admin.payments.PCurrencyEntity;
import com.cutalab.cutalab2.backend.entity.admin.payments.PPaymentEntity;
import com.cutalab.cutalab2.backend.entity.admin.payments.PRegistryEntity;
import com.cutalab.cutalab2.backend.entity.admin.payments.PServiceEntity;
import com.cutalab.cutalab2.backend.repository.admin.payments.PCurrencyRepository;
import com.cutalab.cutalab2.backend.repository.admin.payments.PRegistryRepository;
import com.cutalab.cutalab2.backend.repository.admin.payments.PServiceRepository;
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

    @Autowired
    private PRegistryRepository pRegistryRepository;

    @Autowired
    private PServiceRepository pServiceRepository;

    @Autowired
    private PCurrencyRepository pCurrencyRepository;

    private final ModelMapper paymentModelMapper;
    private final ModelMapper registryModelMapper;
    private final ModelMapper serviceModelMapper;
    private final ModelMapper currencyModelMapper;

    public PaymentService() {
        paymentModelMapper = new ModelMapper();
        registryModelMapper = new ModelMapper();
        serviceModelMapper = new ModelMapper();
        currencyModelMapper = new ModelMapper();
        paymentModelMapper.createTypeMap(PPaymentEntity.class, PPaymentDTO.class)
                .addMappings(mapper -> mapper.map(PPaymentEntity::getRegistry, PPaymentDTO::setRegistry))
                .addMappings(mapper -> mapper.map(PPaymentEntity::getService, PPaymentDTO::setService))
                .addMappings(mapper -> mapper.map(PPaymentEntity::getCurrency, PPaymentDTO::setCurrency));
        registryModelMapper.createTypeMap(PRegistryEntity.class, PRegistryDTO.class);
        serviceModelMapper.createTypeMap(PServiceEntity.class, PServiceDTO.class);
        currencyModelMapper.createTypeMap(PCurrencyEntity.class, PCurrencyDTO.class);
    }

    public List<PPaymentDTO> getAllProgressiPayments() {
        List<Integer> ids = paymentRepository.findAllProgressiPaymentsToExclude();
        List<PPaymentEntity> payments = paymentRepository.findAllProgressiPayments(ids);
        return payments.stream().map(this::convertPaymentEntityToDTO).collect(Collectors.toList());
    }

    public void insert(PPaymentDTO paymentDTO) {
        paymentRepository.saveAndFlush(convertPaymentDTOToEntity(paymentDTO));
    }

    private PPaymentDTO convertPaymentEntityToDTO(PPaymentEntity entity) {
        return paymentModelMapper.map(entity, PPaymentDTO.class);
    }

    private PPaymentEntity convertPaymentDTOToEntity(PPaymentDTO dto) {
        return paymentModelMapper.map(dto, PPaymentEntity.class);
    }

    //** ANAGRAFICHE

    private PRegistryDTO convertRegistryEntityToDTO(PRegistryEntity entity) {
        return registryModelMapper.map(entity, PRegistryDTO.class);
    }

    private PRegistryEntity convertRegistryDTOToEntity(PRegistryDTO dto) {
        return registryModelMapper.map(dto, PRegistryEntity.class);
    }
    public List<PRegistryDTO> getAllRegistries() {
        List<PRegistryEntity> registries = pRegistryRepository.findAll();
        return registries.stream().map(this::convertRegistryEntityToDTO).collect(Collectors.toList());
    }

    public void createRegistry(PRegistryDTO pRegistryDTO) {
        pRegistryRepository.saveAndFlush(convertRegistryDTOToEntity(pRegistryDTO));
    }

    public void updateRegistry(PRegistryDTO pRegistryDTO) {
        pRegistryRepository.saveAndFlush(convertRegistryDTOToEntity(pRegistryDTO));
    }

    public void removeRegistry(PRegistryDTO pRegistryDTO) {
        pRegistryRepository.delete(convertRegistryDTOToEntity(pRegistryDTO));
    }

    //** SERVIZI

    private PServiceDTO convertServiceEntityToDTO(PServiceEntity entity) {
        return serviceModelMapper.map(entity, PServiceDTO.class);
    }

    private PServiceEntity convertServiceDTOToEntity(PServiceDTO dto) {
        return serviceModelMapper.map(dto, PServiceEntity.class);
    }
    public List<PServiceDTO> getAllServices() {
        List<PServiceEntity> services = pServiceRepository.findAll();
        return services.stream().map(this::convertServiceEntityToDTO).collect(Collectors.toList());
    }

    public void createService(PServiceDTO pServiceDTO) {
        pServiceRepository.saveAndFlush(convertServiceDTOToEntity(pServiceDTO));
    }

    public void updateService(PServiceDTO pServiceDTO) {
        pServiceRepository.saveAndFlush(convertServiceDTOToEntity(pServiceDTO));
    }

    public void removeService(PServiceDTO pServiceDTO) {
        pServiceRepository.delete(convertServiceDTOToEntity(pServiceDTO));
    }

    //** VALUTE

    private PCurrencyDTO convertCurrencyEntityToDTO(PCurrencyEntity entity) {
        return currencyModelMapper.map(entity, PCurrencyDTO.class);
    }

    private PCurrencyEntity convertCurrencyDTOToEntity(PCurrencyDTO dto) {
        return currencyModelMapper.map(dto, PCurrencyEntity.class);
    }
    public List<PCurrencyDTO> getAllCurrencies() {
        List<PCurrencyEntity> services = pCurrencyRepository.findAll();
        return services.stream().map(this::convertCurrencyEntityToDTO).collect(Collectors.toList());
    }

    public void createCurrency(PCurrencyDTO pCurrencyDTO) {
        pCurrencyRepository.saveAndFlush(convertCurrencyDTOToEntity(pCurrencyDTO));
    }

    public void updateCurrency(PCurrencyDTO pCurrencyDTO) {
        pCurrencyRepository.saveAndFlush(convertCurrencyDTOToEntity(pCurrencyDTO));
    }

    public void removeCurrency(PCurrencyDTO pCurrencyDTO) {
        pCurrencyRepository.delete(convertCurrencyDTOToEntity(pCurrencyDTO));
    }


}