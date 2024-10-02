package com.beatrizgg.mscards.mqueue;

import com.beatrizgg.mscards.model.Card;
import com.beatrizgg.mscards.model.CardIssuanceRequestData;
import com.beatrizgg.mscards.model.ClientCard;
import com.beatrizgg.mscards.repository.CardRepository;
import com.beatrizgg.mscards.repository.ClientCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardIssuanceSubscriber {

    private final CardRepository cardRepository;
    private final ClientCardRepository clientCardRepository;

    @RabbitListener(queues = "${mq.queues.card-issuance}")
    public void receiveRequestIssue(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();
            CardIssuanceRequestData cardIssuanceRequestData = mapper.readValue(payload, CardIssuanceRequestData.class);

            Card card = cardRepository.findById(cardIssuanceRequestData.getIdCard()).orElseThrow();

            ClientCard clientCard = new ClientCard();
            clientCard.setCard(card);
            clientCard.setCpf(cardIssuanceRequestData.getCpf());
            clientCard.setCardLimit(cardIssuanceRequestData.getReleasedLimit());

            clientCardRepository.save(clientCard);

        } catch (Exception e) {
            log.error("Error when receiving card issuance request: {} ", e.getMessage());
        }
    }
}
