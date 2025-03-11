package br.com.triagemcheck.repositories;

import br.com.triagemcheck.models.FeedbackPacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackPacienteRepository extends JpaRepository<FeedbackPacienteModel, UUID> {


    @Query(value="select * from TB_FEEDBACKPACIENTES where feedbackpaciente_id = :feedbackpacienteId and  paciente_paciente_id = :pacienteId and triagem_triagem_id = :triagemId", nativeQuery = true)
    Optional<FeedbackPacienteModel> findPacienteTriagemInFeedback(UUID pacienteId, UUID triagemId, UUID feedbackpacienteId);

    @Query(value="select * from TB_FEEDBACKPACIENTES where triagem_triagem_id = :triagemId", nativeQuery = true)
    List<FeedbackPacienteModel> findAllTriagensIntoFeedBackPaciente(UUID triagemId);

    @Query(value="select * from TB_FEEDBACKPACIENTES where  paciente_paciente_id = :pacienteId and triagem_triagem_id = :triagemId LIMIT 1", nativeQuery = true)
    Optional<FeedbackPacienteModel> findPacienteTriagem(UUID pacienteId, UUID triagemId);

    @Query(value="select * from TB_FEEDBACKPACIENTES where  feedbackpaciente_id = :feedbackpacienteId  LIMIT 1", nativeQuery = true)
    FeedbackPacienteModel findByIdFeedbackPaciente(UUID feedbackpacienteId);
}
