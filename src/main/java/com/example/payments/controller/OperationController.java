package com.example.payments.controller;

import com.example.payments.dto.OperationDTO;
import com.example.payments.dto.OperationsRequestDTO;
import com.example.payments.encrypt.RequestEncryptor;
import com.example.payments.exception.SettlementPointNotFoundException;
import com.example.payments.model.Operation;
import com.example.payments.model.Operations;
import com.example.payments.model.SettlementPoint;
import com.example.payments.repository.OperationRepository;
import com.example.payments.repository.SettlementPointRepository;
import com.example.payments.service.OperationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/operations")
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    OperationRepository operationRepository;
    @Autowired
    private SettlementPointRepository settlementPointRepository;

    private RequestEncryptor requestEncryptor;


    @PostMapping
    public ResponseEntity<String> addOperations(@RequestBody Operations operations, @RequestHeader("UUID") UUID uuid) throws Exception {
        if (operations == null) {
            return ResponseEntity.badRequest().body("Operations are missing");
        }
        RequestEncryptor encryptor = new RequestEncryptor();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(operations);
        String encryptedRequestBody = encryptor.encrypt(requestBody);

        // Добавление операции в базу данных
        SettlementPoint settlementPoint = settlementPointRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("SettlementPoint not found with id " + settlementPointRepository.findById(uuid)));
        for (Operation operation : operations.getOperations()) {
            operation.setDate(new Date());
            operationRepository.save(operation);
        }
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/list")
    public List<Operation> getAllOperations() {
        return operationService.getAllOperations();
    }
}

