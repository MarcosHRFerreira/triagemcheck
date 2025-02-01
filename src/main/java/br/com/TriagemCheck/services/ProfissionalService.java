package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalService {

    ProfissionalModel salvar(ProfissionalRecordDto profissionalRecordDto);

    boolean existsBycrm(String crm);

    Optional<ProfissionalModel> findById(UUID profissionalId);

}
