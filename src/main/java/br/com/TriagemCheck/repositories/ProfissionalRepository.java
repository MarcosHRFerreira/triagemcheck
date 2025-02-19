package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.ProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfissionalRepository extends JpaRepository<ProfissionalModel, UUID> {

     boolean existsBycrm(String crm) ;

    boolean existsByemail(String email);
}
