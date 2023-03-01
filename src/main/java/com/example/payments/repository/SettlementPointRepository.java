package com.example.payments.repository;

import com.example.payments.model.SettlementPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SettlementPointRepository extends JpaRepository<SettlementPoint, UUID> {
    <S extends SettlementPoint> S save(S entity);

    List<SettlementPoint> findAll();

}
