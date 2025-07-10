package com.example.jsondataset.repository;

import com.example.jsondataset.entity.DatasetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<DatasetRecord, Long> {
    List<DatasetRecord> findByDatasetName(String datasetName);
}
