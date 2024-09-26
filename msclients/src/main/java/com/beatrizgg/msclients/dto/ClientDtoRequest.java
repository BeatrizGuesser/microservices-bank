package com.beatrizgg.msclients.dto;

import com.beatrizgg.msclients.model.Client;
import lombok.Data;

@Data
public class ClientDtoRequest {

    private String cpf;
    private String name;
    private String age;

    public Client toModel() {
        return new Client(cpf, name, age);
    }
}
