package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface FeedbackProfissionalService {

    FeedbackProfissionalModel save(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto,  UUID triagemId);

    Page<FeedbackProfissionalModel> findAll(Pageable pageable);


    FeedbackProfissionalModel findById(UUID feedbackprofissionalId);

    Optional<FeedbackProfissionalModel> findProfissionalTriagemInFeedback(UUID profissionalId, UUID triagemId, UUID feedbackprofissionalId);

    FeedbackProfissionalModel update(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto,  UUID feedbackprofissionalId );

    void delete(UUID feedbackprofissionalId);
}
