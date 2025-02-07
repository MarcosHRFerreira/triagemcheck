package br.com.TriagemCheck.dtos;

import br.com.TriagemCheck.enums.Sexo;
import br.com.TriagemCheck.enums.UnidadeFederativa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record PacienteRecordDto(

        @Schema(description = "CPF do Paciente", example = "11122233344")
        @NotBlank(message = "CPF Obrigatório")
        String cpf,
        @Schema(description = "Nome do Paciente", example = "Pedro")
        @NotBlank(message = "Nome Obrigatório")
        String  nome,
        @Schema(description = "Data de Nascimento do Paciente", example = "yyyy-mm-dd")
        @NotNull(message = "Data de Nascimento Obrigatório")
        LocalDate dtnascimento,
        @Schema(description = "Sexo do Paciente", example = "MASCULINO")
        @NotNull(message = "Campo sexo Obrigatório")
        Sexo sexo,
        @Schema(description = "Email do Paciente", example = "pedro@teste.com")
        String  email,
        @Schema(description = "Logradourao do Paciente", example = "rua do Pedro")
        @NotBlank(message = "Logradourao Obrigatório")
        String logradouro,
        @Schema(description = "Número do Logradouro do Paciente", example = "341")
        @NotBlank(message = "Número Obrigatório")
        String  numero,
        @NotBlank(message = "Bairro Obrigatório")
        @Schema(description = "Bairro do Logradouro do Paciente", example = "BARRA FUNDA")
        String  bairro,
        @Schema(description = "Cep do Logradouro do Paciente", example = "13565565")
        @NotBlank(message = "CEP Obrigatório")
        String  cep,
        @Schema(description = "Cidade do Logradouro do Paciente", example = "SÃO PAULO")
        @NotBlank(message = "Cidade Obrigatório")
        String  cidade,
        @Schema(description = "UF do Logradouro do Paciente", example = "SP")
        @NotNull(message = "UF Obrigatório")
        UnidadeFederativa uf,
        @Schema(description = "Telefone do Paciente", example = "119932323")
        String  telefone1,
        @Schema(description = "Telefone de algum conhecido do Paciente", example = "119932323")
        String  telefone2,
        @Schema(description = "Aguma informação importante", example = "alégico a penicilina")
        String observacao,
        @Schema(description = "Aguma informação importante de medicação ", example = "remédio para pressão")
        String  medicacaoContinua
) {
}
