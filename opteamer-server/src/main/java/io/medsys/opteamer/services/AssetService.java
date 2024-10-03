package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.model.Asset;
import io.medsys.opteamer.repositories.AssetRepository;
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
public class AssetService {
    private final AssetRepository assetRepository;

    public List<AssetDTO> getAll() {
        List<AssetDTO> list = new ArrayList<>();
        Iterable<Asset> all = assetRepository.findAll();
        all.forEach(asset -> list.add(mapEntityToDTO(asset)));
        return list;
    }

    public Optional<AssetDTO> getAssetById(Long id) {
        try {
            Asset asset = assetRepository.findById(id).orElseThrow();
            return Optional.of(mapEntityToDTO(asset));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public AssetDTO save(AssetDTO assetDto) {
        Asset asset = ModelMapperUtils.mapDTOToEntity(assetDto);
        assetRepository.save(asset);
        return mapEntityToDTO(asset);
    }

    public boolean deleteAsset(Long id) {
        return assetRepository.findById(id).map(
                asset -> {
                    assetRepository.delete(asset);
                    return true;
                }).orElse(false);
    }

    public Optional<AssetDTO> updateAsset(Long id, AssetDTO assetDTO) {
        return assetRepository.findById(id).map(
                asset -> {
                    asset.setName(assetDTO.getName());
                    asset.setAssetType(assetDTO.getType());
                    assetRepository.save(asset);
                    return mapEntityToDTO(asset);
                });
    }

    private AssetDTO mapEntityToDTO(Asset asset) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(asset, AssetDTO.class);
    }
}
