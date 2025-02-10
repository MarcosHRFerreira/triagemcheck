package br.com.TriagemCheck.services;


import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TriagemService {

    TriagemModel save(TriagemRecordDto triagemRecordDto, PacienteModel pacienteModel, ProfissionalModel profissionalModel);

    Optional<TriagemModel> findById(UUID triagemId);

    Page<TriagemModel> findAll( Pageable pageable);

    Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId, UUID profissionalId, UUID triagemId);

    TriagemModel update(TriagemRecordDto triagemRecordDto, TriagemModel triagemModel);

    Page<TriagemCompletaRecordDto> findTriagemCompleta(Pageable pageable, String cpf);
}
