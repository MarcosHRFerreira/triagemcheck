package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ResultClinicoRecordDto(
        @NotBlank(message = "Diagnostico Obrigatório")
        String diagnostico,
        @NotBlank(message = "Tratamento Obrigatório")
        String tratamento,
        @NotBlank(message = "Desfecho Obrigatório")
        String desfecho
) {
}
