package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.ResultClinicosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultClinicoRepository extends JpaRepository<ResultClinicosModel, UUID> {
}
