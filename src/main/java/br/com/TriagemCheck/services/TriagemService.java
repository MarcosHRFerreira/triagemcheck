package br.com.TriagemCheck.services;


import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;

import java.util.Optional;
import java.util.UUID;

public interface TriagemService {

    TriagemModel salvar(TriagemRecordDto triagemRecordDto, PacienteModel pacienteModel, ProfissionalModel profissionalModel);

    Optional<TriagemModel> findById(UUID triagemId);
}
