package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.ResultClinicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ResultClinicoRepository extends JpaRepository<ResultClinicosModel, UUID> {


    @Query(value="select * from TB_RESULTCLINICOS where resultado_id = :resultadoId and" +
            "  profissional_profissional_id = :profissionalId and triagem_triagem_id = :triagemId", nativeQuery = true)
    Optional<ResultClinicosModel> findProfissionalTriagemInResultClinico(UUID profissionalId, UUID triagemId, UUID resultadoId);
}
