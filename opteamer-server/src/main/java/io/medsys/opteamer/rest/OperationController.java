package io.medsys.opteamer.rest;


import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;

    @GetMapping
    public ResponseEntity<List<OperationDTO>> getAllOperations() {
        List<OperationDTO> operationDTOS = operationService.getAll();
        return ResponseEntity.status(OK).body(operationDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDTO> getOperationById(@PathVariable(name = "id") Long id) {
        Optional<OperationDTO> operationDTO = operationService.getOperationById(id);
        return operationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(@RequestBody OperationDTO operationDTO) {
        OperationDTO createdOperationDto = operationService.save(operationDTO);
        return ResponseEntity.status(CREATED).body(createdOperationDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationDTO> updateOperation(@PathVariable(name = "id") Long id, @RequestBody OperationDTO operationDTO) {
        Optional<OperationDTO> optionalOperationDTO = operationService.updateOperation(id, operationDTO);
        return optionalOperationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable(name = "id") Long id) {
        boolean deleted = operationService.deleteOperation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}

