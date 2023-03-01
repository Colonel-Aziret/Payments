package com.example.payments.service;


import com.example.payments.dto.SettlementPointDTO;
import com.example.payments.exception.SettlementPointNotFoundException;
import com.example.payments.model.SettlementPoint;

import java.util.List;
import java.util.UUID;

public interface SettlementPointService {
    UUID save(SettlementPointDTO settlementPointDTO);

    boolean update(SettlementPoint settlementPoint);

    SettlementPoint findById(UUID pointId) throws SettlementPointNotFoundException;

    void delete(UUID pointId) throws SettlementPointNotFoundException;

}
