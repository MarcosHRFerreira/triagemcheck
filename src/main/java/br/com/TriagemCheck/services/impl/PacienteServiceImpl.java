package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.*;
import br.com.TriagemCheck.repositories.*;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.validations.ValidaCPF;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteServiceImpl implements PacienteService {

    final PacienteRepository pacienteRepository;
    private final TriagemRepository triagemRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, TriagemRepository triagemRepository) {
        this.pacienteRepository = pacienteRepository;
        this.triagemRepository = triagemRepository;
    }


    @Override
    public PacienteModel save(PacienteRecordDto pacienteRecordDto) {

        if(!ValidaCPF.isCPF(pacienteRecordDto.cpf())){
            throw new NoValidException("Erro: CPF inválido.");
        }

        var pacienteModel=new PacienteModel();
        CustomBeanUtils.copyProperties(pacienteRecordDto,pacienteModel);

        pacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return pacienteRepository.save(pacienteModel);
    }

    @Override
    public boolean existsBycpf(String cpf) {
        return pacienteRepository.existsBycpf(cpf);
    }

    @Override
    public Page<PacienteModel> findAll(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }
    @Override
    public Optional<PacienteModel> findByCpf(String cpf){
        Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findByCpf(cpf);
        if(pacienteModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Paciente not found.");
        }
        return pacienteModelOptional;
    }
    @Override
    public PacienteModel update(PacienteRecordDto pacienteRecordDto, PacienteModel pacienteModel) {
        CustomBeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        return pacienteRepository.save(pacienteModel);
    }

    @Transactional
    @Override
    public void delete(PacienteModel pacienteModel) {
        Optional<TriagemModel> triagemModelOptional = triagemRepository.findPacienteIntoTriagem(pacienteModel.getPacienteId());
        if (!triagemModelOptional.isEmpty()) {
            throw new NotFoundException("Erro: Existe Triagem para esse Paciente, não será permitido o Delete. ");
        }
        pacienteRepository.delete(pacienteModel);
    }
    @Override
    public Optional<PacienteModel> findById(UUID pacienteId) {
        Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(pacienteId);
        if(pacienteModelOptional.isEmpty()){
            throw new NotFoundException("Erro: Paciente not found.");
        }
        return pacienteModelOptional;
    }

}
