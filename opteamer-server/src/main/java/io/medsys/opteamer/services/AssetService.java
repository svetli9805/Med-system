package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.AssetDTO;

import java.util.List;
import java.util.Optional;

public interface AssetService {
    List<AssetDTO> getAll();
    Optional<AssetDTO> getAssetById(Long id);
    AssetDTO save(AssetDTO assetDto);
    boolean deleteAsset(Long id);
    Optional<AssetDTO> updateAsset(Long id, AssetDTO assetDTO);
}
