package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationRoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OPERATION_TYPES")
@NoArgsConstructor
@Getter
@Setter
public class OperationType {
    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private OperationRoomType roomType;

    @Column(name = "duration_hours")
    private Integer durationHours;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "optype_assets",
            joinColumns = @JoinColumn(name = "optype_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private Set<Asset> assets = new HashSet<Asset>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "optype_opproviders",
            joinColumns = @JoinColumn(name = "optype_id"),
            inverseJoinColumns = @JoinColumn(name = "opprovider_id")
    )
    private Set<OperationProvider> operationProviders = new HashSet<OperationProvider>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "optype_pre_op_a",
            joinColumns = @JoinColumn(name = "optype_id"),
            inverseJoinColumns = @JoinColumn(name = "pre_op_a_id")
    )
    private Set<PreOperativeAssessment> preOperativeAssessments = new HashSet<PreOperativeAssessment>();
}
