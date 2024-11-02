package br.com.itau.casePix.enumerators;

public enum TipoChaveEnum {

    CELULAR("celular"),
    EMAIL("email"),
    CPF("cpf"),
    CNPJ("cnpj"),
    ALEATORIA("aleatorio");

    private final String tipoChave;

    TipoChaveEnum(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getTipo() {
        return tipoChave;
    }

    @Override
    public String toString() {
        return tipoChave;
    }
}

