package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.model.Operation;
import io.medsys.opteamer.model.OperationReport;
import io.medsys.opteamer.model.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {Operation.class, TeamMember.class})
public interface OperationReportMapper {
    OperationReportMapper INSTANCE = Mappers.getMapper(OperationReportMapper.class);

    OperationReportDTO toOperationReportDTO(OperationReport operationReport);

    OperationReport toOperationReport(OperationReportDTO operationReportDTO);
}
