package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.Operation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OperationRepository extends CrudRepository<Operation, Long> {
    Optional<Operation> findById(Long id);
}
