package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.OperationRoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationTypeDTO {
    private String name;
    private OperationRoomType roomType;
    private Integer durationHours;
    private Set<AssetDTO> assetDTOS = new HashSet<>();
    private Set<OperationProviderDTO> operationProviderDTOS = new HashSet<>();
    private Set<PreOperativeAssessmentDTO> preOperativeAssessmentDTOS = new HashSet<>();
}
