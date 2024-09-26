package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationProviderDTO;

import java.util.List;
import java.util.Optional;

public interface OperationProviderService {
    List<OperationProviderDTO> getAll();
    Optional<OperationProviderDTO> getOperationProviderById(String id);
    OperationProviderDTO createOperationProvider(OperationProviderDTO operationProviderDTO);
    boolean deleteOperationProvider(String id);
    Optional<OperationProviderDTO> updateOperationProvider(String id, OperationProviderDTO operationProviderDTO);
}
