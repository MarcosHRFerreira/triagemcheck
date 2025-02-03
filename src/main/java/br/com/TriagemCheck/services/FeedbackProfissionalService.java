package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface FeedbackProfissionalService {

    FeedbackProfissionalModel save(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, ProfissionalModel profissionalModel,
                                     TriagemModel triagemModel);

    Page<FeedbackProfissionalModel> findAll(Specification<FeedbackProfissionalModel> spec, Pageable pageable);


}
