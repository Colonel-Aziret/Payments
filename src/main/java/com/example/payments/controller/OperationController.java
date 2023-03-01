package com.example.payments.controller;

import com.example.payments.dto.OperationDTO;
import com.example.payments.dto.OperationsRequestDTO;
import com.example.payments.model.Operation;
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
@RequestMapping("/api")
public class OperationController {

    @Autowired
    OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/operations")
    public ResponseEntity<?> createOperations(@RequestBody List<OperationDTO> operationDTOs) {
        List<OperationDTO> createdOperations = operationService.createOperations(operationDTOs);
        return ResponseEntity.ok(createdOperations);
    }
}

