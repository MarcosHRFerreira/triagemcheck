package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackProfissionalRepository extends JpaRepository<FeedbackProfissionalModel, UUID> {
}
