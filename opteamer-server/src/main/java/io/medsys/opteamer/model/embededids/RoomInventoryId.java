package io.medsys.opteamer.model.embededids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoomInventoryId implements Serializable {
    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "room_id")
    private Long roomId;
}
