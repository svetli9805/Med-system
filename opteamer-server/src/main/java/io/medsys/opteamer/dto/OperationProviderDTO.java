package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.OperationProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationProviderDTO {
    private OperationProviderType type;
}
