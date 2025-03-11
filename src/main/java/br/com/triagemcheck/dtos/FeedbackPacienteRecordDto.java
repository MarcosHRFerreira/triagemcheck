package br.com.triagemcheck.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FeedbackPacienteRecordDto(

        @Schema(description = "Comentário do Paciente ao ser recebido na Triagem", example = "3")
        @NotBlank(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @Schema(description = "Avaliação com notas 1 a 5.", example = "4")
        @NotNull(message = "Avalição do Feedback Obrigatório")
        Integer avaliacao

) {
}
