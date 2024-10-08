package io.medsys.opteamer.model;

import io.medsys.opteamer.model.enums.OperationProviderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OPERATION_PROVIDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperationProvider {
    @Id
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationProviderType type;
}
