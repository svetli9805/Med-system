package io.medsys.opteamer.services.impl;

import io.medsys.opteamer.dto.TeamMemberDTO;
import io.medsys.opteamer.model.OperationProvider;
import io.medsys.opteamer.model.TeamMember;
import io.medsys.opteamer.repositories.TeamMemberRepository;
import io.medsys.opteamer.services.TeamMemberService;
import io.medsys.opteamer.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public List<TeamMemberDTO> getAll() {
        List<TeamMemberDTO> list = new ArrayList<>();
        Iterable<TeamMember> all = teamMemberRepository.findAll();
        all.forEach(teamMember -> list.add(mapEntityToDTO(teamMember)));
        return list;
    }

    @Override
    public Optional<TeamMemberDTO> getTeamMemberById(Long id) {
        try {
            TeamMember teamMember = teamMemberRepository.findById(id).orElseThrow();
            return Optional.of(getMappedTeamMemberDTO(teamMember));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public TeamMemberDTO createTeamMember(TeamMemberDTO teamMemberDto) {
        TeamMember teamMember = ModelMapperUtils.mapDTOToEntity(teamMemberDto);
        teamMember = teamMemberRepository.save(teamMember);
        return getMappedTeamMemberDTO(teamMember);
    }

    @Override
    public boolean deleteTeamMember(Long id) {
        return teamMemberRepository.findById(id).map(
                teamMember -> {
                    teamMemberRepository.delete(teamMember);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<TeamMemberDTO> updateTeamMember(Long id, TeamMemberDTO teamMemberDTO) {
        return teamMemberRepository.findById(id).map(
                teamMember -> {
                    teamMember.setName(teamMemberDTO.getName());
                    OperationProvider operationProvider = ModelMapperUtils.mapDTOToEntity(teamMemberDTO.getOperationProviderDTO());
                    teamMember.setOperationProvider(operationProvider);
                    teamMemberRepository.save(teamMember);
                    return getMappedTeamMemberDTO(teamMember);
                });
    }

    private TeamMemberDTO mapEntityToDTO(TeamMember teamMember) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamMember, TeamMemberDTO.class);
    }

    private static TeamMemberDTO getMappedTeamMemberDTO(TeamMember teamMember) {
        ModelMapper modelMapper = new ModelMapper();
        TeamMemberDTO mappedTeamMemberDTO = modelMapper.map(teamMember, TeamMemberDTO.class);
        mappedTeamMemberDTO.setOperationProviderDTO(ModelMapperUtils.mapEntityToDTO(teamMember.getOperationProvider()));
        return mappedTeamMemberDTO;
    }
}
