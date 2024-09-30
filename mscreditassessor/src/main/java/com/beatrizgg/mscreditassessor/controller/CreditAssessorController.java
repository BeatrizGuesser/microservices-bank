package com.beatrizgg.mscreditassessor.controller;

import com.beatrizgg.mscreditassessor.exception.ClientDataNotFound;
import com.beatrizgg.mscreditassessor.exception.MicroservicesCommunicationErrorException;
import com.beatrizgg.mscreditassessor.model.ClientSituation;
import com.beatrizgg.mscreditassessor.service.CreditAssessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity clientSituation(@RequestParam("cpf") String cpf) {
        try {
            ClientSituation clientSituation = creditAssessorService.getClientSituation(cpf);
            return ResponseEntity.ok(clientSituation);

        } catch (ClientDataNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (MicroservicesCommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
