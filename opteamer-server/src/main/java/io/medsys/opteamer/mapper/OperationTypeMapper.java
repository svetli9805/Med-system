package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.model.OperationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OperationTypeMapper {
    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    @Mapping(source = "assets", target = "assetDTOS")
    @Mapping(source = "operationProviders", target = "operationProviderDTOS")
    @Mapping(source = "preOperativeAssessments", target = "preOperativeAssessmentDTOS")
    OperationTypeDTO toOperationTypeDTO(OperationType operationType);

    OperationType toOperationType(OperationTypeDTO operationTypeDTO);
}
