package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;


public interface ResultClinicoService {
    ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, TriagemModel triagemModel, ProfissionalModel profissionalModel);
}
