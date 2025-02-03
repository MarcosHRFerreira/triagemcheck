package br.com.TriagemCheck.services;


import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface TriagemService {

    TriagemModel save(TriagemRecordDto triagemRecordDto, PacienteModel pacienteModel, ProfissionalModel profissionalModel);

    Optional<TriagemModel> findById(UUID triagemId);

    Page<TriagemModel> findAll(Specification<TriagemModel> spec, Pageable pageable);
}
