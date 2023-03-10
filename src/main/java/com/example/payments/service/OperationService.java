package com.example.payments.service;

import com.example.payments.dto.OperationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface OperationService {
    List<OperationDTO> createOperations(List<OperationDTO> operationDTOs);
}
