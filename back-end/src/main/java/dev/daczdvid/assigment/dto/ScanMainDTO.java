package dev.daczdvid.assigment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanMainDTO {

    private Long id;

    private String domain;

    private boolean inProgress;

    private String type;

    private int numberOfEmails;

    private int numberOfSubdomains;

    private String  begin;

    private String end;
}