package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.OperationProviderDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.enums.OperationProviderType;
import io.medsys.opteamer.repositories.OperationProviderRepository;
import io.medsys.opteamer.services.OperationProviderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationProviderServiceImpl implements OperationProviderService {
    private final OperationProviderRepository operationProviderRepository;

    @Override
    public List<OperationProviderDTO> getAll() {
        List<OperationProviderDTO> list = new ArrayList<>();
        Iterable<OperationProvider> all = operationProviderRepository.findAll();
        all.forEach(asset -> list.add(mapEntityToDTO(asset)));
        return list;
    }

    @Override
    public Optional<OperationProviderDTO> getOperationProviderById(String id) {
        try {
            OperationProvider operationProvider = operationProviderRepository.findByType(OperationProviderType.valueOf(id)).orElseThrow();
            return Optional.of(mapEntityToDTO(operationProvider));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO) {
        OperationProvider operationProvider = mapDTOToEntity(operationProviderDTO);
        operationProviderRepository.save(operationProvider);
        return mapEntityToDTO(operationProvider);
    }

    @Override
    public boolean deleteOperationProvider(String id) {
        return operationProviderRepository.findByType(OperationProviderType.valueOf(id)).map(
                operationProvider -> {
                    operationProviderRepository.delete(operationProvider);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<OperationProviderDTO> updateOperationProvider(String id, OperationProviderDTO operationProviderDTO) {
        return operationProviderRepository.findByType(OperationProviderType.valueOf(id)).map(
                operationProvider -> {
                    operationProvider.setType(operationProvider.getType());
                    operationProviderRepository.save(operationProvider);
                    return mapEntityToDTO(operationProvider);
                });
    }

    private OperationProviderDTO mapEntityToDTO(OperationProvider operationProvider) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProvider, OperationProviderDTO.class);
    }

    private OperationProvider mapDTOToEntity(OperationProviderDTO operationProviderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProviderDTO, OperationProvider.class);
    }
}
