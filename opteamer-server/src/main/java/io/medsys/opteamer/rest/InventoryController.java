package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.InventoryDTO;
import io.medsys.opteamer.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventories = inventoryService.getAll();
        return ResponseEntity.status(OK).body(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryByAssetId(@PathVariable(name = "id") Long id) {
        Optional<InventoryDTO> inventoryDTOOptional = inventoryService.getInventoryById(id);
        return inventoryDTOOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        InventoryDTO createdInventoryDto = inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.status(CREATED).body(createdInventoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable(name = "id") Long id, @RequestBody InventoryDTO inventoryDTO) {
        Optional<InventoryDTO> optionalInventoryDTO = inventoryService.updateInventory(id, inventoryDTO);
        return optionalInventoryDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable(name = "id") Long id) {
        boolean deleted = inventoryService.deleteInventory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
