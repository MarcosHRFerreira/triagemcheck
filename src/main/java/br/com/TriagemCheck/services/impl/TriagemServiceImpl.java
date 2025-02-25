package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.converter.TriagemConverter;
import br.com.TriagemCheck.dtos.TriagemCompletaRecordDto;
import br.com.TriagemCheck.dtos.TriagemRecordDto;
import br.com.TriagemCheck.enums.CorProtocolo;
import br.com.TriagemCheck.enums.StatusOperacional;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.PacienteModel;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ProfissionalRepository;
import br.com.TriagemCheck.repositories.TriagemRepository;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.TriagemCompletaProjection;
import br.com.TriagemCheck.services.TriagemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class TriagemServiceImpl implements TriagemService {

    final TriagemRepository triagemRepository;

    final TriagemConverter triagemConverter = new TriagemConverter();
    final ProfissionalRepository profissionalRepository;
    final ProfissionalService profissionalService;
    final PacienteService pacienteService;

    public TriagemServiceImpl(TriagemRepository triagemRepository,
                              ProfissionalRepository profissionalRepository, ProfissionalService profissionalService, PacienteService pacienteService) {
        this.triagemRepository = triagemRepository;
        this.profissionalRepository = profissionalRepository;
        this.profissionalService = profissionalService;
        this.pacienteService = pacienteService;
    }

    @Override
    public TriagemModel save(TriagemRecordDto triagemRecordDto,  UUID pacienteId, UUID profissionalId) {

        PacienteModel pacienteOptional = pacienteService.findById(pacienteId);
        ProfissionalModel profissionalOptional = profissionalService.findById(profissionalId);

        if (profissionalOptional == null) {
            throw new NotFoundException("Erro: Profissional não encontrado.");
        }
        PacienteModel paciente = pacienteOptional;
        ProfissionalModel profissional  = profissionalOptional;

        var triagemModel = new TriagemModel();
        CustomBeanUtils.copyProperties(triagemRecordDto, triagemModel);
        triagemModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        triagemModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        triagemModel.setPaciente(paciente);
        triagemModel.setProfissional(profissional);

        Optional<ProfissionalModel> existingRecord = profissionalRepository.findById(triagemRecordDto.enfermagemId());
        if (!existingRecord.isPresent()) {
            throw new NoValidException("Erro: Registro de enfermagem não existe.");
        }
        if(profissional.getCrm().isEmpty()){
            throw new NoValidException("Erro: Profissional deve possuir um CRM válido.");
        }
        if(profissional.getStatusOperacional().equals(StatusOperacional.INATIVO)){
            throw new NoValidException("Erro: Profissional com Status de inativo. Não permitindo cadastro da Triagem, com esse profissional.");
        }

        return triagemRepository.save(triagemModel);
    }

    @Override
    public TriagemModel findById(UUID triagemId){
       TriagemModel triagemModel =  triagemRepository.findTriagemId(triagemId);
        if(triagemModel == null){
            throw new NotFoundException("Erro: Triagem não existe.");
        }
        return triagemModel;
    }

    @Override
    public Page<TriagemModel> findAll( Pageable pageable) {
        return triagemRepository.findAll(pageable);
    }
    @Override
    public Optional<TriagemModel> findPacienteProfissionalInTriagem(UUID pacienteId, UUID profissionalId, UUID triagemId) {
        Optional<TriagemModel> triagemModelOptional=
        triagemRepository.findPacienteProfissionalInTriagem(pacienteId,profissionalId,triagemId );
        if(triagemModelOptional.isEmpty()){
            throw new NotFoundException("Error: pacienteId, profissionalId, triagemId  não existem na TB_TRIAGENS.");
        }
        return triagemModelOptional;
    }
    @Override
    public TriagemModel update(TriagemRecordDto triagemRecordDto, UUID triagemId ) {

        TriagemModel triagemModel = triagemRepository.findTriagemId(triagemId);
        if (triagemModel ==null) {
            throw new NotFoundException("Erro: Triagem não encontrada.");
        }else {

            PacienteModel pacienteOptional = pacienteService.findById(triagemModel.getPaciente().getPacienteId());

            ProfissionalModel profissionalOptional = profissionalService.findById(triagemModel.getProfissional().getProfissionalId());

            if (profissionalOptional == null) {
                throw new NotFoundException("Erro: Profissional não encontrado.");
            }

            PacienteModel paciente =pacienteOptional;
            ProfissionalModel profissional = profissionalOptional;
            TriagemModel triagem = triagemModel;

            if (triagemRecordDto.enfermagemId() != null) {
                Optional<ProfissionalModel> existingRecord = profissionalRepository.findById(triagemRecordDto.enfermagemId());
                if (!existingRecord.isPresent()) {
                    throw new NoValidException("Erro: Registro de enfermagem não existe.");
                }
            }
            if (profissional.getCrm().isEmpty()) {
                throw new NoValidException("Erro: Profissional deve possuir um CRM válido.");
            }
            if (profissional.getStatusOperacional().equals(StatusOperacional.INATIVO)) {
                throw new NoValidException("Erro: Profissional com Status de inativo. Não permitindo cadastro da Triagem, com esse profissional.");
            }

            try {
                CorProtocolo corProtocolo = CorProtocolo.valueOf(triagemRecordDto.corProtocolo().toString());
                triagem.setCorProtocolo(corProtocolo);
            } catch (IllegalArgumentException e) {
                throw new NoValidException("Erro: Cor do Protocolo inválido.");
            }


            CustomBeanUtils.copyProperties(triagemRecordDto, triagem);
            triagem.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            triagem.setProfissional(profissional);
            triagem.setPaciente(paciente);

            return triagemRepository.save(triagem);

        }
    }
    @Override
    public Page<TriagemCompletaRecordDto> findTriagemCompleta(Pageable pageable,String cpf) {
        Page<TriagemCompletaProjection> triagemProjections = triagemRepository.findTriagemCompleta(cpf,pageable);
        return triagemProjections.map(triagemConverter::convertToDto );
    }

}
