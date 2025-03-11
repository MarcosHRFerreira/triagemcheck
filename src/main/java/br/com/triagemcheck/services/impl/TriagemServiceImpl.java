package br.com.triagemcheck.services.impl;

import br.com.triagemcheck.configs.CustomBeanUtils;
import br.com.triagemcheck.converter.TriagemConverter;
import br.com.triagemcheck.dtos.TriagemCompletaRecordDto;
import br.com.triagemcheck.dtos.TriagemRecordDto;
import br.com.triagemcheck.enums.StatusOperacional;
import br.com.triagemcheck.exceptions.NoValidException;
import br.com.triagemcheck.exceptions.NotFoundException;
import br.com.triagemcheck.models.PacienteModel;
import br.com.triagemcheck.models.ProfissionalModel;
import br.com.triagemcheck.models.TriagemModel;
import br.com.triagemcheck.repositories.ProfissionalRepository;
import br.com.triagemcheck.repositories.TriagemRepository;
import br.com.triagemcheck.services.PacienteService;
import br.com.triagemcheck.services.ProfissionalService;
import br.com.triagemcheck.services.TriagemCompletaProjection;
import br.com.triagemcheck.services.TriagemService;
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
