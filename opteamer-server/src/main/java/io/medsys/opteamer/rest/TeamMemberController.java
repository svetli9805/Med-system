package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.TeamMemberDTO;
import io.medsys.opteamer.services.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/team-members")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembers() {
        List<TeamMemberDTO> teamMembers = teamMemberService.getAll();
        return ResponseEntity.status(OK).body(teamMembers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> getTeamMemberById(@PathVariable(name = "id") Long id) {
        Optional<TeamMemberDTO> teamMemberOptional = teamMemberService.getTeamMemberById(id);
        return teamMemberOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<TeamMemberDTO> createTeamMember(@RequestBody TeamMemberDTO teamMemberDTO) {
        TeamMemberDTO createdTeamMemberDto = teamMemberService.createTeamMember(teamMemberDTO);
        return ResponseEntity.status(CREATED).body(createdTeamMemberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> updateTeamMember(@PathVariable(name = "id") Long id, @RequestBody TeamMemberDTO teamMemberDTO) {
        Optional<TeamMemberDTO> optionalTeamMemberDTO = teamMemberService.updateTeamMember(id, teamMemberDTO);
        return optionalTeamMemberDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable(name = "id") Long id) {
        boolean deleted = teamMemberService.deleteTeamMember(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
