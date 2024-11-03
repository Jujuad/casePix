package br.com.itau.casePix.enumerators;

public enum TipoClienteEnum {

    PESSOA_FISICA("Pessoa Fisica"),
    PESSOA_JURIDICA("Pessoa Juridica");

    private final String tipoCliente;

    TipoClienteEnum(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTipo() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return tipoCliente;
    }
}


