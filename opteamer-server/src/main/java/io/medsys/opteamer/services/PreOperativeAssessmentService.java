package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;

import java.util.List;
import java.util.Optional;

public interface PreOperativeAssessmentService {
    List<PreOperativeAssessmentDTO> getAll();
    Optional<PreOperativeAssessmentDTO> getPreOperativeAssessmentByName(String name);
    PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO);
    boolean deletePreOperativeAssessment(String name);
    Optional<PreOperativeAssessmentDTO> updatePreOperativeAssessment(String name, PreOperativeAssessmentDTO preOperativeAssessmentDTO);
}
