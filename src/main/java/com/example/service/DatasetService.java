package com.example.jsondataset.service;

import com.example.jsondataset.entity.DatasetRecord;
import com.example.jsondataset.repository.RecordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatasetService {

    @Autowired
    private RecordRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    public DatasetRecord insertRecord(String datasetName, Map<String, Object> jsonData) {
        DatasetRecord record = new DatasetRecord();
        record.setDatasetName(datasetName);

        try {
            String json = objectMapper.writeValueAsString(jsonData);
            record.setJsonData(json);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing JSON", e);
        }

        return repository.save(record);
    }

    public List<Map<String, Object>> fetchJsonRecords(String datasetName) {
        List<DatasetRecord> records = repository.findByDatasetName(datasetName);
        List<Map<String, Object>> jsonObjects = new ArrayList<>();

        for (DatasetRecord r : records) {
            try {
                Map<String, Object> obj = objectMapper.readValue(r.getJsonData(), new TypeReference<Map<String, Object>>() {});
                jsonObjects.add(obj);
            } catch (Exception e) {
                // skip malformed records
            }
        }

        return jsonObjects;
    }

    public Map<String, List<Map<String, Object>>> groupBy(String datasetName, String groupByField) {
        List<Map<String, Object>> records = fetchJsonRecords(datasetName);

        return records.stream()
                .filter(r -> r.containsKey(groupByField))
                .collect(Collectors.groupingBy(
                        (Map<String, Object> r) -> String.valueOf(r.get(groupByField))
                ));
    }

    public List<Map<String, Object>> sortBy(String datasetName, String sortByField, String order) {
        List<Map<String, Object>> records = fetchJsonRecords(datasetName);

        Comparator<Map<String, Object>> comparator = Comparator.comparing(
                r -> ((Comparable) r.get(sortByField))
        );

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return records.stream()
                .filter(r -> r.containsKey(sortByField))
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
