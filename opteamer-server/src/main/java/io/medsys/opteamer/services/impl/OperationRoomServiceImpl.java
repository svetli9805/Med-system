package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.model.OperationRoom;
import io.medsys.opteamer.repositories.OperationRoomRepository;
import io.medsys.opteamer.services.OperationRoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationRoomServiceImpl implements OperationRoomService {
    private final OperationRoomRepository operationRoomRepository;

    @Override
    public List<OperationRoomDTO> getAllOperationRooms() {
        List<OperationRoomDTO> list = new ArrayList<>();
        Iterable<OperationRoom> all = operationRoomRepository.findAll();
        all.forEach(operationRoom -> list.add(mapEntityToDTO(operationRoom)));
        return list;
    }

    @Override
    public Optional<OperationRoomDTO> getOperationRoomById(Long id) {
        try {
            OperationRoom operationRoom = operationRoomRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(operationRoom));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public OperationRoomDTO createOperationRoom(OperationRoomDTO operationRoomDTO) {
        OperationRoom operationRoom = mapDTOToEntity(operationRoomDTO);
        operationRoomRepository.save(operationRoom);
        return mapEntityToDTO(operationRoom);
    }

    @Override
    public boolean deleteOperationRoom(Long id) {
        return operationRoomRepository.findById(id).map(
                operationRoom -> {
                    operationRoomRepository.delete(operationRoom);
                    return true;
                }).orElse(false);
    }

    @Override
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

    private OperationRoom mapDTOToEntity(OperationRoomDTO operationRoomDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoomDTO, OperationRoom.class);
    }
}
