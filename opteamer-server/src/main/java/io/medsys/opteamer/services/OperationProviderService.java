package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationProviderDTO;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.enums.OperationProviderType;
import io.medsys.opteamer.repositories.OperationProviderRepository;
import io.medsys.opteamer.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static io.medsys.opteamer.utils.ModelMapperUtils.mapEntityToDTO;

@Service
@RequiredArgsConstructor
public class OperationProviderService {
    private final OperationProviderRepository operationProviderRepository;

    public List<OperationProviderDTO> getAll() {
        List<OperationProviderDTO> list = new ArrayList<>();
        Iterable<OperationProvider> all = operationProviderRepository.findAll();
        all.forEach(asset -> list.add(mapEntityToDTO(asset)));
        return list;
    }

    public Optional<OperationProviderDTO> getOperationProviderById(String id) {
        try {
            OperationProvider operationProvider = operationProviderRepository.findByType(OperationProviderType.valueOf(id)).orElseThrow();
            return Optional.of(mapEntityToDTO(operationProvider));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO) {
        OperationProvider operationProvider = ModelMapperUtils.mapDTOToEntity(operationProviderDTO);
        operationProviderRepository.save(operationProvider);
        return mapEntityToDTO(operationProvider);
    }

    public boolean deleteOperationProvider(String id) {
        return operationProviderRepository.findByType(OperationProviderType.valueOf(id)).map(
                operationProvider -> {
                    operationProviderRepository.delete(operationProvider);
                    return true;
                }).orElse(false);
    }

    public Optional<OperationProviderDTO> updateOperationProvider(String id, OperationProviderDTO operationProviderDTO) {
        return operationProviderRepository.findByType(OperationProviderType.valueOf(id)).map(
                operationProvider -> {
                    operationProvider.setType(operationProvider.getType());
                    operationProviderRepository.save(operationProvider);
                    return mapEntityToDTO(operationProvider);
                });
    }
}
