package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationRoomDTO {
    private Long id;
    private String roomNr;
    private String buildingBlock;
    private String floor;
    private OperationRoomType type;
    private OperationRoomState state;
}
