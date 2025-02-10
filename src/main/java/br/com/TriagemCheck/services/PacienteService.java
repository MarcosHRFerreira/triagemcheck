package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.models.PacienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface PacienteService {
    PacienteModel save(PacienteRecordDto pacienteRecordDto);

      boolean existsBycpf(String cpf);

    Page<PacienteModel> findAll(Pageable pageable);

    Optional<PacienteModel> findByCpf( String cpf);

    PacienteModel update(PacienteRecordDto pacienteRecordDto,PacienteModel pacienteModel);

    void delete(PacienteModel pacienteModel);

    Optional<PacienteModel> findById(UUID pacienteId);
}
