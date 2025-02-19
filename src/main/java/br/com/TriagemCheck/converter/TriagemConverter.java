package br.com.TriagemCheck.converter;

import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.services.TriagemCompletaProjection;

public class TriagemConverter {

    public TriagemCompletaRecordDto convertToDto(TriagemCompletaProjection projection) {
        return new TriagemCompletaRecordDto(
                projection.gettriagemId(),
                projection.getnomePaciente(),
                projection.getpacienteId(),
                projection.getcpfPaciente(),
                projection.getcorProtocolo(),
                projection.getseveridade(),
                projection.getsintomas(),
                projection.getprofissionalId(),
                projection.getcrmprofissional(),
                projection.getnomeProfissional(),
                projection.getespecialidadeProfissional(),
                projection.getdesfecho(),
                projection.getdiagnostico(),
                projection.gettratamento(),
                projection.getavaliacaoEficacia(),
                projection.getavaliacaoSeveridade(),
                projection.getcomentarioProfissional(),
                projection.getavaliacaoPaciente(),
                projection.getcomentarioPaciente(),
                projection.getprofissionalIdEnfermagem(),
                projection.getnomeEnfermagem(),
                projection.getdataAlteracao(),
                projection.getdataCriacao()
        );
    }

}

