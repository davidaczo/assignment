package dev.daczdvid.assigment.mapper;

import dev.daczdvid.assigment.model.Scan;
import dev.daczdvid.assigment.dto.ScanDetailedDTO;
import dev.daczdvid.assigment.dto.ScanMainDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScanMapper {

    public static ScanMainDTO toDTO(Scan scan) {
        int numberOfEmails = scan.getEmailsOrUsers() != null ? scan.getEmailsOrUsers().size() : 0;
        int numberOfSubdomains = scan.getSubdomains() != null ? scan.getSubdomains().size() : 0;
        return new ScanMainDTO(
                scan.getId(),
                scan.getDomain(),
                scan.isInProgress(),
                scan.getType(),
                numberOfEmails,
                numberOfSubdomains,
                scan.getBegin(),
                scan.getEnd()
                );
    }

    public static List<ScanMainDTO> toDTOList(List<Scan> scans) {
        return scans.stream().map(ScanMapper::toDTO).collect(Collectors.toList());
    }

    public static ScanDetailedDTO DetailedToDTO(Scan scan) {
        return new ScanDetailedDTO(
                scan.getId(),
                scan.getDomain(),
                scan.isInProgress(),
                scan.getType(),
                scan.getEmailsOrUsers(),
                scan.getSubdomains(),
                scan.getBegin(),
                scan.getEnd()
        );
    }

    public static List<ScanDetailedDTO> DetailedToDTOList(List<Scan> scans) {
        return scans.stream().map(ScanMapper::DetailedToDTO).collect(Collectors.toList());
    }
}
