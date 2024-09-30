package com.beatrizgg.mscreditassessor.FeignClient;

import com.beatrizgg.mscreditassessor.model.Card;
import com.beatrizgg.mscreditassessor.model.ClientCards;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscards", path = "/cards")
public interface CardsFeignClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<ClientCards>> getCardsByClient(@RequestParam("cpf") String cpf);

    @GetMapping(params = "income")
    ResponseEntity<List<Card>> getCardsIncomeUpTo(@RequestParam("income") Long income);
}
