package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.dto.InventoryDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.model.Inventory;
import io.medsys.opteamer.repositories.AssetRepository;
import io.medsys.opteamer.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final AssetRepository assetRepository;

    public List<InventoryDTO> getAll() {
        List<InventoryDTO> list = new ArrayList<>();
        Iterable<Inventory> all = inventoryRepository.findAll();
        all.forEach(inventory -> list.add(getInventoryDto(inventory, inventory.getAsset())));
        return list;
    }

    public Optional<InventoryDTO> getInventoryById(Long id) {
        try {
            Inventory inventory = inventoryRepository.findById(id).orElseThrow();
            return Optional.of(getInventoryDto(inventory, inventory.getAsset()));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        Asset asset = assetRepository.findById(inventoryDTO.getAssetId()).get();
        if (asset == null) {
            throw new NoSuchElementException();
        }
        inventory.setAsset(asset);
        inventory.setCount(inventoryDTO.getCount());
        inventoryRepository.save(inventory);
        return getInventoryDto(inventory, asset);
    }

    public boolean deleteInventory(Long id) {
        return inventoryRepository.findById(id).map(
                inventory -> {
                    inventoryRepository.delete(inventory);
                    return true;
                }).orElse(false);
    }

    public Optional<InventoryDTO> updateInventory(Long id, InventoryDTO inventoryDTO) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventory.setCount(inventoryDTO.getCount());
            inventoryRepository.save(inventory);
            return getInventoryDto(inventory, inventory.getAsset());
        });
    }

    private InventoryDTO getInventoryDto(Inventory inventory, Asset asset) {
        return new InventoryDTO(inventory.getAssetId(), new AssetDTO(asset.getId(), asset.getAssetType(), asset.getName()), inventory.getCount());
    }
}
