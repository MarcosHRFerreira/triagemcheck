package br.com.triagemcheck.dtos;

import br.com.triagemcheck.enums.Especialidade;
import br.com.triagemcheck.enums.StatusOperacional;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfissionalRecordDto(

        @Schema(description = "CRM ser o profissional for médico", example = "CRM33232")
        String crm,
        @Schema(description = "Nome do profissional", example = "Dr Roberto")
        @NotBlank(message = "Nome é necessário")
        String nome,
        @Schema(description = "Especialidade do profissional", example = "PEDIATRA")
        @NotNull(message = "Especialidade é necessário")
        Especialidade especialidade,
        @Schema(description = "Status da Operacionalidade do profissional", example = "ATIVO")
        @NotNull(message = "Status da atividade é necessário")
        StatusOperacional statusOperacional,
        @Schema(description = "Telefone para contato do profissional", example = "1199882233")
        @NotBlank(message = "Telefone é necessário")
        String telefone,
        @Schema(description = "Email para contato do profissional", example = "roberto@gmail.com")
        @NotBlank(message = "Email é necessário")
        @Email(message = "O e-mail deve ser válido")
        String email

) {
}
