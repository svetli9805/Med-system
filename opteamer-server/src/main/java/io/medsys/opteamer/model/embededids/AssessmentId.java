package io.medsys.opteamer.model.embededids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentId implements Serializable {
    @Column(name = "team_member_id")
    private Long teamMemberId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "pre_op_a_id")
    private String preOpAId;
}

