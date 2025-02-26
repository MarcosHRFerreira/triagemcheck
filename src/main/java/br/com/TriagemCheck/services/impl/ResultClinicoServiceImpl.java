package br.com.TriagemCheck.services.impl;

import br.com.TriagemCheck.configs.CustomBeanUtils;
import br.com.TriagemCheck.dtos.ResultClinicoRecordDto;
import br.com.TriagemCheck.exceptions.NoValidException;
import br.com.TriagemCheck.exceptions.NotFoundException;
import br.com.TriagemCheck.models.ProfissionalModel;
import br.com.TriagemCheck.models.ResultClinicosModel;
import br.com.TriagemCheck.models.TriagemModel;
import br.com.TriagemCheck.repositories.ResultClinicoRepository;
import br.com.TriagemCheck.services.ProfissionalService;
import br.com.TriagemCheck.services.ResultClinicoService;
import br.com.TriagemCheck.services.TriagemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultClinicoServiceImpl implements ResultClinicoService {

    final ResultClinicoRepository resultClinicoRepository;
    final TriagemService triagemService;
    final ProfissionalService profissionalService;

    public ResultClinicoServiceImpl(ResultClinicoRepository resultClinicoRepository, TriagemService triagemService, ProfissionalService profissionalService) {
        this.resultClinicoRepository = resultClinicoRepository;
        this.triagemService = triagemService;
        this.profissionalService = profissionalService;
    }
    @Override
    public ResultClinicosModel save(ResultClinicoRecordDto resultClinicoRecordDto, UUID triagemId) {
       var resultClinicoModel = new ResultClinicosModel();

        TriagemModel triagemOptional = triagemService.findById(triagemId);

        if (triagemOptional==null) {
            throw new NotFoundException("Erro: Triagem não existe.");
        }else {

            Optional<ResultClinicosModel> resultClinicosModelOptional = findByIdTriagem(triagemId);
            if (!resultClinicosModelOptional.isEmpty()) {
                throw new NoValidException("Erro: Já existe Resultado Clínico para essa Triagem.");
            }

            ProfissionalModel profissionalOptional = profissionalService.findById(triagemOptional.getProfissional().getProfissionalId());

            if (profissionalOptional == null) {
                throw new NotFoundException("Erro: Profissional não existe.");
            }

            TriagemModel triagem = triagemOptional;
            ProfissionalModel profissional = profissionalOptional;

            CustomBeanUtils.copyProperties(resultClinicoRecordDto, resultClinicoModel);
            resultClinicoModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
            resultClinicoModel.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            resultClinicoModel.setProfissional(profissional);
            resultClinicoModel.setTriagem((triagem));

            return resultClinicoRepository.save(resultClinicoModel);
        }
    }

    private Optional<ResultClinicosModel> findByIdTriagem(UUID triagemId) {
        return resultClinicoRepository.findByIdTriagem(triagemId);
    }

    @Override
    public Page<ResultClinicosModel> findAll(Pageable pageable) {
        return resultClinicoRepository.findAll(pageable);
    }

    @Override
    public ResultClinicosModel findById(UUID resultadoId){
        ResultClinicosModel resultClinicosModelOptional = resultClinicoRepository.findByIdresultadoId(resultadoId);
        if(resultClinicosModelOptional == null){
            throw new NotFoundException("Erro: Resultdo Clinico não encontrado.");
        }
        return resultClinicosModelOptional;
    }

    @Override
    public Optional<ResultClinicosModel> findProfissionalTriagemInResultClinico(UUID profissionalId, UUID triagemId, UUID resultadoId) {
       Optional<ResultClinicosModel> resultClinicosModelOptional=
               resultClinicoRepository.findProfissionalTriagemInResultClinico(profissionalId, triagemId, resultadoId) ;
        if(resultClinicosModelOptional.isEmpty()){
            throw new NotFoundException("Error: resultadoId, profissionalId ou triagemId  não existem na TB_RESULTCLINICOS.");
        }
        return resultClinicosModelOptional;
    }

    @Override
    public ResultClinicosModel update(ResultClinicoRecordDto resultClinicoRecordDto, UUID resultadoId) {

        ResultClinicosModel resultClinicosModelOptional = resultClinicoRepository.findByIdresultadoId(resultadoId);
        if (resultClinicosModelOptional == null) {
            throw new NotFoundException("Erro: Resultado Clinicos não existe.");
        }else {

            ResultClinicosModel resultado =  resultClinicosModelOptional;

            CustomBeanUtils.copyProperties(resultClinicoRecordDto, resultado);
            resultado.setDataAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

            return resultClinicoRepository.save(resultado);
        }

    }


}



