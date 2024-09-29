package io.medsys.opteamer.mapper;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    AssetDTO toAssetDTO(Asset asset);

    Asset toAsset(AssetDTO assetDTO);
}
