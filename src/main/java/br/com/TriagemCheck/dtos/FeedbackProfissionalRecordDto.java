package br.com.TriagemCheck.dtos;


import jakarta.validation.constraints.NotNull;


public record FeedbackProfissionalRecordDto(

        @NotNull(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @NotNull(message = "Avaliação de Severidade Obrigatório, nota de 1 a 5")
        Integer avaliacaoseveridade,

        @NotNull(message = "Avaliação de Eficácia Obrigatório, nota de 1 a 5")
        Integer avaliacaoeficacia
) {
}
