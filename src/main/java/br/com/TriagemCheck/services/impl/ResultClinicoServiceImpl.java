package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ResultClinicoRepository;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.services.TriagemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultClinicoServiceImpl implements ResultClinicoService {

    final ResultClinicoRepository resultClinicoRepository;
    final TriagemService triagemService;
    final ProfissionalService profissionalService;

    public ResultClinicoServiceImpl(ResultClinicoRepository resultClinicoRepository, TriagemService triagemService, ProfissionalService profissionalService) {
        this.resultClinicoRepository = resultClinicoRepository;
        this.triagemService = triagemService;
        this.profissionalService = profissionalService;
    }
    @Override
    public ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, UUID triagemId, UUID profissionalId) {
       var resultClinicoModel = new ResultClinicosModel();

        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);
        Optional<ProfissionalModel> profissionalOptional = profissionalService.findById(profissionalId);

        if (profissionalOptional.isEmpty()) {
            throw new NotFoundException("Erro: Profissional não existe.");
        }
        if (triagemOptional.isEmpty()) {
            throw new NotFoundException("Erro: Triagem não existe.");
        }

        TriagemModel triagem = triagemOptional.get();
        ProfissionalModel profissional = profissionalOptional.get();

        CustomBeanUtils.copyProperties(resultClinicoRecordDto, resultClinicoModel);
        resultClinicoModel.setDataCriacao (LocalDateTime.now(ZoneId.of("UTC")));
        resultClinicoModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        resultClinicoModel.setProfissional(profissional);
        resultClinicoModel.setTriagem((triagem));

        return resultClinicoRepository.save(resultClinicoModel);
    }

    @Override
    public Page<ResultClinicosModel> findAll(Pageable pageable) {
        return resultClinicoRepository.findAll(pageable);
    }

    @Override
    public Optional<ResultClinicosModel> findById(UUID resultadoId){
        Optional<ResultClinicosModel> resultClinicosModelOptional = resultClinicoRepository.findById(resultadoId);
        if(resultClinicosModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Resultdo Clinico not found.");
        }
        return resultClinicosModelOptional;
    }

    @Override
    public Optional<ResultClinicosModel> findProfissionalTriagemInResultClinico(UUID profissionalId, UUID triagemId, UUID resultadoId) {
       Optional<ResultClinicosModel> resultClinicosModelOptional=
               resultClinicoRepository.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId) ;
        if(resultClinicosModelOptional.isEmpty()){
            throw new NotFoundException("Error: resultadoId, profissionalId ou triagemId  not found for this TB_RESULTCLINICOS.");
        }
        return resultClinicosModelOptional;
    }

    @Override
    public ResultClinicosModel update(ResultClinicoRecordDto resultClinicoRecordDto, UUID profissionalId, UUID triagemId , UUID resultadoId) {

        Optional<ProfissionalModel> profissionalOptional = profissionalService.findById(profissionalId);
        Optional<TriagemModel> triagemOptional = triagemService.findById(triagemId);
        Optional<ResultClinicosModel> resultClinicosModelOptional = findById(resultadoId);
        Optional<ResultClinicosModel> resultOptional = findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId);

        if (profissionalOptional.isEmpty()) {
            throw new NotFoundException("Erro: Profissional não existe.");
        }
        if (triagemOptional.isEmpty()) {
            throw new NotFoundException("Erro: Triagem não existe.");
        }
        if (resultClinicosModelOptional.isEmpty()) {
            throw new NotFoundException("Erro: Resultado Clinicos não existe.");
        }
        if (resultOptional.isEmpty()) {
            throw new NotFoundException("Erro: Profissional, Triagem e Resultado Clinicos  não existe.");
        }

        ResultClinicosModel resultado = new ResultClinicosModel();

        CustomBeanUtils.copyProperties(resultClinicoRecordDto,resultado);
        resultado.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

       return  resultClinicoRepository.save(resultado);

    }


}



