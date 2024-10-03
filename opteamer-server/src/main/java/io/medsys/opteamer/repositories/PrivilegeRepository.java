package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.Privilege;
import io.medsys.opteamer.model.enums.EPrivilege;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
    Optional<Privilege> findByName(EPrivilege name);
}
