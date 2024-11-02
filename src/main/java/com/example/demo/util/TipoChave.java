package com.example.demo.util;

public enum TipoChave {

    CELULAR("celular"),
    EMAIL("email"),
    CPF("cpf"),
    CNPJ("cnpj"),
    ALEATORIA("aleatorio");

    private final String tipo;

    TipoChave(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
}

