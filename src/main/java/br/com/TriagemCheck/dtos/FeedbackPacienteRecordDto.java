package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FeedbackPacienteRecordDto(

        @NotNull(message = "Data do Feedback Obrigatório")
        LocalDateTime dataFeedback,

        @NotNull(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @NotNull(message = "Avalição do Feedback Obrigatório")
        Integer avaliacao

) {
}
