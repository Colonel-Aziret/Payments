package com.example.payments.repository;

import com.example.payments.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

}
