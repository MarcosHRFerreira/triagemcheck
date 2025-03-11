package br.com.triagemcheck.services;

import br.com.triagemcheck.dtos.ProfissionalRecordDto;
import br.com.triagemcheck.models.ProfissionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProfissionalService {

    ProfissionalModel save(ProfissionalRecordDto profissionalRecordDto);

    boolean existsBycrm(String crm);

    ProfissionalModel findById(UUID profissionalId);

    Page<ProfissionalModel> findAll(Pageable pageable);

    ProfissionalModel update(ProfissionalRecordDto profissionalRecordDto, UUID profissionalId);

    void delete(UUID profissionalId);
}
