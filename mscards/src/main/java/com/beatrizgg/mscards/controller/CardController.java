package com.beatrizgg.mscards.controller;

import com.beatrizgg.mscards.dto.CardDtoRequest;
import com.beatrizgg.mscards.dto.CardsByClientDtoResponse;
import com.beatrizgg.mscards.model.Card;
import com.beatrizgg.mscards.model.ClientCard;
import com.beatrizgg.mscards.service.CardService;
import com.beatrizgg.mscards.service.ClientCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final ClientCardService clientCardService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CardDtoRequest request) {
        Card card = request.toModel();
        cardService.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "income")
    public ResponseEntity<List<Card>> getCardsIncomeUpTo(@RequestParam("income") Long income) {
        List<Card> list = cardService.getCardsWithIncomeLessThanOrEqual(income);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CardsByClientDtoResponse>> getCardsByClient(@RequestParam("cpf") String cpf) {
        List<ClientCard> list = clientCardService.listCardsByCpf(cpf);
        List<CardsByClientDtoResponse> resultList = list.stream()
                .map(CardsByClientDtoResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

}
