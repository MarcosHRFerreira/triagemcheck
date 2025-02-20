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


        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);

        if (triagemOptional.isEmpty()) {
            throw new NotFoundException("Erro: Triagem não encontrada.");
        }else {

            Optional<ProfissionalModel> profissionalOptional = profissionalService.findById(triagemOptional.get().getProfissional().getProfissionalId());
            if (profissionalOptional.isEmpty()) {
                throw new NotFoundException("Erro: Profissional não encontrado.");
            }

            ProfissionalModel profissional = profissionalOptional.get();
            TriagemModel triagem = triagemOptional.get();

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
    public Optional<FeedbackProfissionalModel> findById(UUID feedbackprofissionalId) {
        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional = feedbackProfissionalRepository.findById(feedbackprofissionalId);
        if(feedbackProfissionalModelOptional.isEmpty()){
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


        Optional<FeedbackProfissionalModel> feedbackProfissionalModelOptional = findById(feedbackprofissionalId);
        if (feedbackProfissionalModelOptional.isEmpty()) {
            throw new NotFoundException("Erro: Feedback não encontrado.");
        }else {
            Optional<ProfissionalModel> profissionalOptional = profissionalService.findById(feedbackProfissionalModelOptional.get().getProfissional().getProfissionalId());
            if (profissionalOptional.isEmpty()) {
                throw new NotFoundException("Erro: Profissional não encontrado.");
            }
            Optional<TriagemModel> triagemOptional = triagemService.findById(feedbackProfissionalModelOptional.get().getTriagem().getTriagemId());
            if (triagemOptional.isEmpty()) {
                throw new NotFoundException("Erro: Triagem não encontrada.");
            }

            if (feedbackProfissionalRecordDto.avaliacaoseveridade() <= 0 || feedbackProfissionalRecordDto.avaliacaoseveridade() > 6) {
                throw new NoValidException("Error: Avaliacao de Severidade tem que ser entre 1 e 5.");
            }
            if (feedbackProfissionalRecordDto.avaliacaoeficacia() <= 0 || feedbackProfissionalRecordDto.avaliacaoeficacia() > 6) {
                throw new NoValidException("Error: Avaliacao de Eficacia tem que ser entre 1 e 5.");
            }

            FeedbackProfissionalModel feedback = feedbackProfissionalModelOptional.get();

            CustomBeanUtils.copyProperties(feedbackProfissionalRecordDto, feedback);
            feedback.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            return feedbackProfissionalRepository.save(feedback);
        }
    }

    @Override
    public void delete(UUID feedbackprofissionalId) {

        Optional<FeedbackProfissionalModel> feedbackOptional = findById(feedbackprofissionalId);

        if (feedbackOptional.isEmpty()) {
            throw new NotFoundException("Erro: FeedBackProfissional não encontrado.");
        }

        feedbackProfissionalRepository.deleteById(feedbackprofissionalId);

    }
}
