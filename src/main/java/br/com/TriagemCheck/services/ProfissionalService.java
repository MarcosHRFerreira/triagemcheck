package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ProfissionalService {

    ProfissionalModel save(ProfissionalRecordDto profissionalRecordDto);

    boolean existsBycrm(String crm);

    Optional<ProfissionalModel> findById(UUID profissionalId);

    Page<ProfissionalModel> findAll(Specification<ProfissionalModel> spec, Pageable pageable);

    ProfissionalModel update(ProfissionalRecordDto profissionalRecordDto, ProfissionalModel profissionalModel);

    void delete(ProfissionalModel profissionalModel);
}
