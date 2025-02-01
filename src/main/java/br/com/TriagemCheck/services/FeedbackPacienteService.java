package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;


public interface FeedbackPacienteService {
    FeedbackPacienteModel salva(FeedbackPacienteRecordDto feedbackPacienteRecordDto);
}
