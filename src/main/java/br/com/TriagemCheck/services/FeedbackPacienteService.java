package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface FeedbackPacienteService {
    FeedbackPacienteModel save(FeedbackPacienteRecordDto feedbackPacienteRecordDto, PacienteModel pacienteModel, TriagemModel triagemModel);

    Page<FeedbackPacienteModel> findAll(Specification<FeedbackPacienteModel> spec, Pageable pageable);
}
