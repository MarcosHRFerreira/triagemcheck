package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.FeedbackProfissionalModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.FeedbackProfissionalRepository;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackProfissionalServiceImpl implements FeedbackProfissionalService {

    final FeedbackProfissionalRepository feedbackProfissionalRepository;

    public FeedbackProfissionalServiceImpl(FeedbackProfissionalRepository feedbackProfissionalRepository) {
        this.feedbackProfissionalRepository = feedbackProfissionalRepository;
    }

    @Override
    public FeedbackProfissionalModel save(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, ProfissionalModel profissionalModel,
                                            TriagemModel triagemModel) {

        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional =
                feedbackProfissionalRepository.findProfissionalTriagem(profissionalModel.getProfissionalId(),triagemModel.getTriagemId() );
        if(!feedbackProfissionalModelOptional.isEmpty()){
            throw new NotFoundException("Error: profissional, triagemId  found for this TB_FEEDBACKPROFISSIONAL.");
        }

        var feedbackprofissionalModel = new FeedbackProfissionalModel();

        BeanUtils.copyProperties(feedbackProfissionalRecordDto, feedbackprofissionalModel);

        feedbackprofissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        feedbackprofissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        feedbackprofissionalModel.setProfissional(profissionalModel);
        feedbackprofissionalModel.setTriagem(triagemModel);

        return feedbackProfissionalRepository.save(feedbackprofissionalModel);

    }

    @Override
    public Page<FeedbackProfissionalModel> findAll(Specification<FeedbackProfissionalModel> spec, Pageable pageable) {
        return feedbackProfissionalRepository.findAll(pageable);
    }


    @Override
    public Optional<FeedbackProfissionalModel> findById(UUID feedbackprofissionalId) {
        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional = feedbackProfissionalRepository.findById(feedbackprofissionalId);
        if(feedbackProfissionalModelOptional.isEmpty()){
            throw new NotFoundException("Error: FeedbackProfissional not found.");
        }
        return feedbackProfissionalModelOptional;
    }


    @Override
    public Optional<FeedbackProfissionalModel> findProfissionalTriagemInFeedback(UUID profissionalId, UUID triagemId, UUID feedbackprofissionalId) {
        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional =
                feedbackProfissionalRepository.findProssionalTriagemInFeedback(profissionalId,triagemId,feedbackprofissionalId );
        if(feedbackProfissionalModelOptional.isEmpty()){
            throw new NotFoundException("Error: profissionalId, triagemId  not found for this TB_FEEDBACKPROFISSIONAL.");
        }
        return feedbackProfissionalModelOptional;
    }

    @Override
    public FeedbackProfissionalModel update(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto,
                                            FeedbackProfissionalModel feedbackProfissionalModel) {
        BeanUtils.copyProperties(feedbackProfissionalRecordDto, feedbackProfissionalModel);
        feedbackProfissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return  feedbackProfissionalRepository.save(feedbackProfissionalModel);
    }

    @Override
    public void delete(FeedbackProfissionalModel feedbackProfissionalModel) {
        feedbackProfissionalRepository.delete(feedbackProfissionalModel);
    }
}
