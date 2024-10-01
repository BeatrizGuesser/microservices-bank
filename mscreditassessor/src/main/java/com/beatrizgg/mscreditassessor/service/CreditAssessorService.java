package com.beatrizgg.mscreditassessor.service;

import com.beatrizgg.mscreditassessor.FeignClient.CardsFeignClient;
import com.beatrizgg.mscreditassessor.FeignClient.ClientFeignClient;
import com.beatrizgg.mscreditassessor.exception.CardRequestErrorException;
import com.beatrizgg.mscreditassessor.exception.ClientDataNotFound;
import com.beatrizgg.mscreditassessor.exception.MicroservicesCommunicationErrorException;
import com.beatrizgg.mscreditassessor.model.*;
import com.beatrizgg.mscreditassessor.mqueue.CardIssuanceRequestPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditAssessorService {

    private final ClientFeignClient clientFeignClient;
    private final CardsFeignClient cardsFeignClient;
    private final CardIssuanceRequestPublisher cardIssuanceRequestPublisher;

    public ClientSituation getClientSituation(String cpf) throws ClientDataNotFound, MicroservicesCommunicationErrorException {
        try {
            ResponseEntity<ClientData> clientDataResponseEntity = clientFeignClient.clientData(cpf);
            ResponseEntity<List<ClientCards>> clientCardsResponseEntity = cardsFeignClient.getCardsByClient(cpf);

            return ClientSituation
                    .builder()
                    .client(clientDataResponseEntity.getBody())
                    .cards(clientCardsResponseEntity.getBody())
                    .build();

        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new ClientDataNotFound();
            }

            throw new MicroservicesCommunicationErrorException(e.getMessage(), status);
        }
    }

    public ClientAssessmentReturn postAssessment(String cpf, Long income) throws ClientDataNotFound, MicroservicesCommunicationErrorException {
        try {
            ResponseEntity<ClientData> clientDataResponseEntity = clientFeignClient.clientData(cpf);
            ResponseEntity<List<Card>> cardsResponseEntity = cardsFeignClient.getCardsIncomeUpTo(income);
            
            List<Card> cards = cardsResponseEntity.getBody();
            var approvedCardsList = cards.stream().map(card -> {

                ClientData clientData = clientDataResponseEntity.getBody();

                BigDecimal basicLimit = card.getBasicLimit();
                BigDecimal ageDB = BigDecimal.valueOf(clientData.getAge());
                var factor = ageDB.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = factor.multiply(basicLimit);

                ApprovedCard approvedCard = new ApprovedCard();
                approvedCard.setCard(card.getName());
                approvedCard.setBrand(card.getBrand());
                approvedCard.setReleasedLimit(approvedLimit);

                return approvedCard;
            }).collect(Collectors.toList());

            return new ClientAssessmentReturn(approvedCardsList);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new ClientDataNotFound();
            }

            throw new MicroservicesCommunicationErrorException(e.getMessage(), status);
        }
    }

    public CardRequestProtocol requestCardIssuance(CardIssuanceRequestData cardIssuanceRequestData) {
        try {
            cardIssuanceRequestPublisher.requestCard(cardIssuanceRequestData);
            var protocol = UUID.randomUUID().toString();
            return new CardRequestProtocol(protocol);
        } catch (Exception e) {
            throw new CardRequestErrorException(e.getMessage());
        }
    }
}
