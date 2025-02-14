package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.FeedbackPacienteRepository;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.TriagemService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackPacienteServiceImpl implements FeedbackPacienteService {

   final FeedbackPacienteRepository feedbackPacienteRepository;
   final PacienteService pacienteService;
   final TriagemService triagemService;


    public FeedbackPacienteServiceImpl(FeedbackPacienteRepository feedbackPacienteRepository, PacienteService pacienteService, TriagemService triagemService) {
        this.feedbackPacienteRepository = feedbackPacienteRepository;
        this.pacienteService = pacienteService;
        this.triagemService = triagemService;
    }

    @Override
    public FeedbackPacienteModel save(FeedbackPacienteRecordDto feedbackPacienteRecordDto, UUID pacienteId, UUID triagemId ) {

        Optional<PacienteModel> pacienteOptional = pacienteService.findById(pacienteId);
        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);

        if (pacienteOptional.isEmpty()) {
            throw new NotFoundException("Erro: Paciente não encontrado.");
        }
        if (triagemOptional.isEmpty()) {
            throw new NotFoundException("Erro: Triagem não encontrada.");
        }

        PacienteModel paciente = pacienteOptional.get();
        TriagemModel triagem = triagemOptional.get();

        Optional<FeedbackPacienteModel> feedbackPacienteModelOptional =
                feedbackPacienteRepository.findPacienteTriagem(paciente.getPacienteId(), triagem.getTriagemId() );
        if(!feedbackPacienteModelOptional.isEmpty()){
            throw new NotFoundException("Error: pacienteId, triagemId  found for this TB_FEEDBACKPACIENTES.");
        }
        if(feedbackPacienteRecordDto.avaliacao()<=0 || feedbackPacienteRecordDto.avaliacao()>6 ){
            throw new NoValidException("Error: Avaliacao tem que ser entre 1 e 5.");
        }

        var feedbackPacienteModel = new FeedbackPacienteModel();

        BeanUtils.copyProperties(feedbackPacienteRecordDto,feedbackPacienteModel);
        feedbackPacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackPacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        feedbackPacienteModel.setPaciente(paciente);
        feedbackPacienteModel.setTriagem(triagem);

        return feedbackPacienteRepository.save(feedbackPacienteModel);

    }

    @Override
    public Page<FeedbackPacienteModel> findAll(Pageable pageable) {
        return feedbackPacienteRepository.findAll(pageable);
    }

    @Override
    public Optional<FeedbackPacienteModel> findById(UUID feedbackpacienteId) {
        Optional<FeedbackPacienteModel> feedbackPacienteModelOptional = feedbackPacienteRepository.findById(feedbackpacienteId);
        if(feedbackPacienteModelOptional.isEmpty()){
            throw new NotFoundException("Error: FeedbackPaciente not found.");
        }
        return feedbackPacienteModelOptional;
    }

    @Override
    public Optional<FeedbackPacienteModel> findPacienteTriagemInFeedback(UUID pacienteId, UUID triagemId, UUID feedbackpacienteId) {
       Optional<FeedbackPacienteModel> feedbackPacienteModelOptional =
               feedbackPacienteRepository.findPacienteTriagemInFeedback(pacienteId,triagemId,feedbackpacienteId );
       if(feedbackPacienteModelOptional.isEmpty()){
           throw new NotFoundException("Error: pacienteId,triagemId  not found for this TB_FEEDBACKPACIENTES.");
       }
       return feedbackPacienteModelOptional;
    }

    @Override
    public FeedbackPacienteModel update(UUID pacienteId, UUID triagemId, UUID feedbackpacienteId, FeedbackPacienteRecordDto feedbackPacienteRecordDto) {

        Optional<FeedbackPacienteModel> feedbackOptional = findPacienteTriagemInFeedback(pacienteId, triagemId, feedbackpacienteId);

        Optional<PacienteModel> pacienteOptional = pacienteService.findById(pacienteId);
        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);

        if (feedbackOptional.isEmpty()) {
            throw new NotFoundException("Erro: Feedback não encontrado.");
        }
        if (pacienteOptional.isEmpty()) {
            throw new NotFoundException("Erro: Paciente não encontrado.");
        }
        if (triagemOptional.isEmpty()) {
            throw new NotFoundException("Erro: Triagem não encontrada.");
        }

        FeedbackPacienteModel feedback = feedbackOptional.get();

        if(feedbackPacienteRecordDto.avaliacao()<=0 || feedbackPacienteRecordDto.avaliacao()>6 ){
            throw new NoValidException("Error: Avaliacao tem que ser entre 1 e 5.");
        }

        CustomBeanUtils.copyProperties(feedbackPacienteRecordDto, feedback);
        feedback.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return  feedbackPacienteRepository.save(feedback);
    }

    @Transactional
    @Override
    public void delete(UUID feedbackpacienteId) {

        Optional<FeedbackPacienteModel> feedbackOptional = findById(feedbackpacienteId);

        if (feedbackOptional.isEmpty()) {
            throw new NotFoundException("Erro: FeedbackPaciente não encontrado.");
        }
        FeedbackPacienteModel feedback = feedbackOptional.get();
        feedbackPacienteRepository.delete(feedback);


    }
}
