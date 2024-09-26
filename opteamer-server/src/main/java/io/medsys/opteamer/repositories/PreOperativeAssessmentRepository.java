package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.PreOperativeAssessment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PreOperativeAssessmentRepository extends CrudRepository<PreOperativeAssessment, String> {
    Optional<PreOperativeAssessment> findByName(String name);
}
