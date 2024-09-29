package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.OperationType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OperationTypeRepository extends CrudRepository<OperationType, String> {
    Optional<OperationType> findByName(String name);
}
