package br.com.triagemcheck.services.impl;

import br.com.triagemcheck.configs.CustomBeanUtils;
import br.com.triagemcheck.dtos.ProfissionalRecordDto;
import br.com.triagemcheck.exceptions.NoValidException;
import br.com.triagemcheck.exceptions.NotFoundException;
import br.com.triagemcheck.models.*;
import br.com.triagemcheck.repositories.ProfissionalRepository;
import br.com.triagemcheck.repositories.TriagemRepository;
import br.com.triagemcheck.services.ProfissionalService;
import br.com.triagemcheck.validations.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    final ProfissionalRepository profissionalRepository;
    final TriagemRepository triagemRepository;


    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository,
                                   TriagemRepository triagemRepository
    ) {
        this.profissionalRepository = profissionalRepository;
        this.triagemRepository = triagemRepository;

    }
    EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public ProfissionalModel save(ProfissionalRecordDto profissionalRecordDto) {

        if (!emailValidator.isValid(profissionalRecordDto.email())) {
            throw new NoValidException("Erro: Email inválido.");
        }
        if(existsByEmail(profissionalRecordDto.email())){
            throw new NoValidException("Erro: Email já existe.");
        }

        if (profissionalRepository.existsBycrm(profissionalRecordDto.crm())) {
            throw new NoValidException("Erro: Esse CRM já existe para outro profissional.");
        }

        var profissionalModel = new ProfissionalModel();
        CustomBeanUtils.copyProperties(profissionalRecordDto, profissionalModel);
        profissionalModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        profissionalModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        return profissionalRepository.save(profissionalModel);
    }

    private boolean existsByEmail(String email) {
        return profissionalRepository.existsByemail(email);
    }

    @Override
    public boolean existsBycrm(String crm) {
        return profissionalRepository.existsBycrm(crm);
    }

    @Override
    public ProfissionalModel findById(UUID profissionalId){

        ProfissionalModel profissionalModelOptional = profissionalRepository.findByIdprofissionalId(profissionalId);

        if (profissionalModelOptional== null) {
            throw new NotFoundException("Erro: Profissional não encontrado.");
        }

        return profissionalModelOptional;
    }

    @Override
    public Page<ProfissionalModel> findAll(Pageable pageable) {
        return profissionalRepository.findAll(pageable);
    }

    @Override
    public ProfissionalModel update(ProfissionalRecordDto profissionalRecordDto, UUID profissionalId) {


        Optional<ProfissionalModel> profissionalOptional = profissionalRepository.findById(profissionalId);

        if (profissionalOptional.isEmpty()) {
            throw new NotFoundException("Erro: Profissional não existe.");
        } else {
            ProfissionalModel profissional = profissionalOptional.get();

            if (Objects.nonNull(profissionalRecordDto.crm()) &&
                    !Objects.equals(profissional.getCrm(), profissionalRecordDto.crm()) &&
                    profissionalRepository.existsBycrm(profissionalRecordDto.crm())) {
                throw new NoValidException("Erro: Esse CRM já existe para outro profissional.");
            }

            if(profissionalRecordDto.email() !=null) {
                if (!emailValidator.isValid(profissionalRecordDto.email())) {
                    throw new NoValidException("Erro: Email inválido.");
                }
                if(!profissional.getEmail().equals(profissionalRecordDto.email()) && existsByEmail(profissionalRecordDto.email()) ){
                    throw new NoValidException("Erro: Email já existe.");
                }
            }

            CustomBeanUtils.copyProperties(profissionalRecordDto, profissional);
            profissional.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
            return profissionalRepository.save(profissional);
        }
    }

    @Override
    public void delete(UUID profissionalId) {

        Optional<TriagemModel> triagemModelOptional = triagemRepository.findProficionalIntoTriagem(profissionalId);
        if (!triagemModelOptional.isEmpty()) {
            throw new NoValidException("Erro: Existe Triagem para esse Profissional, não será permitido o Delete. ");
        }
        profissionalRepository.deleteById(profissionalId);
    }




}
