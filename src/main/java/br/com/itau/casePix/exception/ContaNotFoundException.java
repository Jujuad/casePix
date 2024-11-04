package br.com.itau.casePix.exception;

public class ContaNotFoundException extends RuntimeException {

    public ContaNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ContaNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
