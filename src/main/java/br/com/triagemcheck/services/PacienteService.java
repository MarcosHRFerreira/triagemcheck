package br.com.triagemcheck.services;

import br.com.triagemcheck.dtos.PacienteRecordDto;
import br.com.triagemcheck.models.PacienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PacienteService {
    PacienteModel save(PacienteRecordDto pacienteRecordDto);

      boolean existsBycpf(String cpf);

    Page<PacienteModel> findAll(Pageable pageable);

     PacienteModel findByCpf( String cpf);

    PacienteModel update(PacienteRecordDto pacienteRecordDto, UUID pacienteId);

    void delete(UUID pacienteId);

    PacienteModel findById(UUID pacienteId);
}
