package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.repositories.PacienteRepository;
import br.com.TriagemCheck.services.PacienteService;
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
public class PacienteServiceImpl implements PacienteService {

    final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }


    @Override
    public PacienteModel save(PacienteRecordDto pacienteRecordDto) {

        var pacienteModel=new PacienteModel();
        BeanUtils.copyProperties(pacienteRecordDto,pacienteModel);

        pacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return pacienteRepository.save(pacienteModel);
    }

    @Override
    public boolean existsBycpf(String cpf) {
        return pacienteRepository.existsBycpf(cpf);
    }

    @Override
    public Page<PacienteModel> findAll(Specification<PacienteModel> spec, Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    @Override
    public Optional<PacienteModel> findById(UUID pacienteId){
        Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(pacienteId);
        if(pacienteModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Paciente não encontrado.");
        }
        return pacienteModelOptional;
    }


    @Override
    public PacienteModel update(PacienteRecordDto pacienteRecordDto, PacienteModel pacienteModel) {
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        return pacienteRepository.save(pacienteModel);
    }


}
