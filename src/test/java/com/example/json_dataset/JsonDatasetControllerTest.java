package com.example.jsondataset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.jsondataset.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JsonDatasetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecordRepository recordRepository;

    @BeforeEach
    void clearDatabase() {
        recordRepository.deleteAll();
    }

    @Test
    void testInsertRecord() throws Exception {
        Map<String, Object> record = Map.of(
                "id", 1,
                "name", "John Doe",
                "age", 30,
                "department", "Engineering"
        );

        String response = mockMvc.perform(post("/api/dataset/employee_dataset/record")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Record added successfully"))
                .andExpect(jsonPath("$.dataset").value("employee_dataset"))
                .andExpect(jsonPath("$.recordId").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Insert Record API Response: " + response);
    }

    @Test
    void testQueryGroupByDepartment() throws Exception {
        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "id", 2,
                        "name", "Alice Brown",
                        "age", 28,
                        "department", "Marketing"
                )))).andExpect(status().isOk());

        String response = mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("groupBy", "department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupedRecords.Marketing").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Group By Department API Response: " + response);
    }

    @Test
    void testQuerySortByAge() throws Exception {
        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "id", 1,
                        "name", "Jane Smith",
                        "age", 25,
                        "department", "Engineering"
                )))).andExpect(status().isOk());

        mockMvc.perform(post("/api/dataset/employee_dataset/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "id", 2,
                        "name", "Bob Johnson",
                        "age", 35,
                        "department", "Engineering"
                )))).andExpect(status().isOk());

        String response = mockMvc.perform(get("/api/dataset/employee_dataset/query")
                        .param("sortBy", "age")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sortedRecords").isArray())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Sort By Age API Response: " + response);
    }
}
