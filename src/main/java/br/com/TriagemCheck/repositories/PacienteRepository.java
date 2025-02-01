package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteRepository extends JpaRepository<PacienteModel, UUID> {


      boolean existsBycpf(String cpf);
}
