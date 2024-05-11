package dev.daczdvid.assigment.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.daczdvid.assigment.model.Scan;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@NoArgsConstructor
public class FileRepository {
    private String SCAN_RESULT_FILE = "scan_results.json";

    public void writeScanResultToJson(Scan newScan) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Scan> scans = retrieveAllScansFromJson();
        File file = new File(SCAN_RESULT_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("[\n");

            for (int i = 0; i < scans.size(); i++) {
                writer.write(objectMapper.writeValueAsString(scans.get(i)));
                writer.write(",\n");
            }
            writer.write(objectMapper.writeValueAsString(newScan));

            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateScan(Scan newScan) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Scan> scans = retrieveAllScansFromJson();
        File file = new File(SCAN_RESULT_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("[\n");

            for (int i = 0; i < scans.size(); i++) {
                if(scans.get(i).getId() == newScan.getId()) {
                    writer.write(objectMapper.writeValueAsString(newScan));
                } else {
                    writer.write(objectMapper.writeValueAsString(scans.get(i)));
                }
                if(scans.size() != i + 1) {
                    writer.write(",\n");
                }
            }

            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scan findById(Long id) {
        List<Scan> scans = retrieveAllScansFromJson();
        return scans.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public int numberOfScans() {
        return retrieveAllScansFromJson().toArray().length;
    }

    public List<Scan> retrieveAllScansFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(SCAN_RESULT_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Scan>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
