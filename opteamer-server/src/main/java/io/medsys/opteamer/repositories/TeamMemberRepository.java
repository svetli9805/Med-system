package io.medsys.opteamer.repositories;

import io.medsys.opteamer.model.TeamMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
    Optional<TeamMember> findById(Long id);
}
