package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.model.OperationRoom;
import io.medsys.opteamer.repositories.OperationRoomRepository;
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
public class OperationRoomService {
    private final OperationRoomRepository operationRoomRepository;

    public List<OperationRoomDTO> getAllOperationRooms() {
        List<OperationRoomDTO> list = new ArrayList<>();
        Iterable<OperationRoom> all = operationRoomRepository.findAll();
        all.forEach(operationRoom -> list.add(mapEntityToDTO(operationRoom)));
        return list;
    }

    public Optional<OperationRoomDTO> getOperationRoomById(Long id) {
        try {
            OperationRoom operationRoom = operationRoomRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(operationRoom));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public OperationRoomDTO createOperationRoom(OperationRoomDTO operationRoomDTO) {
        OperationRoom operationRoom = ModelMapperUtils.mapDTOToEntity(operationRoomDTO);
        operationRoomRepository.save(operationRoom);
        return mapEntityToDTO(operationRoom);
    }

    public boolean deleteOperationRoom(Long id) {
        return operationRoomRepository.findById(id).map(
                operationRoom -> {
                    operationRoomRepository.delete(operationRoom);
                    return true;
                }).orElse(false);
    }

    public Optional<OperationRoomDTO> updateOperationRoom(Long id, OperationRoomDTO operationRoomDTO) {
        return operationRoomRepository.findById(id).map(
                operationRoom -> {
                   operationRoom.setRoomNr(operationRoomDTO.getRoomNr());
                   operationRoom.setBuildingBlock(operationRoomDTO.getBuildingBlock());
                   operationRoom.setFloor(operationRoomDTO.getFloor());
                   operationRoom.setType(operationRoomDTO.getType());
                   operationRoom.setState(operationRoomDTO.getState());
                    operationRoomRepository.save(operationRoom);
                    return mapEntityToDTO(operationRoom);
                });
    }

    private OperationRoomDTO mapEntityToDTO(OperationRoom operationRoom) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoom, OperationRoomDTO.class);
    }
}
