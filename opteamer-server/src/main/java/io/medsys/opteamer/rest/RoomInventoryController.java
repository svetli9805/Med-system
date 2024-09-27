package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.RoomInventoryDTO;
import io.medsys.opteamer.services.RoomInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/room-inventories")
@RequiredArgsConstructor
public class RoomInventoryController {
    private final RoomInventoryService roomInventoryService;

    @GetMapping
    public ResponseEntity<List<RoomInventoryDTO>> getAllRoomInventories() {
        List<RoomInventoryDTO> roomInventoryDTOList = roomInventoryService.getAll();
        return ResponseEntity.status(OK).body(roomInventoryDTOList);
    }

    @GetMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> getRoomInventoryById(
            @PathVariable(name = "assetId") Long assetId,
            @PathVariable(name = "roomId") Long roomId) {
        Optional<RoomInventoryDTO> optionalRoomInventoryDTO = roomInventoryService.getRoomInventoryById(assetId, roomId);
        return optionalRoomInventoryDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<RoomInventoryDTO> createRoomInventory(@RequestBody RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryDTO createdRoomInventoryDto = roomInventoryService.save(roomInventoryDTO);
        return ResponseEntity.status(CREATED).body(createdRoomInventoryDto);
    }

    @PutMapping("/{assetId}/{roomId}")
    public ResponseEntity<RoomInventoryDTO> updateRoomInventory(
            @PathVariable(name = "assetId") Long assetId,
            @PathVariable(name = "roomId") Long roomId,
            @RequestBody RoomInventoryDTO roomInventoryDto
    ) {
        Optional<RoomInventoryDTO> optionalRoomInventoryDTO = roomInventoryService.updateRoomInventory(assetId, roomId, roomInventoryDto);
        return optionalRoomInventoryDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{assetId}/{roomId}")
    public ResponseEntity<Void> deleteRoomInventory(
            @PathVariable(name = "assetId") Long assetId,
            @PathVariable(name = "roomId") Long roomId
    ) {
        boolean deleted = roomInventoryService.deleteRoomInventory(assetId, roomId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
