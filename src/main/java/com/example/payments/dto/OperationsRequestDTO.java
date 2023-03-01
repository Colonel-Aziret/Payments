package com.example.payments.dto;

import lombok.Data;

import java.util.List;

@Data
public class OperationsRequestDTO {
    private List<OperationDTO> operations;
}
