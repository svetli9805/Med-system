package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;
import io.medsys.opteamer.model.PreOperativeAssessment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreOperativeAssessmentMapper {
    PreOperativeAssessmentMapper INSTANCE = Mappers.getMapper(PreOperativeAssessmentMapper.class);

    PreOperativeAssessmentDTO toPreOperativeAssessmentDTO(PreOperativeAssessment preOperativeAssessment);

    PreOperativeAssessment toPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO);
}
