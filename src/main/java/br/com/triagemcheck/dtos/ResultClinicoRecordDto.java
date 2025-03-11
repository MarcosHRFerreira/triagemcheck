package br.com.triagemcheck.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ResultClinicoRecordDto(
        @Schema(description = "Diagnostico do exame", example = "Peneumonia")
        @NotBlank(message = "Diagnostico Obrigatório")
        String diagnostico,
        @Schema(description = "Tratamento para paciente", example = "Antibióticos")
        @NotBlank(message = "Tratamento Obrigatório")
        String tratamento,
        @Schema(description = "Desfecho para paciente", example = "Em Tratamento")
        @NotBlank(message = "Desfecho Obrigatório")
        String desfecho
) {
}
