package br.com.TriagemCheck.repositories;

import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackProfissionalRepository extends JpaRepository<FeedbackProfissionalModel, UUID> {

    @Query(value="select * from TB_FEEDBACKPROFISSIONAL where feedbackprofissional_id = :feedbackprofissionalId and" +
            "  profissional_profissional_id = :profissionalId and triagem_triagem_id = :triagemId", nativeQuery = true)
    Optional<FeedbackProfissionalModel> findProssionalTriagemInFeedback(UUID profissionalId, UUID triagemId, UUID feedbackprofissionalId);


    @Query(value="select * from TB_FEEDBACKPROFISSIONAL where triagem_triagem_id = :triagemId", nativeQuery = true)
    List<FeedbackProfissionalModel> findAllTriagensIntoFeedBackProfissional(UUID triagemId);

    @Query(value="select * from TB_FEEDBACKPROFISSIONAL where  profissional_profissional_id = :profissionalId and triagem_triagem_id = :triagemId LIMIT 1", nativeQuery = true)
    Optional<FeedbackProfissionalModel> findProfissionalTriagem(UUID profissionalId, UUID triagemId);

    @Query(value="select * from TB_FEEDBACKPROFISSIONAL where  feedbackprofissional_id = :feedbackprofissionalId LIMIT 1", nativeQuery = true)
    FeedbackProfissionalModel findByfeedbackprofissionalId(UUID feedbackprofissionalId);
}
