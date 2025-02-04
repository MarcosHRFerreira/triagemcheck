package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.dtos.PacienteRecordDto;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.*;
import br.com.TriagemCheck.repositories.*;
import br.com.TriagemCheck.services.PacienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteServiceImpl implements PacienteService {

    final PacienteRepository pacienteRepository;
    private final TriagemRepository triagemRepository;
    private final ResultClinicoRepository resultClinicoRepository;
    private final FeedbackPacienteRepository feedbackPacienteRepository;
    private final FeedbackProfissionalRepository feedbackProfissionalRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository,
                               TriagemRepository triagemRepository,
                               ResultClinicoRepository resultClinicoRepository,
                               FeedbackPacienteRepository feedbackPacienteRepository,
                               FeedbackProfissionalRepository feedbackProfissionalRepository) {
        this.pacienteRepository = pacienteRepository;
        this.triagemRepository = triagemRepository;
        this.resultClinicoRepository = resultClinicoRepository;
        this.feedbackPacienteRepository = feedbackPacienteRepository;
        this.feedbackProfissionalRepository = feedbackProfissionalRepository;
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
            throw new NotFoundException("Erro: Paciente not found.");
        }
        return pacienteModelOptional;
    }


    @Override
    public PacienteModel update(PacienteRecordDto pacienteRecordDto, PacienteModel pacienteModel) {
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        pacienteModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        return pacienteRepository.save(pacienteModel);
    }

    @Transactional
    @Override
    public void delete(PacienteModel pacienteModel) {

        List<TriagemModel> triagemModelList = triagemRepository.findAllPacientesIntoTriagens(pacienteModel.getPacienteId());
        for (TriagemModel triagemModel : triagemModelList) {

            List<ResultClinicosModel> resultClinicosModelList = resultClinicoRepository.findAllTriagensIntoResultClinico(triagemModel.getTriagemId());
            for (ResultClinicosModel resultClinicoModel : resultClinicosModelList) {
                List<FeedbackPacienteModel> feedbackPacienteModelList = feedbackPacienteRepository.findAllTriagensIntoFeedBackPaciente(triagemModel.getTriagemId());

                for (FeedbackPacienteModel feedbackPacienteModel : feedbackPacienteModelList) {
                    feedbackPacienteRepository.delete(feedbackPacienteModel);
                }
                resultClinicoRepository.delete(resultClinicoModel);
            }
            List<FeedbackPacienteModel> feedbackPacienteModelList = feedbackPacienteRepository.findAllTriagensIntoFeedBackPaciente(triagemModel.getTriagemId());
            for (FeedbackPacienteModel feedbackPacienteModel : feedbackPacienteModelList) {

                feedbackPacienteRepository.delete(feedbackPacienteModel);
            }
            List<FeedbackProfissionalModel> feedbackProfissionalModelList = feedbackProfissionalRepository.findAllTriagensIntoFeedBackProfissional(triagemModel.getTriagemId());
            for (FeedbackProfissionalModel feedbackProfissionalModel : feedbackProfissionalModelList) {

                feedbackProfissionalRepository.delete(feedbackProfissionalModel);
            }
            triagemRepository.delete(triagemModel);
        }
        pacienteRepository.delete(pacienteModel);
    }


}
