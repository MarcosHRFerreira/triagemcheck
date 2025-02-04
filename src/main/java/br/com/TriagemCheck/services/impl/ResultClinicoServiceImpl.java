package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ResultClinicoRepository;
import br.com.TriagemCheck.services.ResultClinicoService;
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
public class ResultClinicoServiceImpl implements ResultClinicoService {

    final ResultClinicoRepository resultClinicoRepository;

    public ResultClinicoServiceImpl(ResultClinicoRepository resultClinicoRepository) {
        this.resultClinicoRepository = resultClinicoRepository;
    }
    @Override
    public ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, TriagemModel triagemModel, ProfissionalModel profissionalModel) {
       var resultClinicoModel = new ResultClinicosModel();

        BeanUtils.copyProperties(resultClinicoRecordDto, resultClinicoModel);
        resultClinicoModel.setDataCriacao (LocalDateTime.now(ZoneId.of("UTC")));
        resultClinicoModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        resultClinicoModel.setProfissional(profissionalModel);
        resultClinicoModel.setTriagem((triagemModel));

        return resultClinicoRepository.save(resultClinicoModel);
    }

    @Override
    public Page<ResultClinicosModel> findAll(Specification<ResultClinicosModel> specification, Pageable pageable) {
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
    public ResultClinicosModel update(ResultClinicoRecordDto resultClinicoRecordDto, ResultClinicosModel resultClinicosModel) {
       BeanUtils.copyProperties(resultClinicoRecordDto,resultClinicosModel);
       resultClinicosModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

       return  resultClinicoRepository.save(resultClinicosModel);

    }


}



