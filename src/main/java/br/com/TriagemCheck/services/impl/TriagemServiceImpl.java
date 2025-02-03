package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.TriagemRepository;
import br.com.TriagemCheck.services.TriagemService;
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
public class TriagemServiceImpl implements TriagemService {

    final TriagemRepository triagemRepository;

    public TriagemServiceImpl(TriagemRepository triagemRepository) {
        this.triagemRepository = triagemRepository;
    }

    @Override
    public TriagemModel save(TriagemRecordDto triagemRecordDto, PacienteModel pacienteModel, ProfissionalModel profissionalModel) {

        var triagemModel = new TriagemModel();
        BeanUtils.copyProperties(triagemRecordDto, triagemModel);
        triagemModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        triagemModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        triagemModel.setPaciente(pacienteModel);
        triagemModel.setProfissional(profissionalModel);

        return triagemRepository.save(triagemModel);
    }
    @Override
    public Optional<TriagemModel> findById(UUID triagemId){
        Optional<TriagemModel> triagemModelOptional = triagemRepository.findById(triagemId);
        if(triagemModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Triagem not found.");
        }
        return triagemModelOptional;
    }

    @Override
    public Page<TriagemModel> findAll(Specification<TriagemModel> spec, Pageable pageable) {
        return triagemRepository.findAll(pageable);
    }

    @Override
    public Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId, UUID profissionalId, UUID triagemId) {
        Optional<TriagemModel> triagemModelOptional=
        triagemRepository.findPacienteProfissionalInTriagem(pacienteId,profissionalId,triagemId );
        if(triagemModelOptional.isEmpty()){
            throw new NotFoundException("Error: pacienteId, profissionalId, triagemId   not found for this TB_TRIAGENS.");
        }
        return triagemModelOptional;
    }

    @Override
    public TriagemModel update(TriagemRecordDto triagemRecordDto, TriagemModel triagemModel) {
       BeanUtils.copyProperties(triagemRecordDto,triagemModel);
       triagemModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

       return  triagemRepository.save(triagemModel);

    }


}
