package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    @Mapping(source = "operationType", target = "operationType")
    @Mapping(source = "operationRoom", target = "operationRoom")
    @Mapping(source = "patient", target = "patient")
    @Mapping(source = "teamMembers", target = "teamMembers")
    OperationDTO toOperationDTO(Operation operation);

    Operation toOperation(OperationDTO operationDTO);
}
