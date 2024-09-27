package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationRoomDTO;

import java.util.List;
import java.util.Optional;

public interface OperationRoomService {
    List<OperationRoomDTO> getAllOperationRooms();
    Optional<OperationRoomDTO> getOperationRoomById(Long id);
    OperationRoomDTO createOperationRoom(OperationRoomDTO operationRoomDTO);
    boolean deleteOperationRoom(Long id);
    Optional<OperationRoomDTO> updateOperationRoom(Long id, OperationRoomDTO operationRoomDTO);
}
