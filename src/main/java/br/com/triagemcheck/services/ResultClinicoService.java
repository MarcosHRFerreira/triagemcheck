package br.com.triagemcheck.services;

import br.com.triagemcheck.dtos.ResultClinicoRecordDto;
import br.com.triagemcheck.models.ResultClinicosModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface ResultClinicoService {
    ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, UUID triagemId);

    Page<ResultClinicosModel> findAll(Pageable pageable);

    ResultClinicosModel findById(UUID resultadoId);

    Optional<ResultClinicosModel> findProfissionalTriagemInResultClinico(UUID profissionalId, UUID triagemId, UUID resultadoId);

    ResultClinicosModel update(ResultClinicoRecordDto resultClinicoRecordDto,  UUID resultadoId);
}
