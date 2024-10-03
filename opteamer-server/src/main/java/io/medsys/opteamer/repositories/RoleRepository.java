package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.Role;
import io.medsys.opteamer.model.enums.ERole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
