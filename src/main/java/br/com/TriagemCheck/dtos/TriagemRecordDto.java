package br.com.TriagemCheck.dtos;

import br.com.TriagemCheck.enums.CorProtocolo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TriagemRecordDto(

        @Schema(description = "Sintomas relatados pelo paciente", example = "Dor de cabeça")
        @NotBlank(message = "Sintomas é necessário")
        String sintomas,

        @Schema(description = "Classificação de severidade - ALTA, MEDIA, BAIXA, MUITO BAIXA", example = "ALTA")
        @NotBlank(message = "Severidade é necessário")
        String severidade,

        @Schema(description = "Classificação de cores do protocolo ex: VERMELHO, LARANJA, AMARELA, VERDE, AZUL.", example = "VERMELHO")
        @NotNull(message = "CorProtocolo é necessário")
        CorProtocolo corProtocolo

) {
}
