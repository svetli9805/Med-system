package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.OperationReportDTO;
import io.medsys.opteamer.mapper.OperationReportMapper;
import io.medsys.opteamer.model.Operation;
import io.medsys.opteamer.model.OperationReport;
import io.medsys.opteamer.model.TeamMember;
import io.medsys.opteamer.model.embededids.OperationReportId;
import io.medsys.opteamer.repositories.OperationReportRepository;
import io.medsys.opteamer.repositories.OperationRepository;
import io.medsys.opteamer.repositories.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationReportService {
    private final OperationReportRepository operationReportRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final OperationRepository operationRepository;

    public List<OperationReportDTO> getAll() {
        List<OperationReportDTO> list = new ArrayList<>();
        Iterable<OperationReport> all = operationReportRepository.findAll();
        all.forEach(operationReport -> list.add(getOperationreportDTO(operationReport)));
        return list;
    }

    public Optional<OperationReportDTO> getOperationReportById(Long teamMemberId, Long operationId) {
        try {
            OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
            OperationReport operationReport = operationReportRepository.findById(operationReportId).orElseThrow();
            return Optional.of(getOperationreportDTO(operationReport));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public OperationReportDTO save(OperationReportDTO operationReportDTO) throws NoSuchElementException {
        OperationReport operationReport = new OperationReport();
        TeamMember teamMember = teamMemberRepository.findById(operationReportDTO.getTeamMemberId()).get();
        Operation operation = operationRepository.findById(operationReportDTO.getOperationId()).get();
        if (teamMember == null || operation == null) {
            throw new NoSuchElementException();
        }
        operationReport.setOperationReportId(new OperationReportId(teamMember.getId(), operation.getId()));
        operationReport.setTeamMember(teamMember);
        operationReport.setOperation(operation);
        operationReport.setReport(operationReportDTO.getReport());
        return getOperationreportDTO(operationReport);
    }

    public boolean deleteOperationReport(Long teamMemberId, Long operationId) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, teamMemberId);
        return operationReportRepository.findById(operationReportId).map(
                operationReport -> {
                    operationReportRepository.delete(operationReport);
                    return true;
                }).orElse(false);
    }

    public Optional<OperationReportDTO> updateOperationReport(Long teamMemberId, Long operationId, OperationReportDTO operationReportDTO) {
        OperationReportId operationReportId = new OperationReportId(teamMemberId, operationId);
        return operationReportRepository.findById(operationReportId).map(
                operationReport -> {
                    operationReport.setReport(operationReportDTO.getReport());
                    operationReportRepository.save(operationReport);
                    return getOperationreportDTO(operationReport);
                });
    }

    private OperationReportDTO getOperationreportDTO(OperationReport operationReport) {
        OperationReportDTO operationReportDTO = OperationReportMapper.INSTANCE.toOperationReportDTO(operationReport);
        operationReportDTO.setTeamMemberId(operationReport.getTeamMember().getId());
        operationReportDTO.setOperationId(operationReport.getOperation().getId());
        return operationReportDTO;
    }
}
