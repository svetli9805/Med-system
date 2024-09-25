package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationRoomState;
import io.medsys.opteamer.model.enums.OperationRoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OPERATION_ROOMS")
@NoArgsConstructor
@Getter
@Setter
public class OperationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_nr")
    private String roomNr;

    @Column(name = "building_block")
    private String buildingBlock;

    @Column(name = "floor")
    private String floor;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationRoomType type;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OperationRoomState state;
}
