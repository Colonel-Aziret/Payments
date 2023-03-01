package com.example.payments.controller;

import com.example.payments.dto.OperationDTO;
import com.example.payments.dto.OperationsRequestDTO;
import com.example.payments.model.Operation;
import com.example.payments.model.Operations;
import com.example.payments.repository.OperationRepository;
import com.example.payments.service.OperationService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    public OperationController(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @PostMapping
    public ResponseEntity<String> addOperations(@RequestBody Operations operations, @RequestHeader("UUID") UUID uuid) {
        // Добавление операций в базу данных
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

