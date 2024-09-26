package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.InventoryDTO;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<InventoryDTO> getAll();
    Optional<InventoryDTO> getInventoryById(Long id);
    InventoryDTO createInventory(InventoryDTO inventoryDTO);
    boolean deleteInventory(Long id);
    Optional<InventoryDTO> updateInventory(Long id, InventoryDTO inventoryDTO);
}
