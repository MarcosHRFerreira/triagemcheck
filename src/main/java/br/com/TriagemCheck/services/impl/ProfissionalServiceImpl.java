package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.ProfissionalRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.repositories.ProfissionalRepository;
import br.com.TriagemCheck.services.ProfissionalService;
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
public class ProfissionalServiceImpl implements ProfissionalService {

    final ProfissionalRepository profissionalRepository;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    public ProfissionalModel save(ProfissionalRecordDto profissionalRecordDto) {

        var profissionalModel = new ProfissionalModel();
        BeanUtils.copyProperties(profissionalRecordDto, profissionalModel);
        profissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        profissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return profissionalRepository.save(profissionalModel);
    }

    @Override
    public boolean existsBycrm(String crm) {
        return profissionalRepository.existsBycrm(crm);
    }


    @Override
    public Optional<ProfissionalModel> findById(UUID profissionalId){
        Optional<ProfissionalModel> profissionalModelOptional = profissionalRepository.findById(profissionalId);
        if(profissionalModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Profissional não encontrado.");
        }
        return profissionalModelOptional;
    }

    @Override
    public Page<ProfissionalModel> findAll(Specification<ProfissionalModel> spec, Pageable pageable) {
        return profissionalRepository.findAll(pageable);
    }

}
