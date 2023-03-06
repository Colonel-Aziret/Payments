package com.example.payments.controller;

import com.example.payments.dto.SettlementPointDTO;
import com.example.payments.encrypt.RequestEncryptor;
import com.example.payments.exception.SettlementPointNotFoundException;
import com.example.payments.model.SettlementPoint;
import com.example.payments.repository.SettlementPointRepository;
import com.example.payments.service.SettlementPointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/point")
public class SettlementPointController {
    @Autowired
    SettlementPointService settlementPointService;
    @Autowired
    private SettlementPointRepository settlementPointRepository;

    private RequestEncryptor requestEncryptor;

    @PostMapping("/add")
    public ResponseEntity<String> save(@RequestBody SettlementPointDTO settlementPointDTO) throws Exception {
        SettlementPoint settlementPoint = new SettlementPoint();
        if (settlementPoint.getPointId() == null) {
            return ResponseEntity.badRequest().body("SettlementPoint not found");
        }
        RequestEncryptor encryptor = new RequestEncryptor();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(settlementPointDTO);
        String encryptedRequestBody = encryptor.encrypt(requestBody);
        settlementPointDTO.setCreated(LocalDate.now());
        settlementPointDTO.setUpdated(LocalDate.now());
        UUID pointId = settlementPointService.save(settlementPointDTO);
        String responseMessage = "Точка расчета успешно зарегистрирована. Point ID: " + pointId;
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(
            @RequestHeader("UUID") UUID uuid,
            @RequestBody SettlementPoint settlementPoint) {

        // Проверка UUID в заголовке
        if (!uuid.equals(settlementPoint.getPointId())) {
            return ResponseEntity.badRequest().build();
        }

        // Обновление регистрационных параметров точки расчета в базе данных
        settlementPoint.setUpdated(LocalDate.now());
        // Вызываем сервис для обновления регистрационных параметров точки расчета
        boolean success = settlementPointService.update(settlementPoint);

        if (success) {
            return ResponseEntity.ok().body("Точка расчета успешно изменена");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Обработчик исключения, когда точка расчета не найдена
    @ExceptionHandler(SettlementPointNotFoundException.class)
    public ResponseEntity<String> handleSettlementPointNotFoundException(SettlementPointNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePoint(@RequestHeader("UUID") UUID uuid,
                                              @RequestBody SettlementPoint settlementPoint) {
        if (!uuid.equals(settlementPoint.getPointId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            settlementPointService.delete(settlementPoint.getPointId());
            return ResponseEntity.status(HttpStatus.OK).body("Точка расчета успешно удалена");
        } catch (SettlementPointNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, List<SettlementPoint>>> getPoints() {
        List<SettlementPoint> points = settlementPointRepository.findAll();
        Map<String, List<SettlementPoint>> response = new HashMap<>();
        response.put("points", points);
        return ResponseEntity.ok(response);
    }
}