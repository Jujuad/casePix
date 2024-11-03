package br.com.itau.casePix.enumerators;

public enum TipoContaEnum {

    POUPANCA("poupanca"),
    CORRENTE("corrente");

    private final String tipoConta;

    TipoContaEnum(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getTipo() {
        return tipoConta;
    }

    @Override
    public String toString() {
        return tipoConta;
    }
}


