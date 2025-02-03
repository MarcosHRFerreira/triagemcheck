package br.com.TriagemCheck.services;

import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface ResultClinicoService {
    ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, TriagemModel triagemModel, ProfissionalModel profissionalModel);

    Page<ResultClinicosModel> findAll(Specification<ResultClinicosModel> specification, Pageable pageable);
}
