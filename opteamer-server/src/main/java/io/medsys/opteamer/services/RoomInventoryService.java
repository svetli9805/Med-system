package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.RoomInventoryDTO;

import java.util.List;
import java.util.Optional;

public interface RoomInventoryService {
    List<RoomInventoryDTO> getAll();
    Optional<RoomInventoryDTO> getRoomInventoryById(Long assetId, Long roomId);
    RoomInventoryDTO save(RoomInventoryDTO roomInventoryDTO);
    boolean deleteRoomInventory(Long assetId, Long roomId);
    Optional<RoomInventoryDTO> updateRoomInventory(Long assetId, Long roomId, RoomInventoryDTO roomInventoryDTO);
}
