package dev.daczdvid.assigment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanDetailedDTO {

    private Long id;

    private String domain;

    private boolean inProgress;

    private String type;

    private List<String> emailsOrUsers;

    private List<String> subdomains;

    private String begin;

    private String end;
}