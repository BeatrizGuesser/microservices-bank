package com.beatrizgg.mscards.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CardBrand brand;
    private BigDecimal income;
    private BigDecimal basicLimit;

    public Card(String name,
                CardBrand brand,
                BigDecimal income,
                BigDecimal basicLimit) {
        this.name = name;
        this.brand = brand;
        this.income = income;
        this.basicLimit = basicLimit;
    }
}

