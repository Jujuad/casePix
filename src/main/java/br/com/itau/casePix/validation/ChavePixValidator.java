package br.com.itau.casePix.validation;

import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.enumerators.TipoChaveEnum;

import java.util.regex.Pattern;

public class ChavePixValidator {

    public static void validarChave(ChavePix chavePix) {
        String valorChave = chavePix.getValorChave();
        switch (chavePix.getTipoChave()) {
            case CELULAR:
                if (!Pattern.matches("^\\+[1-9][0-9]{0,2}\\d{10}$", valorChave)) {
                    throw new ChavePixInvalidException("Chave de celular inválida. Deve iniciar com '+', seguido do código do país, DDD e número com nove dígitos.");
                }
                break;
            case EMAIL:
                if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", valorChave) || valorChave.length() > 77) {
                    throw new ChavePixInvalidException("Chave de e-mail inválida. Deve conter '@' e ter no máximo 77 caracteres.");
                }
                break;
            case CPF:
                if (!Pattern.matches("^\\d{11}$", valorChave)) {
                    throw new ChavePixInvalidException("Chave de CPF inválida. Deve ter exatamente 11 dígitos.");
                }
                break;
            case CNPJ:
                if (!Pattern.matches("^\\d{14}$", valorChave)) {
                    throw new ChavePixInvalidException("Chave de CNPJ inválida. Deve ter exatamente 14 dígitos.");
                }
                break;
            case ALEATORIA:
                if (!Pattern.matches("^[a-zA-Z0-9]{36}$", valorChave)) {
                    throw new ChavePixInvalidException("Chave aleatória inválida. Deve ter 36 caracteres alfanuméricos.");
                }
                break;
            default:
                throw new ChavePixInvalidException("Tipo de chave inválido.");
        }
    }
}
