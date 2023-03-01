package com.example.payments.service;

import com.example.payments.dto.SettlementPointDTO;
import com.example.payments.exception.SettlementPointNotFoundException;
import com.example.payments.model.PointAddress;
import com.example.payments.model.SettlementPoint;
import com.example.payments.repository.SettlementPointRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SettlementPointServiceImpl implements SettlementPointService {
    @Autowired
    SettlementPointRepository settlementPointRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public SettlementPoint convertToEntity(SettlementPointDTO settlementPointDTO) {
        return modelMapper.map(settlementPointDTO, SettlementPoint.class);
    }


    public UUID save(SettlementPointDTO settlementPointDTO) {
        // Преобразуем PostDto в сущность Post
        SettlementPoint settlementPoint = convertToEntity(settlementPointDTO);
        // Сохраняем запись в базу данных и получаем идентификатор
        settlementPointRepository.save(settlementPoint);
        UUID pointId = settlementPointRepository.save(settlementPoint).getPointId();
        return pointId;
    }

    public boolean update(SettlementPoint settlementPoint) throws SettlementPointNotFoundException {
        try {
            SettlementPoint existingPoint = findById(settlementPoint.getPointId());
            existingPoint.setClientTin(settlementPoint.getClientTin());
            existingPoint.setClientName(settlementPoint.getClientName());
            existingPoint.setActivity(settlementPoint.getActivity());
            existingPoint.setObjectType(settlementPoint.getObjectType());
            existingPoint.setPointType(settlementPoint.getPointType());
            existingPoint.setPointFormat(settlementPoint.getPointFormat());
            existingPoint.setEquipmentType(settlementPoint.getEquipmentType());
            existingPoint.setEquipmentId(settlementPoint.getEquipmentId());
            existingPoint.setPointAddress(settlementPoint.getPointAddress());
            settlementPointRepository.save(existingPoint);
            return true;
        } catch (SettlementPointNotFoundException e) {
            return false;
        }
    }

    @Override
    public SettlementPoint findById(UUID pointId) throws SettlementPointNotFoundException {
        Optional<SettlementPoint> optionalPoint = settlementPointRepository.findById(pointId);
        if (optionalPoint.isPresent()) {
            return optionalPoint.get();
        } else {
            throw new SettlementPointNotFoundException(pointId);
        }
    }

    @Override
    public void delete(UUID pointId) throws SettlementPointNotFoundException {
        SettlementPoint existingPoint = findById(pointId);
        settlementPointRepository.delete(existingPoint);
    }
}
