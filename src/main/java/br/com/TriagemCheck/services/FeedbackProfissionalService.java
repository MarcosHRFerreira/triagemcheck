package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;


public interface FeedbackProfissionalService {

    FeedbackProfissionalModel salvar(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto);

}
