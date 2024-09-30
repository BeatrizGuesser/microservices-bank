package com.beatrizgg.mscards.dto;

import com.beatrizgg.mscards.model.Card;
import com.beatrizgg.mscards.model.CardBrand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardDtoRequest {

    private String name;
    private CardBrand brand;
    private BigDecimal income;
    private BigDecimal basicLimit;

    public Card toModel(){
        return new Card(name, brand, income, basicLimit);
    }
}
