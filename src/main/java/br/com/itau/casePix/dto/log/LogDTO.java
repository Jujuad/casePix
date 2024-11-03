package br.com.itau.casePix.dto.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    private String nivel;
    private String mensagem;
    private LocalDateTime dataHora;
    private String classe;
    private String metodo;
    private String detalhes;

    public static LogDTO logErro(String mensagem, String classe, String metodo, String detalhes) {
        return LogDTO.builder()
                .nivel("ERROR")
                .mensagem(mensagem)
                .dataHora(LocalDateTime.now())
                .classe(classe)
                .metodo(metodo)
                .detalhes(detalhes)
                .build();
    }

    public static LogDTO logInfo(String mensagem, String classe, String metodo) {
        return LogDTO.builder()
                .nivel("INFO")
                .mensagem(mensagem)
                .dataHora(LocalDateTime.now())
                .classe(classe)
                .metodo(metodo)
                .detalhes(null)
                .build();
    }
}

