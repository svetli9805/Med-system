package io.medsys.opteamer.rest;


import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.services.OperationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/operation-reports")
@RequiredArgsConstructor
public class OperationReportController {
    private final OperationReportService operationReportService;

    @GetMapping
    public ResponseEntity<List<OperationReportDTO>> getAllOperationReports() {
        List<OperationReportDTO> operationReportDTOS = operationReportService.getAll();
        return ResponseEntity.status(OK).body(operationReportDTOS);
    }

    @GetMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> getOperationReportById(
            @PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId
    ) {
        Optional<OperationReportDTO> operationreportOptional = operationReportService.getOperationReportById(teamMemberId, operationId);
        return operationreportOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OperationReportDTO> createOperationReport(@RequestBody OperationReportDTO operationReportDTO) {
        OperationReportDTO createdOperationreportDto = operationReportService.save(operationReportDTO);
        return ResponseEntity.status(CREATED).body(createdOperationreportDto);
    }

    @PutMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<OperationReportDTO> updateOperationReport(
            @PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId,
            @RequestBody OperationReportDTO operationReportDTO
    ) {
        Optional<OperationReportDTO> optionalOperationReportDTO = operationReportService
                .updateOperationReport(teamMemberId, operationId, operationReportDTO);
        return optionalOperationReportDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{teamMemberId}/{operationId}")
    public ResponseEntity<Void> deleteOperationReport(
            @PathVariable(name = "teamMemberId") Long teamMemberId,
            @PathVariable(name = "operationId") Long operationId
    ) {
        boolean deleted = operationReportService.deleteOperationReport(teamMemberId, operationId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
