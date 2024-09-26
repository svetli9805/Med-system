package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;
import io.medsys.opteamer.services.PreOperativeAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pre-operative-assessments")
@RequiredArgsConstructor
public class PreOperativeAssessmentController {
    private final PreOperativeAssessmentService preOperativeAssessmentService;

    @GetMapping
    public ResponseEntity<List<PreOperativeAssessmentDTO>> getAllPreOperativeAssessments() {
        List<PreOperativeAssessmentDTO> preOperativeAssessments = preOperativeAssessmentService.getAll();
        return ResponseEntity.status(OK).body(preOperativeAssessments);
    }

    @GetMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> getPreOperativeAssessmentByName(@PathVariable(name = "name") String name) {
        Optional<PreOperativeAssessmentDTO> preOperativeAssessmentDTO = preOperativeAssessmentService.getPreOperativeAssessmentByName(name);
        return preOperativeAssessmentDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PreOperativeAssessmentDTO> createPreOperativeAssessment(@RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessmentDTO createdPreOperativeAssessmentDto = preOperativeAssessmentService.createPreOperativeAssessment(preOperativeAssessmentDTO);
        return ResponseEntity.status(CREATED).body(createdPreOperativeAssessmentDto);
    }

    @PutMapping("/{name}")
    public ResponseEntity<PreOperativeAssessmentDTO> updatePreOperativeAssessment(@PathVariable(name = "name") String name, @RequestBody PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        Optional<PreOperativeAssessmentDTO> optionalPreOperativeAssessmentDTO = preOperativeAssessmentService.updatePreOperativeAssessment(name, preOperativeAssessmentDTO);
        return optionalPreOperativeAssessmentDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePreOperativeAssessment(@PathVariable(name = "name") String name) {
        boolean deleted = preOperativeAssessmentService.deletePreOperativeAssessment(name);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
