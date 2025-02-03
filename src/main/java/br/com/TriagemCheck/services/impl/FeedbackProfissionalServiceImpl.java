package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
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

@Service
public class FeedbackProfissionalServiceImpl implements FeedbackProfissionalService {

    final FeedbackProfissionalRepository feedbackProfissionalRepository;

    public FeedbackProfissionalServiceImpl(FeedbackProfissionalRepository feedbackProfissionalRepository) {
        this.feedbackProfissionalRepository = feedbackProfissionalRepository;
    }

    @Override
    public FeedbackProfissionalModel save(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, ProfissionalModel profissionalModel,
                                            TriagemModel triagemModel) {

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
}
