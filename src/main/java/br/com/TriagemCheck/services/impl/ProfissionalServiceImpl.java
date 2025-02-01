package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.repositories.ProfissionalRepository;
import br.com.TriagemCheck.services.ProfissionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    final ProfissionalRepository profissionalRepository;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public ProfissionalModel salvar(ProfissionalRecordDto profissionalRecordDto) {

        var medicoModel = new ProfissionalModel();
        BeanUtils.copyProperties(profissionalRecordDto, medicoModel);
        medicoModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        medicoModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return profissionalRepository.save(medicoModel);
    }

    @Override
    public boolean existsBycrm(String crm) {
        return profissionalRepository.existsBycrm(crm);
    }
}
