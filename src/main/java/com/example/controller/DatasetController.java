package com.example.jsondataset.controller;

import com.example.jsondataset.entity.DatasetRecord;
import com.example.jsondataset.service.DatasetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dataset")
public class DatasetController {

    @Autowired
    private DatasetService service;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/{datasetName}/record")
    public ResponseEntity<?> insertRecord(@PathVariable String datasetName, @RequestBody Map<String, Object> recordData) throws Exception {
        DatasetRecord saved = service.insertRecord(datasetName, recordData);
        return ResponseEntity.ok(Map.of(
                "message", "Record added successfully",
                "dataset", datasetName,
                "recordId", saved.getId()
        ));
    }

    @GetMapping("/{datasetName}/query")
    public ResponseEntity<?> queryDataset(@PathVariable String datasetName,
                                          @RequestParam(required = false) String groupBy,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String order) throws Exception {

        if (groupBy != null) {
            return ResponseEntity.ok(Map.of("groupedRecords", service.groupBy(datasetName, groupBy)));
        } else if (sortBy != null) {
            return ResponseEntity.ok(Map.of("sortedRecords", service.sortBy(datasetName, sortBy, order)));
        } else {
            return ResponseEntity.badRequest().body("Either groupBy or sortBy parameter is required");
        }
    }
}
