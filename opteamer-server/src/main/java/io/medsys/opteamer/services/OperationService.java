package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationDTO;

import java.util.List;
import java.util.Optional;

public interface OperationService {
    List<OperationDTO> getAll();

    Optional<OperationDTO> getOperationById(Long id);

    OperationDTO save(OperationDTO operationDTO);

    boolean deleteOperation(Long id);

    Optional<OperationDTO> updateOperation(Long id, OperationDTO operationDTO);
}
