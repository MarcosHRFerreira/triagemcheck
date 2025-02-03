package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.FeedbackPacienteRepository;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public FeedbackPacienteModel save(FeedbackPacienteRecordDto feedbackPacienteRecordDto, PacienteModel pacienteModel, TriagemModel triagemModel) {

        var feedbackPacienteModel = new FeedbackPacienteModel();

        BeanUtils.copyProperties(feedbackPacienteRecordDto,feedbackPacienteModel);
        feedbackPacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackPacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        feedbackPacienteModel.setPaciente(pacienteModel);
        feedbackPacienteModel.setTriagem(triagemModel);


        return feedbackPacienteRepository.save(feedbackPacienteModel);

    }

    @Override
    public Page<FeedbackPacienteModel> findAll(Specification<FeedbackPacienteModel> spec, Pageable pageable) {
        return feedbackPacienteRepository.findAll(pageable);
    }
}
