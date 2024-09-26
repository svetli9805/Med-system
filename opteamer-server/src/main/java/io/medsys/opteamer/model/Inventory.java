package io.medsys.opteamer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "INVENTORY")
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @Column(name = "asset_id")
    private  Long assetId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;
}
