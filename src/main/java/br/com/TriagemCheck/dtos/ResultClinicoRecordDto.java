package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ResultClinicoRecordDto(
        @NotBlank(message = "Nome Obrigatório")
        UUID triagemId,
        @NotBlank(message = "Nome Obrigatório")
        String diagnostico,
        String tratamento,
        String desfecho,
        @NotBlank(message = "Nome Obrigatório")
        UUID profissionalId

) {
}
