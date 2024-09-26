package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.PatientDTO;
import io.medsys.opteamer.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientDTO> getAll();
    Optional<PatientDTO> getPatientById(Long id);
    PatientDTO createPatient(PatientDTO patientDTO);
    boolean deletePatient(Long id);
    Optional<PatientDTO> updatePatient(Long id, PatientDTO patientDTO);
}
