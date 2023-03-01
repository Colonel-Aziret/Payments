package com.example.payments.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SettlementPointListResponse {
    private List<SettlementPoint> points = new ArrayList<>();
}
