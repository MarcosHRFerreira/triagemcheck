package br.com.triagemcheck.services;


import br.com.triagemcheck.dtos.TriagemCompletaRecordDto;
import br.com.triagemcheck.dtos.TriagemRecordDto;
import br.com.triagemcheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TriagemService {

    TriagemModel save(TriagemRecordDto triagemRecordDto, UUID pacienteId, UUID profissionalId);

    TriagemModel findById(UUID triagemId);

    Page<TriagemModel> findAll( Pageable pageable);

    Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId, UUID profissionalId, UUID triagemId);

    TriagemModel update(TriagemRecordDto triagemRecordDto, UUID triagemId  );

    Page<TriagemCompletaRecordDto> findTriagemCompleta(Pageable pageable, String cpf);
}
