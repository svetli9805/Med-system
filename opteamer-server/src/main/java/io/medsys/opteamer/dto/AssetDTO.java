package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.AssetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssetDTO {
    private Long id;
    private AssetType type;
    private String name;
}
