package br.com.TriagemCheck.dtos;

import br.com.TriagemCheck.enums.CorProtocolo;
import br.com.TriagemCheck.enums.Severidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TriagemRecordDto(

        @Schema(description = "Sintomas relatados pelo paciente", example = "Dor de cabeça")
        @NotBlank(message = "Sintomas é necessário")
        String sintomas,

        @Schema(description = "Classificação de severidade - EMERGENCIA, MUITO URGENTE, URGENTE, POUCO URGENTE, NAO URGENTE", example = "EMERGENCIA")
        @NotNull(message = "Severidade é necessário")
        Severidade severidade,

        @Schema(description = "Classificação de cores do protocolo ex: VERMELHO, LARANJA, AMARELA, VERDE, AZUL.", example = "VERMELHO")
        @NotNull(message = "CorProtocolo é necessário")
        CorProtocolo corProtocolo,

        @Schema(description = "Id da enfermeira que está abrindo a Triagem",example = "teste")
        @NotNull(message = "Id da Enfermagem é necessário")
        UUID enfermagemId



) {
}
