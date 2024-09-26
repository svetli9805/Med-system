package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.OperationProviderDTO;
import io.medsys.opteamer.services.OperationProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/operation-providers")
@RequiredArgsConstructor
public class OperationProviderController {
    private final OperationProviderService operationProviderService;

    @GetMapping
    public ResponseEntity<List<OperationProviderDTO>> getAllOperationProviders() {
        List<OperationProviderDTO> operationProviders = operationProviderService.getAll();
        return ResponseEntity.status(OK).body(operationProviders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> getOperationProviderById(@PathVariable(name = "id") String id) {
        Optional<OperationProviderDTO> operationProviderOptional = operationProviderService.getOperationProviderById(id);
        return operationProviderOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OperationProviderDTO> createOperationProvider(@RequestBody OperationProviderDTO operationProviderDTO) {
        OperationProviderDTO createdOperationProvider = operationProviderService.createOperationProvider(operationProviderDTO);
        return ResponseEntity.status(CREATED).body(createdOperationProvider);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationProviderDTO> updateOperationProvider(@PathVariable(name = "id") String id, @RequestBody OperationProviderDTO operationProviderDTO) {
        Optional<OperationProviderDTO> optionalOperationProviderDTO = operationProviderService.updateOperationProvider(id, operationProviderDTO);
        return optionalOperationProviderDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperationProvider(@PathVariable(name = "id") String id) {
        boolean deleted = operationProviderService.deleteOperationProvider(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
