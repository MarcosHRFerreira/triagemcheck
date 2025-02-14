package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalService {

    ProfissionalModel save(ProfissionalRecordDto profissionalRecordDto);

    boolean existsBycrm(String crm);

    Optional<ProfissionalModel> findById(UUID profissionalId);

    Page<ProfissionalModel> findAll(Pageable pageable);

    ProfissionalModel update(ProfissionalRecordDto profissionalRecordDto, UUID profissionalId);

    void delete(UUID profissionalId);
}
