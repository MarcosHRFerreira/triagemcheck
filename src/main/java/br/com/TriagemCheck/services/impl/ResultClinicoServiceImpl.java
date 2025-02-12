package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ResultClinicoRepository;
import br.com.TriagemCheck.services.ResultClinicoService;
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

    public ResultClinicoServiceImpl(ResultClinicoRepository resultClinicoRepository) {
        this.resultClinicoRepository = resultClinicoRepository;
    }
    @Override
    public ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, TriagemModel triagemModel, ProfissionalModel profissionalModel) {
       var resultClinicoModel = new ResultClinicosModel();

        CustomBeanUtils.copyProperties(resultClinicoRecordDto, resultClinicoModel);
        resultClinicoModel.setDataCriacao (LocalDateTime.now(ZoneId.of("UTC")));
        resultClinicoModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        resultClinicoModel.setProfissional(profissionalModel);
        resultClinicoModel.setTriagem((triagemModel));

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
    public ResultClinicosModel update(ResultClinicoRecordDto resultClinicoRecordDto, ResultClinicosModel resultClinicosModel) {
        CustomBeanUtils.copyProperties(resultClinicoRecordDto,resultClinicosModel);
       resultClinicosModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

       return  resultClinicoRepository.save(resultClinicosModel);

    }


}



