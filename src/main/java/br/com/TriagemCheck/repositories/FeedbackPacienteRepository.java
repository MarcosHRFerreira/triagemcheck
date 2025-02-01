package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.FeedbackPacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackPacienteRepository extends JpaRepository<FeedbackPacienteModel, UUID> {

}
