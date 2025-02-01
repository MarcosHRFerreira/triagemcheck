package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;

public interface ProfissionalService {

    ProfissionalModel salvar(ProfissionalRecordDto profissionalRecordDto);

    boolean existsBycrm(String crm);
}
