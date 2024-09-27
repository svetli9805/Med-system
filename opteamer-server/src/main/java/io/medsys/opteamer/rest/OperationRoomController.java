package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.services.OperationRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/operation-rooms")
@RequiredArgsConstructor
public class OperationRoomController {
    private final OperationRoomService operationRoomService;

    @GetMapping
    public ResponseEntity<List<OperationRoomDTO>> getAllOperationRooms() {
        List<OperationRoomDTO> operationRoomDTOS = operationRoomService.getAllOperationRooms();
        return ResponseEntity.status(OK).body(operationRoomDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> getOperationRoomById(@PathVariable(name = "id") Long id) {
        Optional<OperationRoomDTO> optionalOperationRoomDTO = operationRoomService.getOperationRoomById(id);
        return optionalOperationRoomDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OperationRoomDTO> createOperationRoom(@RequestBody OperationRoomDTO operationRoomDTO) {
        OperationRoomDTO createdOperationRoomDto = operationRoomService.createOperationRoom(operationRoomDTO);
        return ResponseEntity.status(CREATED).body(createdOperationRoomDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationRoomDTO> updateOperationRoom(@PathVariable(name = "id") Long id, @RequestBody OperationRoomDTO operationRoomDTO) {
        Optional<OperationRoomDTO> optionalOperationRoomDTO = operationRoomService.updateOperationRoom(id, operationRoomDTO);
        return optionalOperationRoomDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationRoom(@PathVariable(name = "id") Long id) {
        boolean deleted = operationRoomService.deleteOperationRoom(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
