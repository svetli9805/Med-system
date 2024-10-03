package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.model.OperationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationTypeMapper {
    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    OperationTypeDTO toOperationTypeDTO(OperationType operationType);

    OperationType toOperationType(OperationTypeDTO operationTypeDTO);
}
