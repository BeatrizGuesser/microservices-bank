package com.beatrizgg.mscreditassessor.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {

    private String card;
    private String brand;
    private BigDecimal releasedLimit;
}
