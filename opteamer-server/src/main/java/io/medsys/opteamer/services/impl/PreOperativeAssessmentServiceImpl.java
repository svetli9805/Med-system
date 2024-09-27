package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.PreOperativeAssessmentDTO;
import io.medsys.opteamer.model.PreOperativeAssessment;
import io.medsys.opteamer.repositories.PreOperativeAssessmentRepository;
import io.medsys.opteamer.services.PreOperativeAssessmentService;
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
public class PreOperativeAssessmentServiceImpl implements PreOperativeAssessmentService {
    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;

    @Override
    public List<PreOperativeAssessmentDTO> getAll() {
        List<PreOperativeAssessmentDTO> list = new ArrayList<>();
        Iterable<PreOperativeAssessment> all = preOperativeAssessmentRepository.findAll();
        all.forEach(preOperativeAssessment -> list.add(mapEntityToDTO(preOperativeAssessment)));
        return list;
    }

    @Override
    public Optional<PreOperativeAssessmentDTO> getPreOperativeAssessmentByName(String name) {
        try {
            PreOperativeAssessment preOperativeAssessment = preOperativeAssessmentRepository.findByName(name).orElseThrow();
            return Optional.of(mapEntityToDTO(preOperativeAssessment));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public PreOperativeAssessmentDTO createPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        PreOperativeAssessment preOperativeAssessment = ModelMapperUtils.mapDTOToEntity(preOperativeAssessmentDTO);
        preOperativeAssessmentRepository.save(preOperativeAssessment);
        return mapEntityToDTO(preOperativeAssessment);
    }

    @Override
    public boolean deletePreOperativeAssessment(String name) {
        return preOperativeAssessmentRepository.findByName(name).map(
                preOperativeAssessment -> {
                    preOperativeAssessmentRepository.delete(preOperativeAssessment);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<PreOperativeAssessmentDTO> updatePreOperativeAssessment(String name, PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        return preOperativeAssessmentRepository.findByName(name).map(
                preOperativeAssessment -> {
                    preOperativeAssessment.setName(preOperativeAssessmentDTO.getName());
                    preOperativeAssessmentRepository.save(preOperativeAssessment);
                    return mapEntityToDTO(preOperativeAssessment);
                });
    }

    private PreOperativeAssessmentDTO mapEntityToDTO(PreOperativeAssessment preOperativeAssessment) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(preOperativeAssessment, PreOperativeAssessmentDTO.class);
    }
}
