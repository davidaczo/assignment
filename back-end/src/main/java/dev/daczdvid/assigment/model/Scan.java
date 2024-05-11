package dev.daczdvid.assigment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scan {

    private Long id;

    private String domain;

    private boolean inProgress;

    private String type;

    private List<String> emails;

    private List<String> subdomains;

    private String begin;

    private String end;

}
