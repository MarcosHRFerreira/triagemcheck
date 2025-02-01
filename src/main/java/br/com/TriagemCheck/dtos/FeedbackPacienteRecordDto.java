package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackPacienteRecordDto(
        @NotNull(message = "Id Paciente Obrigatório")
        UUID pacienteId,

        @NotNull(message = "Id Triagem Obrigatório")
        UUID triagemId,

        @NotNull(message = "Data do Feedback Obrigatório")
        LocalDateTime dataFeedback,

        @NotNull(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @NotNull(message = "Avalição do Feedback Obrigatório")
        Integer avaliacao

) {
}
