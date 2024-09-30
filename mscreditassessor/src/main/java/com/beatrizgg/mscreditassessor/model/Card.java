package com.beatrizgg.mscreditassessor.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Card {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal basicLimit;
}
