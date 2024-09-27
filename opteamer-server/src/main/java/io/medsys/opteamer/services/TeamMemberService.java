package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.TeamMemberDTO;

import java.util.List;
import java.util.Optional;

public interface TeamMemberService {
    List<TeamMemberDTO> getAll();
    Optional<TeamMemberDTO> getTeamMemberById(Long id);
    TeamMemberDTO createTeamMember(TeamMemberDTO teamMember);
    boolean deleteTeamMember(Long id);
    Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO);
}
