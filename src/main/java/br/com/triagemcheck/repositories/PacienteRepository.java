package br.com.triagemcheck.repositories;

import br.com.triagemcheck.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteRepository extends JpaRepository<PacienteModel, UUID> {

    boolean existsBycpf(String cpf);

  // @Query(value="SELECT * FROM tb_pacientes p WHERE cpf  = :cpf)", nativeQuery = true)
    PacienteModel findByCpf(String cpf);

    boolean existsByemail(String email);
}
