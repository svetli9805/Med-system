package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.PatientDTO;
import io.medsys.opteamer.model.Patient;
import io.medsys.opteamer.repositories.PatientRepository;
import io.medsys.opteamer.services.PatientService;
import io.medsys.opteamer.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public List<PatientDTO> getAll() {
        List<PatientDTO> list = new ArrayList<>();
        Iterable<Patient> all = patientRepository.findAll();
        all.forEach(patient -> list.add(mapEntityToDTO(patient)));
        return list;
    }

    @Override
    public Optional<PatientDTO> getPatientById(Long id) {
        try {
            Patient patient = patientRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(patient));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = ModelMapperUtils.mapDTOToEntity(patientDTO);
        patientRepository.save(patient);
        return mapEntityToDTO(patient);
    }

    @Override
    public boolean deletePatient(Long id) {
        return patientRepository.findById(id).map(
                patient -> {
                    patientRepository.delete(patient);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<PatientDTO> updatePatient(Long id, PatientDTO patientDTO) {
        return patientRepository.findById(id).map(
                patient -> {
                    patient.setName(patient.getName());
                    patient.setNin(patientDTO.getNin());
                    patientRepository.save(patient);
                    return mapEntityToDTO(patient);
                });
    }

    private PatientDTO mapEntityToDTO(Patient patient) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patient, PatientDTO.class);
    }
}
