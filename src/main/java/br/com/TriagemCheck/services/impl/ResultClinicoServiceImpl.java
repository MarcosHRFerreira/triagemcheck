package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ResultClinicoRepository;
import br.com.TriagemCheck.services.ResultClinicoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
}



