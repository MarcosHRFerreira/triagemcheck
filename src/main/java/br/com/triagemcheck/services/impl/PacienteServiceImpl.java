package br.com.triagemcheck.services.impl;

import br.com.triagemcheck.configs.CustomBeanUtils;
import br.com.triagemcheck.dtos.PacienteRecordDto;
import br.com.triagemcheck.exceptions.NoValidException;
import br.com.triagemcheck.exceptions.NotFoundException;
import br.com.triagemcheck.models.*;
import br.com.triagemcheck.repositories.*;
import br.com.triagemcheck.services.PacienteService;
import br.com.triagemcheck.validations.EmailValidator;
import br.com.triagemcheck.validations.ValidaCPF;
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
    final TriagemRepository triagemRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, TriagemRepository triagemRepository) {
        this.pacienteRepository = pacienteRepository;
        this.triagemRepository = triagemRepository;
    }
    EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public PacienteModel save(PacienteRecordDto pacienteRecordDto) {

        if(!ValidaCPF.isCPF(pacienteRecordDto.cpf())){
            throw new NoValidException("Erro: CPF inválido.");
        }
        if (!pacienteRecordDto.email().isEmpty() && !emailValidator.isValid(pacienteRecordDto.email())) {
            throw new NoValidException("Erro: Email inválido.");
        }
        if(existsBycpf(pacienteRecordDto.cpf())){
            throw new NoValidException("Erro: CPF já existe.");
        }
        if(existsByEmail(pacienteRecordDto.email())){
            throw new NoValidException("Erro: Email já existe.");
        }

        var pacienteModel=new PacienteModel();
        CustomBeanUtils.copyProperties(pacienteRecordDto,pacienteModel);

        pacienteModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return pacienteRepository.save(pacienteModel);
    }

    private boolean existsByEmail(String email) {
        return pacienteRepository.existsByemail(email);
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
    public PacienteModel findByCpf(String cpf){
        PacienteModel pacienteModel = pacienteRepository.findByCpf(cpf);
        if(pacienteModel == null){
            throw new NotFoundException("Erro: Paciente não encontrado.");
        }
        return pacienteModel;
    }
    @Override
    public PacienteModel update(PacienteRecordDto pacienteRecordDto, UUID pacienteId) {

        if(pacienteRecordDto.email()!=null) {

            if (!pacienteRecordDto.email().isEmpty() && !emailValidator.isValid(pacienteRecordDto.email())) {
                throw new NoValidException("Erro: Email inválido.");
            }
            if (!ValidaCPF.isCPF(pacienteRecordDto.cpf())) {
                throw new NoValidException("Erro: CPF inválido.");
            }
        }

        PacienteModel pacienteModel  =  getPaciente(pacienteId);

            if(!pacienteModel.getCpf().equals(pacienteRecordDto.cpf()) && existsBycpf(pacienteRecordDto.cpf()) ){
                throw new NoValidException("Erro: CPF já existe.");
            }
            if(!pacienteModel.getEmail().equals(pacienteRecordDto.email()) && existsByEmail(pacienteRecordDto.email()) ){
                throw new NoValidException("Erro: Email já existe.");
            }

            CustomBeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
            pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
            return pacienteRepository.save(pacienteModel);


    }
    @Transactional
    @Override
    public void delete(UUID pacienteId) {

           getPaciente(pacienteId);

            Optional<TriagemModel> triagemModelOptional = triagemRepository.findPacienteIntoTriagem(pacienteId);

            if(!triagemModelOptional.isEmpty()){
                throw new NoValidException("Erro: A Deleção não será posssível, existem(s) Triagem associada ao paciente.");
            }
            pacienteRepository.deleteById(pacienteId);

    }
    @Override
    public PacienteModel findById(UUID pacienteId) {

        return getPaciente(pacienteId);
    }

    public PacienteModel getPaciente(UUID pacienteId) {
        Optional<PacienteModel> pacienteModelOptional = pacienteRepository.findById(pacienteId);
        if (pacienteModelOptional.isEmpty()) {
            throw new NotFoundException("Erro: Paciente não encontrado.");
        }
        return pacienteModelOptional.get();
    }


}
