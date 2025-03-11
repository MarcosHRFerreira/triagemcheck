package br.com.triagemcheck.repositories;

import br.com.triagemcheck.models.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, UUID> {

     boolean existsBycrm(String crm) ;

    boolean existsByemail(String email);


    @Query(value="select * from TB_PROFISSIONAIS where  profissional_id = :profissionalId LIMIT 1", nativeQuery = true)
    ProfissionalModel findByIdprofissionalId(UUID profissionalId);
}
