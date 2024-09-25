package io.medsys.opteamer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "INVENTORY")
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
