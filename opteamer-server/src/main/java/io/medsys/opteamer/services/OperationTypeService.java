package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationTypeDTO;
import io.medsys.opteamer.mapper.OperationTypeMapper;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.OperationType;
import io.medsys.opteamer.model.PreOperativeAssessment;
import io.medsys.opteamer.repositories.AssetRepository;
import io.medsys.opteamer.repositories.OperationProviderRepository;
import io.medsys.opteamer.repositories.OperationTypeRepository;
import io.medsys.opteamer.repositories.PreOperativeAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationTypeService {
    private final AssetRepository assetRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    private final OperationProviderRepository operationProviderRepository;

    public List<OperationTypeDTO> getAll() {
        List<OperationTypeDTO> list = new ArrayList<>();
        Iterable<OperationType> all = operationTypeRepository.findAll();
        all.forEach(operationType -> list.add(OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType)));
        return list;
    }

    public Optional<OperationTypeDTO> getOperationTypeByName(String name) {
        try {
            OperationType operationType = operationTypeRepository.findByName(name).orElseThrow();
            return Optional.of(OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public OperationTypeDTO save(OperationTypeDTO operationTypeDTO) {
        OperationType operationType = OperationTypeMapper.INSTANCE.toOperationType(operationTypeDTO);
        setChildEntities(operationTypeDTO, operationType);
        operationType = operationTypeRepository.save(operationType);
        return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
    }

    public boolean deleteOperationType(String name) {
        return operationTypeRepository.findByName(name).map(
                operationType -> {
                    operationTypeRepository.delete(operationType);
                    return true;
                }).orElse(false);
    }

    public Optional<OperationTypeDTO> updateOperationType(String name, OperationTypeDTO operationTypeDTO) {
        return operationTypeRepository.findByName(name).map(
                operationType -> {
                    operationType.setRoomType(operationTypeDTO.getRoomType());
                    operationType.setName(operationTypeDTO.getName());
                    operationType.setDurationHours(operationTypeDTO.getDurationHours());
                    setChildEntities(operationTypeDTO, operationType);
                    operationTypeRepository.save(operationType);
                    return OperationTypeMapper.INSTANCE.toOperationTypeDTO(operationType);
                });
    }

    private void setChildEntities(OperationTypeDTO operationTypeDTO, OperationType operationType) {
        Set<Asset> assets = new HashSet<>();
        operationTypeDTO.getAssets().forEach(assetDTO -> {
            Asset asset = assetRepository.findById(assetDTO.getId()).get();
            assets.add(asset);
        });

        Set<PreOperativeAssessment> preOperativeAssessments = operationTypeDTO.getPreOperativeAssessments()
                .stream()
                .map(preOperativeAssessmentDTO -> preOperativeAssessmentRepository.findByName(preOperativeAssessmentDTO.getName())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());

        Set<OperationProvider> operationProviders = operationTypeDTO.getOperationProviders()
                .stream()
                .map(operationProviderDTO -> operationProviderRepository.findByType(operationProviderDTO.getType())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());

        operationType.setAssets(assets);
        operationType.setPreOperativeAssessments(preOperativeAssessments);
        operationType.setOperationProviders(operationProviders);
    }
}
