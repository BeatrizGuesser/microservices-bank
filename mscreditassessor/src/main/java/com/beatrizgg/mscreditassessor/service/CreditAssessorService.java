package com.beatrizgg.mscreditassessor.service;

import com.beatrizgg.mscreditassessor.FeignClient.CardsFeignClient;
import com.beatrizgg.mscreditassessor.FeignClient.ClientFeignClient;
import com.beatrizgg.mscreditassessor.exception.ClientDataNotFound;
import com.beatrizgg.mscreditassessor.exception.MicroservicesCommunicationErrorException;
import com.beatrizgg.mscreditassessor.model.ClientCards;
import com.beatrizgg.mscreditassessor.model.ClientData;
import com.beatrizgg.mscreditassessor.model.ClientSituation;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditAssessorService {

    private final ClientFeignClient clientFeignClient;
    private final CardsFeignClient cardsFeignClient;

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
}
