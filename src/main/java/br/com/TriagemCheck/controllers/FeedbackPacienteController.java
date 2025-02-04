package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.models.FeedbackPacienteModel;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import br.com.TriagemCheck.services.PacienteService;
import br.com.TriagemCheck.services.TriagemService;
import br.com.TriagemCheck.specificationTemplate.SpecificationTemplate;
import br.com.TriagemCheck.validations.FeedbackPacienteValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feedbackpacientes")
public class FeedbackPacienteController {

    Logger logger = LogManager.getLogger(FeedbackPacienteController.class);

    final FeedbackPacienteService feedbackPacienteService;
    final FeedbackPacienteValidator feedbackPacienteValidator;
    final TriagemService triagemService;
    final PacienteService pacienteService;

    public FeedbackPacienteController(FeedbackPacienteService feedbackPacienteService, Validator validator, FeedbackPacienteValidator feedbackPacienteValidator, TriagemService triagemService, PacienteService pacienteService) {
        this.feedbackPacienteService = feedbackPacienteService;
        this.feedbackPacienteValidator = feedbackPacienteValidator;
        this.triagemService = triagemService;
        this.pacienteService = pacienteService;
    }
    @PostMapping("/pacientes/{pacienteId}/triagens/{triagemId}/feedbackpaciente")
    public ResponseEntity<Object> save(@PathVariable(value ="pacienteId") UUID pacienteId,
                                       @PathVariable(value="triagemId") UUID triagemId,
            @RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto, Errors errors){

        logger.debug("POST salvarFeedbackPaciente feedbackPacienteRecordDto recebido {} ", feedbackPacienteRecordDto);

        feedbackPacienteValidator.validate(feedbackPacienteRecordDto,errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackPacienteService.save(feedbackPacienteRecordDto,
                pacienteService.findById(pacienteId).get(),
                triagemService.findById(triagemId).get()));
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackPacienteModel>> getAll(SpecificationTemplate.FeedbackpacienteSpec spec,
                                                              Pageable pageable,
                                                              @RequestParam(required = false) UUID feedbackpacienteId){
        Page<FeedbackPacienteModel>  feedbackpacienteModelPage = (feedbackpacienteId != null)
                ? feedbackPacienteService.findAll(SpecificationTemplate.feedbackpacienteId(feedbackpacienteId).and(spec), pageable)
                : feedbackPacienteService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(feedbackpacienteModelPage);
    }

    @GetMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "feedbackpacienteId") UUID feedbackpacienteId){
        return ResponseEntity.status(HttpStatus.OK).body(feedbackPacienteService.findById(feedbackpacienteId).get());
    }


    @PutMapping("/{feedbackpacienteId}/pacientes/{pacienteId}/triagens/{triagemId}/feedbackpaciente")
    public ResponseEntity<Object> update(@PathVariable(value ="pacienteId") UUID pacienteId,
                                               @PathVariable(value="triagemId") UUID triagemId,
                                               @PathVariable(value="feedbackpacienteId") UUID feedbackpacienteId,
                                               @RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto){

        logger.debug("PUT updateFeedBackPaciente FeedbackPacienteRecordDto received {} ", feedbackPacienteRecordDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(feedbackPacienteService.update(feedbackPacienteRecordDto, feedbackPacienteService.
                        findPacienteTriagemInFeedback(pacienteId, triagemId,feedbackpacienteId).get()));
    }

    @DeleteMapping("/{feedbackpacienteId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "feedbackpacienteId") UUID feedbackpacienteId){
        logger.debug("DELETE delete FeedbackPacientes feedbackpacienteId received {} ", feedbackpacienteId);
        feedbackPacienteService.delete(feedbackPacienteService.findById(feedbackpacienteId).get());
        return ResponseEntity.status(HttpStatus.OK).body("FeedBack Paciente deleted successfully.");
    }




}
