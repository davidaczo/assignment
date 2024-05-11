package dev.daczdvid.assigment.controller;

import dev.daczdvid.assigment.repository.FileRepository;
import dev.daczdvid.assigment.model.Scan;
import dev.daczdvid.assigment.dto.ScanDetailedDTO;
import dev.daczdvid.assigment.dto.ScanMainDTO;
import dev.daczdvid.assigment.mapper.ScanMapper;
import dev.daczdvid.assigment.services.AmassService;
import dev.daczdvid.assigment.services.TheHarvesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/scans")
@CrossOrigin(origins = "*")
public class ScanController {


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TheHarvesterService theHarvesterService;

    @Autowired
    private AmassService amassService;

    @GetMapping()
    public ResponseEntity<List<ScanMainDTO>> getAllScans() {
        List<Scan> scans = fileRepository.retrieveAllScansFromJson();

        return ResponseEntity.ok(ScanMapper.toDTOList(scans));
    }

    @PostMapping("/{scanOption}")
    public ResponseEntity<Long> startScan(
            @PathVariable String scanOption,
            @RequestBody Map<String, String> requestBody
    ) {
        String domain = requestBody.get("domain");
        String datasource = requestBody.get("datasource");

        if (domain == null || domain.isEmpty()) {
            return ResponseEntity.badRequest().body(-1L);
        }

        Optional<Scan> scanResult;
        if ("theharvester".equals(scanOption)) {
            if (datasource == null || datasource.isEmpty()) {
                return ResponseEntity.badRequest().body(-1L);
            }
            scanResult = performScan(scanOption, domain, datasource).getBody();
        } else {
            scanResult = performScan(scanOption, domain, null).getBody();
        }

        if (scanResult.isPresent()) {
            return ResponseEntity.ok(scanResult.get().getId());
        }

        return ResponseEntity.badRequest().body(-1L);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ScanDetailedDTO> getSingleMovie(@PathVariable Long id) {
        Scan scan = fileRepository.findById(id);
        if(scan == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ScanMapper.DetailedToDTO(scan));
    }

    private ResponseEntity<Optional<Scan>> performScan(String scanOption, String domain, String datasource) {
        switch (scanOption) {
            case "amass":
                Scan amassScan = amassService.scanWithAmass(domain);
                return ResponseEntity.ok(Optional.of(amassScan));
            case "theharvester":
                Scan theHarvesterScan = theHarvesterService.scanWithTheHarvester(domain, datasource);
                return ResponseEntity.ok(Optional.of(theHarvesterScan));
            default:
                return ResponseEntity.badRequest().build();
        }
    }

}