package br.com.TriagemCheck.services;


import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;

public interface TriagemService {

    TriagemModel salvar(TriagemRecordDto triagemRecordDto, PacienteModel pacienteModel);
}
