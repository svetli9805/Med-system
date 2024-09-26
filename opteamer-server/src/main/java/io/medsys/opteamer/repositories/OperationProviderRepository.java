package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.enums.OperationProviderType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OperationProviderRepository extends CrudRepository<OperationProvider, Long> {
    Optional<OperationProvider> findByType(OperationProviderType id);
}
