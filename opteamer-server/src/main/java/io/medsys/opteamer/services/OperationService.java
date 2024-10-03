package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationDTO;
import io.medsys.opteamer.mapper.OperationMapper;
import io.medsys.opteamer.model.*;
import io.medsys.opteamer.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final OperationRoomRepository operationRoomRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final PatientRepository patientRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<OperationDTO> getAll() {
        List<OperationDTO> list = new ArrayList<>();
        Iterable<Operation> all = operationRepository.findAll();
        all.forEach(operation -> list.add(OperationMapper.INSTANCE.toOperationDTO(operation)));
        return list;
    }

    public Optional<OperationDTO> getOperationById(Long id) {
        try {
            Operation operation = operationRepository.findById(id).orElseThrow();
            return Optional.of(OperationMapper.INSTANCE.toOperationDTO(operation));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public OperationDTO save(OperationDTO operationDTO) {
        Operation operation = OperationMapper.INSTANCE.toOperation(operationDTO);
        setChildEntities(operationDTO, operation);
        operation = operationRepository.save(operation);
        return OperationMapper.INSTANCE.toOperationDTO(operation);
    }

    public boolean deleteOperation(Long id) {
        return operationRepository.findById(id).map(
                operation -> {
                    operationRepository.delete(operation);
                    return true;
                }).orElse(false);
    }

    public Optional<OperationDTO> updateOperation(Long id, OperationDTO operationDTO) {
        return operationRepository.findById(id).map(
                operation -> {
                    operation.setState(operationDTO.getState());
                    operation.setStartDate(operationDTO.getStartDate());
                    setChildEntities(operationDTO, operation);
                    operationRepository.save(operation);
                    return OperationMapper.INSTANCE.toOperationDTO(operation);
                });
    }

    private void setChildEntities(OperationDTO operationDTO, Operation operation) {
        OperationType operationType = operationTypeRepository.findByName(operationDTO.getOperationType().getName()).get();
        OperationRoom operationRoom = operationRoomRepository.findById(operationDTO.getOperationRoom().getId()).get();
        Patient patient = patientRepository.findById(operationDTO.getPatient().getId()).get();

        Set<TeamMember> teamMembers = operationDTO.getTeamMembers()
                .stream()
                .map(teamMemberDTO -> teamMemberRepository.findById(teamMemberDTO.getId())
                        .orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toSet());

        operation.setOperationType(operationType);
        operation.setOperationRoom(operationRoom);
        operation.setPatient(patient);
        operation.setTeamMembers(teamMembers);
    }
}
