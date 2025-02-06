package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FeedbackPacienteRecordDto(

        @NotBlank(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @NotNull(message = "Avalição do Feedback Obrigatório")
        Integer avaliacao

) {
}
