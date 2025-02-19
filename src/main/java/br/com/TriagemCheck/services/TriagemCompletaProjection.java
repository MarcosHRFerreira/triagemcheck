package br.com.TriagemCheck.services;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TriagemCompletaProjection {
    UUID gettriagemId();
    UUID getpacienteId();
    String getnomePaciente();
    String getcpfPaciente();
    String getcorProtocolo();
    String getseveridade();
    String getsintomas();
    UUID getprofissionalId();
    String getcrmprofissional();
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
    UUID getprofissionalIdEnfermagem();
    String getnomeEnfermagem();
    LocalDateTime getdataAlteracao();
    LocalDateTime getdataCriacao();
}
