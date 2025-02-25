package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface FeedbackPacienteService {
    FeedbackPacienteModel save(FeedbackPacienteRecordDto feedbackPacienteRecordDto, UUID triagemId);

    Page<FeedbackPacienteModel> findAll( Pageable pageable);

    FeedbackPacienteModel findById(UUID feedbackpacienteId);

    Optional<FeedbackPacienteModel> findPacienteTriagemInFeedback(UUID pacienteId, UUID triagemId, UUID feedbackpacienteId) ;

    FeedbackPacienteModel update( UUID feedbackpacienteId, FeedbackPacienteRecordDto feedbackPacienteRecordDto);

    void delete(UUID feedbackpacienteId);
}
