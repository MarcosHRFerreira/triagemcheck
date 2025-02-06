package br.com.TriagemCheck.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record TriagemCompletaRecordDto(
        UUID triagemId,
        String nomePaciente,
        String cpfPaciente,
        String corProtocolo,
        String severidade,
        String sintomas,
        String nomeProfissional,
        String especialidadeProfissional,
        String desfecho,
        String diagnostico,
        String tratamento,
        Integer avaliacaoEficacia,
        Integer avaliacaoSeveridade,
        String comentarioProfissional,
        Integer avaliacaoPaciente,
        String comentarioPaciente,
        LocalDateTime dataAlteracao,
        LocalDateTime dataCriacao
) {
}
