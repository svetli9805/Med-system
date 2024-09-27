package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.dto.OperationRoomDTO;
import io.medsys.opteamer.dto.RoomInventoryDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.OperationRoom;
import io.medsys.opteamer.model.RoomInventory;
import io.medsys.opteamer.model.embededids.RoomInventoryId;
import io.medsys.opteamer.repositories.AssetRepository;
import io.medsys.opteamer.repositories.OperationRoomRepository;
import io.medsys.opteamer.repositories.RoomInventoryRepository;
import io.medsys.opteamer.services.RoomInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomInventoryServiceImpl implements RoomInventoryService {
    private final RoomInventoryRepository roomInventoryRepository;
    private final AssetRepository assetRepository;
    private final OperationRoomRepository operationRoomRepository;

    @Override
    public List<RoomInventoryDTO> getAll() {
        List<RoomInventoryDTO> list = new ArrayList<>();
        Iterable<RoomInventory> all = roomInventoryRepository.findAll();
        all.forEach(roomInventory -> list.add(getRoomInventoryDTO(
                roomInventory,
                roomInventory.getAsset(),
                roomInventory.getOperationRoom()
        )));
        return list;
    }

    @Override
    public Optional<RoomInventoryDTO> getRoomInventoryById(Long assetId, Long roomId) {
        try {
            RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
            RoomInventory roomInventory = roomInventoryRepository.findById(roomInventoryId).orElseThrow();
            return Optional.of(getRoomInventoryDTO(
                    roomInventory,
                    roomInventory.getAsset(),
                    roomInventory.getOperationRoom()
            ));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public RoomInventoryDTO save(RoomInventoryDTO roomInventoryDTO) {
        RoomInventory roomInventory = new RoomInventory();
        Asset asset = assetRepository.findById(roomInventoryDTO.getAssetId()).get();
        OperationRoom operationRoom = operationRoomRepository.findById(roomInventoryDTO.getOperationRoomId()).get();
        if (asset == null || operationRoom == null) {
            throw new NoSuchElementException();
        }
        roomInventory.setAsset(asset);
        roomInventory.setOperationRoom(operationRoom);
        roomInventory.setCount(roomInventoryDTO.getCount());
        roomInventoryRepository.save(roomInventory);
        return getRoomInventoryDTO(
                roomInventory,
                roomInventory.getAsset(),
                roomInventory.getOperationRoom()
        );
    }

    @Override
    public boolean deleteRoomInventory(Long assetId, Long roomId) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventoryRepository.delete(roomInventory);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<RoomInventoryDTO> updateRoomInventory(Long assetId, Long roomId, RoomInventoryDTO roomInventoryDTO) {
        RoomInventoryId roomInventoryId = new RoomInventoryId(assetId, roomId);
        return roomInventoryRepository.findById(roomInventoryId).map(roomInventory -> {
            roomInventory.setCount(roomInventoryDTO.getCount());
            roomInventoryRepository.save(roomInventory);
            return getRoomInventoryDTO(roomInventory, roomInventory.getAsset(), roomInventory.getOperationRoom());
        });
    }

    private static RoomInventoryDTO getRoomInventoryDTO(RoomInventory roomInventory, Asset asset, OperationRoom operationRoom) {
        return new RoomInventoryDTO(
                roomInventory.getAsset().getId(),
                roomInventory.getOperationRoom().getId(),
                new AssetDTO(asset.getId(), asset.getAssetType(), asset.getName()),
                new OperationRoomDTO(
                        operationRoom.getId(),
                        operationRoom.getRoomNr(),
                        operationRoom.getBuildingBlock(),
                        operationRoom.getFloor(),
                        operationRoom.getType(),
                        operationRoom.getState()
                ),
                roomInventory.getCount()
        );
    }
}
