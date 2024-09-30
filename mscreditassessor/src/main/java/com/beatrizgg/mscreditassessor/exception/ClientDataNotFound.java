package com.beatrizgg.mscreditassessor.exception;

public class ClientDataNotFound extends Exception {

    public ClientDataNotFound() {
        super("Client data not found for the CPF entered.");
    }
}
