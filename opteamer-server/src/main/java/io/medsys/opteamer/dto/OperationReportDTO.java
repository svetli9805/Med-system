package io.medsys.opteamer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationReportDTO {
    private Long teamMemberId;
    private Long operationId;
    private TeamMemberDTO teamMember;
    private OperationDTO operation;
    private String report;
}
