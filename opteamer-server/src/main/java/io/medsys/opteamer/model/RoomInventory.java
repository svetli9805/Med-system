package io.medsys.opteamer.model;

import io.medsys.opteamer.model.embededids.RoomInventoryId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROOM_INVENTORY")
@NoArgsConstructor
@Getter
@Setter
public class RoomInventory {
    @EmbeddedId
    private RoomInventoryId roomInventoryId;

    @ManyToOne
    @MapsId("assetId")
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private OperationRoom operationRoom;

    private Integer count;
}
