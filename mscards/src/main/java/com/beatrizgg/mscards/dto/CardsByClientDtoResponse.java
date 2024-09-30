package com.beatrizgg.mscards.dto;

import com.beatrizgg.mscards.model.ClientCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsByClientDtoResponse {

    private String name;
    private String brand;
    private BigDecimal releasedLimit;

    public static  CardsByClientDtoResponse fromModel(ClientCard model) {
        return new CardsByClientDtoResponse(
                model.getCard().getName(),
                model.getCard().getBrand().toString(),
                model.getCardLimit()
        );
    }
}
