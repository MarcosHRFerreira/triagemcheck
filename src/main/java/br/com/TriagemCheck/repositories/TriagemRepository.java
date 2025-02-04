package br.com.TriagemCheck.repositories;


import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TriagemRepository extends JpaRepository<TriagemModel, UUID> {

    @Query(value="select * from TB_TRIAGENS where triagem_id = :triagemId and  paciente_paciente_id = :pacienteId and" +
            " profissional_profissional_id = :profissionalId", nativeQuery = true)
    Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId, UUID profissionalId, UUID triagemId);

    @Query(value="select * from TB_TRIAGENS where paciente_paciente_id = :pacienteId", nativeQuery = true)
    List<TriagemModel> findAllPacientesIntoTriagens(UUID pacienteId);
}
