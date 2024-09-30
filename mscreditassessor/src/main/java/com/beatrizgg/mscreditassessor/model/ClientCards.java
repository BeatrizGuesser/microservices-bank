package com.beatrizgg.mscreditassessor.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCards {

    private String name;
    private String brand;
    private BigDecimal releasedLimit;
}
