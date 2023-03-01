package com.example.payments.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class Operations {
    private List<Operation> operations;

}
