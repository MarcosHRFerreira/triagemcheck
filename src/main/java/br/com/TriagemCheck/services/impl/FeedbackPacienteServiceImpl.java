package br.com.TriagemCheck.services.impl;


import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.repositories.FeedbackPacienteRepository;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class FeedbackPacienteServiceImpl implements FeedbackPacienteService {

   final FeedbackPacienteRepository feedbackPacienteRepository;

    public FeedbackPacienteServiceImpl(FeedbackPacienteRepository feedbackPacienteRepository) {
        this.feedbackPacienteRepository = feedbackPacienteRepository;
    }

    @Override
    public FeedbackPacienteModel salva(FeedbackPacienteRecordDto feedbackPacienteRecordDto) {

        var feedbackPacienteModel = new FeedbackPacienteModel();

        BeanUtils.copyProperties(feedbackPacienteRecordDto,feedbackPacienteModel);
        feedbackPacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackPacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return feedbackPacienteRepository.save(feedbackPacienteModel);

    }
}
