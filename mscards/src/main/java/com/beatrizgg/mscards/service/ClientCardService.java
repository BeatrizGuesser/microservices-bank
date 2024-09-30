package com.beatrizgg.mscards.service;

import com.beatrizgg.mscards.model.ClientCard;
import com.beatrizgg.mscards.repository.ClientCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientCardService {

    private final ClientCardRepository clientCardRepository;

    public List<ClientCard> listCardsByCpf(String cpf) {
        return clientCardRepository.findByCpf(cpf);
    }
}
