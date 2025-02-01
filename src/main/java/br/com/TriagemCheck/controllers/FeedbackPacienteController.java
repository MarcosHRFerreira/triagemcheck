package br.com.TriagemCheck.controllers;

import br.com.TriagemCheck.dtos.FeedbackPacienteRecordDto;
import br.com.TriagemCheck.services.FeedbackPacienteService;
import br.com.TriagemCheck.validations.FeedbackPacienteValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedbackpacientes")
public class FeedbackPacienteController {

    Logger logger = LogManager.getLogger(FeedbackPacienteController.class);

    final FeedbackPacienteService feedbackPacienteService;
    final FeedbackPacienteValidator feedbackPacienteValidator;

    public FeedbackPacienteController(FeedbackPacienteService feedbackPacienteService, Validator validator, FeedbackPacienteValidator feedbackPacienteValidator) {
        this.feedbackPacienteService = feedbackPacienteService;
        this.feedbackPacienteValidator = feedbackPacienteValidator;
    }
    @PostMapping
    public ResponseEntity<Object> salvarFeedbackPaciente(@RequestBody FeedbackPacienteRecordDto feedbackPacienteRecordDto, Errors errors){

        logger.debug("POST salvarFeedbackPaciente feedbackPacienteRecordDto recebido {} ", feedbackPacienteRecordDto);

        feedbackPacienteValidator.validate(feedbackPacienteRecordDto,errors);
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackPacienteService.salva(feedbackPacienteRecordDto));
    }
}
