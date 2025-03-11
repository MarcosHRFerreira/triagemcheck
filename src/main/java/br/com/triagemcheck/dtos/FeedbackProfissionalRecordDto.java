package br.com.triagemcheck.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record FeedbackProfissionalRecordDto(

        @Schema(description = "comentario", example = "Comentário do Profissional, com relação da abertura da Triagem")
        @NotNull(message = "Comentário do Feedback Obrigatório")
        String comentario,

        @Schema(description = "Avaliação da Severidade com notas 1 a 5.", example = "1")
        @NotNull(message = "Avaliação de Severidade Obrigatório, nota de 1 a 5")
        Integer avaliacaoseveridade,

        @Schema(description = "Avaliação da Eficacia com notas 1 a 5.", example = "5")
        @NotNull(message = "Avaliação de Eficácia Obrigatório, nota de 1 a 5")
        Integer avaliacaoeficacia
) {
}
