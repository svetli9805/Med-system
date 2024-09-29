package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationTypeDTO;

import java.util.List;
import java.util.Optional;

public interface OperationTypeService {
    List<OperationTypeDTO> getAll();
    Optional<OperationTypeDTO> getOperationTypeByName(String name);
    OperationTypeDTO save(OperationTypeDTO operationTypeDTO);
    boolean deleteOperationType(String name);
    Optional<OperationTypeDTO> updateOperationType(String name, OperationTypeDTO operationTypeDTO);
}
