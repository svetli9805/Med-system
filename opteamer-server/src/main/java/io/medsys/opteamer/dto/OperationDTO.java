package io.medsys.opteamer.dto;

import io.medsys.opteamer.model.OperationType;
import io.medsys.opteamer.model.enums.OperationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationDTO {
    private Long id;
    private OperationType operationType;
    private OperationRoomDTO operationRoom;
    private PatientDTO patient;
    private OperationState state;
    private LocalDateTime startDate;
    private Set<TeamMemberDTO> teamMembers;
}
