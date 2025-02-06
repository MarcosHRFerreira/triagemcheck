package br.com.TriagemCheck.dtos;


import br.com.TriagemCheck.enums.Sexo;
import br.com.TriagemCheck.enums.UnidadeFederativa;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record PacienteRecordDto(@NotBlank(message = "CPF Obrigatório")
                                String cpf,
                                @NotBlank(message = "Nome Obrigatório")
                                String  nome,
                                @NotNull(message = "Data de Nascimento Obrigatório")
                                LocalDate dtnascimento,
                                @NotNull(message = "Campo sexo Obrigatório")
                                Sexo sexo,
                                String  email,
                                @NotBlank(message = "Logradourao Obrigatório")
                                String logradouro,
                                @NotBlank(message = "Número Obrigatório")
                                String  numero,
                                @NotBlank(message = "Bairro Obrigatório")
                                String  bairro,
                                @NotBlank(message = "CEP Obrigatório")
                                String  cep,
                                @NotBlank(message = "Cidade Obrigatório")
                                String  cidade,
                                @NotNull(message = "UF Obrigatório")
                                UnidadeFederativa uf,
                                String  telefone1,
                                String  telefone2,
                                String observacao,
                                String  medicacaoContinua


) {
}
