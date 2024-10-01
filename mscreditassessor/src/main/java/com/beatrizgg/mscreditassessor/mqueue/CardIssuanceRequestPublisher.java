package com.beatrizgg.mscreditassessor.mqueue;

import com.beatrizgg.mscreditassessor.model.CardIssuanceRequestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardIssuanceRequestPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueIssuanceCards;

    public void requestCard(CardIssuanceRequestData cardIssuanceRequestData) throws JsonProcessingException {
        var json = convertIntoJson(cardIssuanceRequestData);
        rabbitTemplate.convertAndSend(queueIssuanceCards.getName(), json);
    }

    private String convertIntoJson(CardIssuanceRequestData cardIssuanceRequestData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsString(cardIssuanceRequestData);
        return json;
    }
}
