package br.com.TriagemCheck.dtos;

import jakarta.validation.constraints.NotBlank;


public record FeedbackProfissionalRecordDto(

        @NotBlank(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @NotBlank(message = "Avaliação de Severidade Obrigatório, nota de 1 a 5")
        Integer avaliacaoseveridade,

        @NotBlank(message = "Avaliação de Eficácia Obrigatório, nota de 1 a 5")
        Integer avaliacaoeficacia
) {
}
