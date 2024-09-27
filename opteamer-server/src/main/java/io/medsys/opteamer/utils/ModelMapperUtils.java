package io.medsys.opteamer.utils;

import io.medsys.opteamer.dto.*;
import io.medsys.opteamer.model.*;
import org.modelmapper.ModelMapper;

public class ModelMapperUtils {
    public static Asset mapDTOToEntity(AssetDTO assetDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(assetDTO, Asset.class);
    }

    public static OperationProvider mapDTOToEntity(OperationProviderDTO operationProviderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProviderDTO, OperationProvider.class);
    }


    public static OperationProviderDTO mapEntityToDTO(OperationProvider operationProvider) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationProvider, OperationProviderDTO.class);
    }

    public static OperationRoom mapDTOToEntity(OperationRoomDTO operationRoomDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(operationRoomDTO, OperationRoom.class);
    }

    public static Patient mapDTOToEntity(PatientDTO patientDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(patientDTO, Patient.class);
    }

    public static PreOperativeAssessment mapDTOToEntity(PreOperativeAssessmentDTO preOperativeAssessmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(preOperativeAssessmentDTO, PreOperativeAssessment.class);
    }

    public static TeamMember mapDTOToEntity(TeamMemberDTO teamMemberDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamMemberDTO, TeamMember.class);
    }
}
