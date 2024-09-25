package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationProviderType;
import jakarta.persistence.*;

@Entity
@Table(name = "OPERATION_PROVIDERS")
public class OperationProvider {
    @Id
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationProviderType type;
}
