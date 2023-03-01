package com.example.payments.service;

import com.example.payments.dto.OperationDTO;
import com.example.payments.model.Operation;
import com.example.payments.model.SettlementPoint;
import com.example.payments.repository.OperationRepository;
import com.example.payments.repository.SettlementPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    SettlementPointRepository settlementPointRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }


    @Override
    public List<OperationDTO> createOperations(List<OperationDTO> operationDTOs) {
        List<Operation> operations = new ArrayList<>();
        for (OperationDTO operationDTO : operationDTOs) {
            Operation operation = new Operation();
            operation.setPointId(operationDTO.getPointId());
            operation.setDate(new Date());
            operation.setSum(operationDTO.getSum());
            operation.setCurrency(operationDTO.getCurrency());
            operation.setCommission(operationDTO.getCommission());
            operation.setSupplierTin(operationDTO.getSupplierTin());
            operation.setSupplierName(operationDTO.getSupplierName());
            operation.setNonresidentName(operationDTO.getNonresidentName());
            operation.setPaymentDetails(operationDTO.getPaymentDetails());
            operation.setPaymentType(operationDTO.getPaymentType());
            operations.add(operation);
        }
        List<Operation> createdOperations = operationRepository.saveAll(operations);
        List<OperationDTO> createdOperationDTOs = new ArrayList<>();
        for (Operation operation : createdOperations) {
            OperationDTO operationDTO = new OperationDTO();
            operationDTO.setPointId(operation.getPointId());
            operation.setDate(new Date());
            operationDTO.setSum(operation.getSum());
            operationDTO.setCurrency(operation.getCurrency());
            operationDTO.setCommission(operation.getCommission());
            operationDTO.setSupplierTin(operation.getSupplierTin());
            operationDTO.setSupplierName(operation.getSupplierName());
            operationDTO.setNonresidentName(operation.getNonresidentName());
            operationDTO.setPaymentDetails(operation.getPaymentDetails());
            operationDTO.setPaymentType(operation.getPaymentType());
            createdOperationDTOs.add(operationDTO);
        }
        return createdOperationDTOs;
    }
}
