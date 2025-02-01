package br.com.TriagemCheck.dtos;

import br.com.TriagemCheck.enums.Especialidade;
import br.com.TriagemCheck.enums.StatusOperacional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfissionalRecordDto(

        String crm,
        @NotBlank(message = "Nome é necessário")
        String nome,
        @NotNull(message = "Especialidade é necessário")
        Especialidade especialidade,
        @NotNull(message = "Status da atividade é necessário")
        StatusOperacional statusOperacional,
        @NotBlank(message = "Telefone é necessário")
        String telefone,
        @NotBlank(message = "Email é necessário")
        String email

) {
}
