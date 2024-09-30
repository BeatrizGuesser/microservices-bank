package com.beatrizgg.mscreditassessor.controller;

import com.beatrizgg.mscreditassessor.model.ClientSituation;
import com.beatrizgg.mscreditassessor.service.CreditAssessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit-assessor")
@RequiredArgsConstructor
public class CreditAssessorController {

    private final CreditAssessorService creditAssessorService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "/client-situation", params = "cpf")
    public ResponseEntity<ClientSituation> clientSituation(@RequestParam("cpf") String cpf) {
        ClientSituation clientSituation = creditAssessorService.getClientSituation(cpf);
        return ResponseEntity.ok(clientSituation);
    }
}
