package io.medsys.opteamer.rest;


import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.services.OperationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/operation-types")
@RequiredArgsConstructor
public class OperationTypeController {
    private final OperationTypeService operationTypeService;

    @GetMapping
    public ResponseEntity<List<OperationTypeDTO>> getAllOperationTypes() {
        List<OperationTypeDTO> operationTypeDTOS = operationTypeService.getAll();
        return ResponseEntity.status(OK).body(operationTypeDTOS);
    }

    @GetMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> getOperationTypeByName(@PathVariable(name = "name") String name) {
        Optional<OperationTypeDTO> operationTypeDTO = operationTypeService.getOperationTypeByName(name);
        return operationTypeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OperationTypeDTO> createOperationType(@RequestBody OperationTypeDTO operationTypeDTO) {
        OperationTypeDTO createdOperationType = operationTypeService.save(operationTypeDTO);
        return ResponseEntity.status(CREATED).body(createdOperationType);
    }

    @PutMapping("/{name}")
    public ResponseEntity<OperationTypeDTO> updateOperationType(@PathVariable(name = "name") String name, @RequestBody OperationTypeDTO operationTypeDTO) {
        Optional<OperationTypeDTO> optionalOperationTypeDTO = operationTypeService.updateOperationType(name, operationTypeDTO);
        return optionalOperationTypeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteOperationType(@PathVariable(name = "name") String name) {
        boolean deleted = operationTypeService.deleteOperationType(name);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}

