package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.FeedbackProfissionalRecordDto;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.*;
import br.com.TriagemCheck.repositories.FeedbackProfissionalRepository;
import br.com.TriagemCheck.services.FeedbackProfissionalService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackProfissionalServiceImpl implements FeedbackProfissionalService {

    final FeedbackProfissionalRepository feedbackProfissionalRepository;
    final ProfissionalService profissionalService;
    final TriagemService triagemService;

    public FeedbackProfissionalServiceImpl(FeedbackProfissionalRepository feedbackProfissionalRepository, ProfissionalService profissionalService, TriagemService triagemService) {
        this.feedbackProfissionalRepository = feedbackProfissionalRepository;
        this.profissionalService = profissionalService;
        this.triagemService = triagemService;
    }

    @Override
    public FeedbackProfissionalModel save(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, UUID triagemId) {


        TriagemModel triagemOptional = triagemService.findById(triagemId);

        if (triagemOptional ==null) {
            throw new NotFoundException("Erro: Triagem não encontrada.");
        }else {

            UUID profissionalId = triagemOptional.getProfissional().getProfissionalId();

            ProfissionalModel profissionalOptional = profissionalService.findById(profissionalId);

            if (profissionalOptional == null) {
                throw new NotFoundException("Erro: Profissional não encontrado.");
            }

            ProfissionalModel profissional = profissionalOptional;
            TriagemModel triagem = triagemOptional;

            if(feedbackProfissionalRecordDto.avaliacaoseveridade()<=0 || feedbackProfissionalRecordDto.avaliacaoseveridade()>6 ){
                throw new NoValidException("Error: Avaliacao de Severidade tem que ser entre 1 e 5.");
            }
            if(feedbackProfissionalRecordDto.avaliacaoeficacia()<=0 || feedbackProfissionalRecordDto.avaliacaoeficacia()>6 ){
                throw new NoValidException("Error: Avaliacao de Eficacia tem que ser entre 1 e 5.");
            }

            Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional =
                    feedbackProfissionalRepository.findProfissionalTriagem(profissional.getProfissionalId(),triagem.getTriagemId() );
            if(!feedbackProfissionalModelOptional.isEmpty()){
                throw new NotFoundException("Error: profissional, triagemId  já existem na TB_FEEDBACKPROFISSIONAL.");
            }

            var feedbackprofissionalModel = new FeedbackProfissionalModel();

            CustomBeanUtils.copyProperties(feedbackProfissionalRecordDto, feedbackprofissionalModel);

            feedbackprofissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
            feedbackprofissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            feedbackprofissionalModel.setProfissional(profissional);
            feedbackprofissionalModel.setTriagem(triagem);

            return feedbackProfissionalRepository.save(feedbackprofissionalModel);
            }

    }

    @Override
    public Page<FeedbackProfissionalModel> findAll(Pageable pageable) {
        return feedbackProfissionalRepository.findAll(pageable);
    }


    @Override
    public FeedbackProfissionalModel findById(UUID feedbackprofissionalId) {
        FeedbackProfissionalModel feedbackProfissionalModelOptional = feedbackProfissionalRepository.findByfeedbackprofissionalId(feedbackprofissionalId);
        if(feedbackProfissionalModelOptional == null){
            throw new NotFoundException("Error: FeedbackProfissional não encontrado.");
        }
        return feedbackProfissionalModelOptional;
    }

    @Override
    public Optional<FeedbackProfissionalModel> findProfissionalTriagemInFeedback(UUID profissionalId, UUID triagemId, UUID feedbackprofissionalId) {
        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional =
                feedbackProfissionalRepository.findProssionalTriagemInFeedback(profissionalId,triagemId,feedbackprofissionalId );
        if(feedbackProfissionalModelOptional.isEmpty()){
            throw new NotFoundException("Error: profissionalId, triagemId não existem na TB_FEEDBACKPROFISSIONAL.");
        }
        return feedbackProfissionalModelOptional;
    }

    @Override
    public FeedbackProfissionalModel update(FeedbackProfissionalRecordDto feedbackProfissionalRecordDto, UUID feedbackprofissionalId ) {


        FeedbackProfissionalModel feedbackProfissionalModelOptional = feedbackProfissionalRepository.findByfeedbackprofissionalId(feedbackprofissionalId);

        if (feedbackProfissionalModelOptional == null) {
            throw new NotFoundException("Erro: Feedback não encontrado.");
        }else {
            ProfissionalModel profissionalOptional = profissionalService.findById(feedbackProfissionalModelOptional.getProfissional().getProfissionalId());
            if (profissionalOptional == null) {
                throw new NotFoundException("Erro: Profissional não encontrado.");
            }
            TriagemModel triagemOptional = triagemService.findById(feedbackProfissionalModelOptional.getTriagem().getTriagemId());
            if (triagemOptional == null) {
                throw new NotFoundException("Erro: Triagem não encontrada.");
            }

            if (feedbackProfissionalRecordDto.avaliacaoseveridade() <= 0 || feedbackProfissionalRecordDto.avaliacaoseveridade() > 6) {
                throw new NoValidException("Error: Avaliacao de Severidade tem que ser entre 1 e 5.");
            }
            if (feedbackProfissionalRecordDto.avaliacaoeficacia() <= 0 || feedbackProfissionalRecordDto.avaliacaoeficacia() > 6) {
                throw new NoValidException("Error: Avaliacao de Eficacia tem que ser entre 1 e 5.");
            }

            FeedbackProfissionalModel feedback = feedbackProfissionalModelOptional;

            CustomBeanUtils.copyProperties(feedbackProfissionalRecordDto, feedback);
            feedback.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            return feedbackProfissionalRepository.save(feedback);
        }
    }

    @Override
    public void delete(UUID feedbackprofissionalId) {

        FeedbackProfissionalModel feedbackProfissionalModelOptional = feedbackProfissionalRepository.findByfeedbackprofissionalId(feedbackprofissionalId);

        if (feedbackProfissionalModelOptional == null) {
            throw new NotFoundException("Erro: FeedBackProfissional não encontrado.");
        }

        feedbackProfissionalRepository.deleteById(feedbackprofissionalId);

    }
}
