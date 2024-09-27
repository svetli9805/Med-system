package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.OperationRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OperationRoomRepository extends CrudRepository<OperationRoom, Long> {
    Optional<OperationRoom> findById(Long id);
}
