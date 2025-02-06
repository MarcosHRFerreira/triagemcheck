package br.com.TriagemCheck.services;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TriagemCompletaProjection {
    UUID gettriagemId();
    String getnomePaciente();
    String getcpfPaciente();
    String getcorProtocolo();
    String getseveridade();
    String getsintomas();
    String getnomeProfissional();
    String getespecialidadeProfissional();
    String getdesfecho();
    String getdiagnostico();
    String gettratamento();
    Integer getavaliacaoEficacia();
    Integer getavaliacaoSeveridade();
    String getcomentarioProfissional();
    Integer getavaliacaoPaciente();
    String getcomentarioPaciente();
    LocalDateTime getdataAlteracao();
    LocalDateTime getdataCriacao();
}
