package br.com.TriagemCheck.dtos;

import br.com.TriagemCheck.enums.CorProtocolo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TriagemRecordDto(
//        @NotNull(message = "ID Paciente é necessário")
//        UUID pacienteId,
        @NotBlank(message = "Sintomas é necessário")
        String sintomas,
        @NotBlank(message = "Severidade é necessário")
        String severidade,
        @NotNull(message = "CorProtocolo é necessário")
        CorProtocolo corProtocolo
//        @NotNull(message = "ID Profissional da Saúde é necessário")
//        UUID profissionalId

) {
}
